package com.benny.library.neobinding.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
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
import org.jetbrains.anko.internals.AnkoInternals
import rx.functions.Action1
import java.util.*

/**
 * Created by benny on 12/12/15.
 */

public fun Context.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    val bindableLayout = BindableLayout(this)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.contentView)
    return bindableLayout
}

public fun AnkoContext<*>.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    val bindableLayout = BindableLayout(this.ctx)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.contentView)
    return bindableLayout
}

public class BindableLayout(ctx: Context) : ViewGroup(ctx), ViewBinder, BindingPropertyProvider {
    var view: View? = null
    override val contentView: View get() = view ?: throw UnsupportedOperationException("BindableLayout must have child")

    public companion object {
        val bindingExtensions: MutableMap<Class<*>, BindingExtension<in BindingProperty, in Any, *>> = HashMap()
        public fun addBindingExtension(propClass:Class<*>, extension: BindingExtension<*, *, *>) {
            bindingExtensions.put(propClass, extension as BindingExtension<in BindingProperty, in Any, *>)
        }

        init {
            addBindingExtension(DrawableBindingProperty.Level().javaClass, DrawableLevelBindingExtension())

            addBindingExtension(ViewBindingProperty.Click().javaClass, ClickBindingExtension())
            addBindingExtension(ViewBindingProperty.Enabled().javaClass, EnabledBindingExtension())
            addBindingExtension(ViewBindingProperty.Visibility().javaClass, VisibilityBindingExtension())

            addBindingExtension(TextViewBindingProperty.TextColor().javaClass, TextColorBindingExtension())
            addBindingExtension(TextViewBindingProperty.Text().javaClass, TextBindingExtension())
        }
    }

    private val bindingAssembler = BindingAssembler()

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

    override fun addView(view: View?) {
        if(view == null) return

        if(this.view != null) throw UnsupportedOperationException("BindableLayout can only have one child")
        this.view = view
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}

