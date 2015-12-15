package com.benny.library.neobinding.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.util.Log
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

fun AnkoContext<*>.bind(propertyBinding: PropertyBinding): Unit {
   if(this is BindableLayout) bindingAssembler.addBinding(propertyBinding)
}

public fun Context.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    val bindableLayout = BindableLayout(this)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.view)
    return bindableLayout
}

public fun AnkoContext<*>.bindableLayout(init: BindableLayout.() -> Unit): BindableLayout {
    val bindableLayout = BindableLayout(this.ctx)
    bindableLayout.init()
    AnkoInternals.addView(this, bindableLayout.view)
    return bindableLayout
}

public class BindableLayout(override val ctx: Context) : AnkoContext<Unit>, ViewBinder, BindingPropertyProvider {
    var childView: View? = null

    override val owner: Unit
        get() = throw UnsupportedOperationException()

    override val view: View
        get() = childView ?: throw IllegalStateException("View was not set previously")

    val bindingAssembler = BindingAssembler()

    public override fun bindTo(bindingContext: BindingContext<*>, viewModel: ViewModel<*>) {
        bindingAssembler.bindTo(bindingContext, viewModel)
    }

    public fun bind(propertyBinding: PropertyBinding): Unit {
        bindingAssembler.addBinding(propertyBinding)
    }

    override fun addView(view: View?, params: ViewGroup.LayoutParams?) {
        if(view == null) return

        if(this.childView != null) throw UnsupportedOperationException("BindableLayout can only have one child")
        this.childView = view
    }
}

