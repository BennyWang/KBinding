package com.benny.library.neobinding.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.widget.text
import com.jakewharton.rxbinding.widget.textChanges

import com.benny.library.neobinding.bind.*
import com.benny.library.neobinding.converter.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import rx.functions.Action1
import java.util.*

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

public class BindableLayout(context: Context) : RelativeLayout(context), ViewBinder, BindingPropertyProvider {
    public companion object {
        val bindingExtensions: MutableMap<Class<*>, BindingExtension<in BindingProperty, in View, *>> = HashMap()
        public fun addBindingExtension(propClass:Class<*>, extension: BindingExtension<*, *, *>) {
            bindingExtensions.put(propClass, extension as BindingExtension<in BindingProperty, in View, *>)
        }

        init {
            addBindingExtension(ViewBindingProperty.Click().javaClass, ClickBindingExtension())
            addBindingExtension(ViewBindingProperty.Enabled().javaClass, EnabledBindingExtension())
            addBindingExtension(ViewBindingProperty.Visibility().javaClass, VisibilityBindingExtension())

            addBindingExtension(TextViewBindingProperty.TextColor().javaClass, TextColorBindingExtension())
            addBindingExtension(TextViewBindingProperty.Text().javaClass, TextBindingExtension())
        }
    }

    private val bindingAssembler = BindingAssembler()
    override val contentView: View get() = this

    public override fun bindTo(bindingContext: BindingContext<*>, viewModel: ViewModel<*>) {
        bindingAssembler.bindTo(bindingContext, viewModel)
    }

    fun View.bind(prop: BindingProperty, path: String): Unit {
        bindingExtensions[prop.javaClass]?.bind(this, bindingAssembler, prop, path)
    }
    fun View.bind(prop: BindingProperty, path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = null): Unit {
        bindingExtensions[prop.javaClass]?.bind(this, bindingAssembler, prop, path, mode, converter)
    }
    fun View.bind(prop: BindingProperty, paths: List<String>, converter: MultipleConverter<*>) {
        bindingExtensions[prop.javaClass]?.bind(this, bindingAssembler, prop, paths, converter)
    }

    fun Drawable.bind(prop: DrawableBindingProperty.Level, path: String, mode: BindingMode = BindingMode.OneWay, converter: Any? = null): Unit = when(mode) {
        BindingMode.OneWay -> {
            bindingAssembler.addOneWayPropertyBinding(OneWayPropertyBinding<Int, Any>(path, Action1<kotlin.Int> { t -> setLevel(t) }, converter as? OneWayConverter<Int> ?: EmptyOneWayConverter<Int>()))
        }
        BindingMode.OneWayToSource -> throw UnsupportedOperationException("enabled is one way mode, can not bind as one way to source")
        BindingMode.TwoWay -> throw UnsupportedOperationException("enabled is one way mode, can not bind as two way")
    }
}

