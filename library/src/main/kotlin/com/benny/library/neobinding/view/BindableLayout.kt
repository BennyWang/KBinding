package com.benny.library.neobinding.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.widget.text
import com.jakewharton.rxbinding.widget.textChanges

import com.benny.library.neobinding.bind.*
import com.benny.library.neobinding.converter.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import rx.functions.Action1

/**
 * Created by benny on 12/12/15.
 */

public fun View.bindTo(bindingContext: BindingContext<*>, viewModel: ViewModel<*>): Unit = when(this) {
    is BindableLayout -> bindTo(bindingContext, viewModel)
    else -> {}
}

public fun Activity.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    return ankoView({ BindableLayout(this) }, init)
}

public fun AnkoContext<*>.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    return ankoView({ BindableLayout(this.ctx) }, init)
}

public class BindableLayout(context: Context) : RelativeLayout(context), ViewBinder {
    private val bindingAssembler = BindingAssembler()
    override val contentView: View get() = this

    public override fun bindTo(bindingContext: BindingContext<*>, viewModel: ViewModel<*>) {
        bindingAssembler.bindTo(bindingContext, viewModel)
    }

    val Drawable.levelProp: DrawableBindingProperty.Level get() = DrawableBindingProperty.Level()
    val View.clickProp: ViewBindingProperty.Click get() = ViewBindingProperty.Click()
    val View.enabledProp: ViewBindingProperty.Enabled get() = ViewBindingProperty.Enabled()
    val TextView.textProp: TextViewBindingProperty.Text get() = TextViewBindingProperty.Text()

    fun Drawable.bind(prop: DrawableBindingProperty.Level, path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = null): Unit = when(mode) {
        BindingMode.OneWay -> {
            bindingAssembler.addOneWayPropertyBinding(OneWayPropertyBinding<Int, Any>(path, Action1<kotlin.Int> { t -> setLevel(t) }, converter as? OneWayConverter<Int> ?: EmptyOneWayConverter<Int>()))
        }
        BindingMode.OneWayToSource -> throw UnsupportedOperationException("enabled is one way mode, can not bind as one way to source")
        BindingMode.TwoWay -> throw UnsupportedOperationException("enabled is one way mode, can not bind as two way")
    }

    fun View.bind(prop: ViewBindingProperty.Click, path: String): Unit {
        bindingAssembler.addCommandBinding(CommandBinding(path, this.clicks(), this.enabled()))
    }

    fun View.bind(prop: ViewBindingProperty.Enabled, path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = null): Unit = when (mode) {
        BindingMode.OneWay -> {
            bindingAssembler.addOneWayPropertyBinding(OneWayPropertyBinding<Boolean, Any>(path, this.enabled(), converter as? OneWayConverter<Boolean> ?: EmptyOneWayConverter<Boolean>()))
        }
        BindingMode.OneWayToSource -> throw UnsupportedOperationException("enabled is one way mode, can not bind as one way to source")
        BindingMode.TwoWay -> throw UnsupportedOperationException("enabled is one way mode, can not bind as two way")
    }
    fun View.bind(prop: ViewBindingProperty.Enabled, paths: List<String>, converter: MultipleConverter<Boolean>) {
        bindingAssembler.addMultiplePropertyBinding(OneWayPropertyBinding<Boolean, Any>(paths, this.enabled(), converter))
    }

    fun TextView.bind(prop: TextViewBindingProperty.Text, path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = null): Unit = when (mode) {
        BindingMode.OneWay -> {
            bindingAssembler.addOneWayPropertyBinding(OneWayPropertyBinding<CharSequence, Any>(path, this.text(), converter as? OneWayConverter<CharSequence> ?: EmptyOneWayConverter<CharSequence>()))
        }
        BindingMode.OneWayToSource -> throw UnsupportedOperationException("Text View is readonly, can not bind as one way to source")
        BindingMode.TwoWay -> throw UnsupportedOperationException("Text View is readonly, can not bind as two way")
    }
    fun TextView.bind(prop: TextViewBindingProperty.Text, paths: List<String>, converter: MultipleConverter<CharSequence>) {
        bindingAssembler.addMultiplePropertyBinding(OneWayPropertyBinding<CharSequence, Any>(paths, this.text(), converter))
    }

    fun EditText.bind(prop: TextViewBindingProperty.Text, path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = null): Unit = when (mode) {
        BindingMode.OneWay -> {
            bindingAssembler.addOneWayPropertyBinding(OneWayPropertyBinding<CharSequence, Any>(path, this.text(), converter as? OneWayConverter<CharSequence> ?: EmptyOneWayConverter<CharSequence>()))
        }
        BindingMode.OneWayToSource -> {
            bindingAssembler.addOneWayPropertyBinding(OneWayPropertyBinding(path, this.textChanges().map { it.toString() }.skip(1), converter as? OneWayConverter<CharSequence> ?: EmptyOneWayConverter<CharSequence>()))
        }
        BindingMode.TwoWay -> {
            bindingAssembler.addTwoWayPropertyBinding(TwoWayPropertyBinding<String, String>(path, this.textChanges().map { it.toString() }.skip(1), this.text(), converter as? TwoWayConverter<String, String> ?: EmptyTwoWayConverter<String, String>()))
        }
    }
}

