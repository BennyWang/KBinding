package com.benny.library.neobinding.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import com.benny.library.neobinding.bind.*
import com.benny.library.neobinding.converter.*
import com.jakewharton.rxbinding.view.clicks
import com.jakewharton.rxbinding.view.enabled
import com.jakewharton.rxbinding.view.visibility
import com.jakewharton.rxbinding.widget.color
import com.jakewharton.rxbinding.widget.text
import com.jakewharton.rxbinding.widget.textChanges
import rx.functions.Action1

/**
 * Created by benny on 12/15/15.
 */

public interface BindingExtension<T: BindingProperty, R, MCT> {
    fun bind(view: R, bindingAssembler: BindingAssembler, prop: T, path: String): Unit = throw UnsupportedOperationException()
    fun bind(view: R, bindingAssembler: BindingAssembler, prop: T, path: String, mode: BindingMode, converter: Any?): Unit = throw UnsupportedOperationException()
    fun bind(view: R, bindingAssembler: BindingAssembler, prop: T, paths: List<String>, converter: MultipleConverter<MCT>): Unit = throw UnsupportedOperationException()
}

val BindingPropertyProvider.levelProp: DrawableBindingProperty.Level get() = DrawableBindingProperty.Level()
class DrawableLevelBindingExtension: BindingExtension<DrawableBindingProperty.Level, Drawable, Any> {
    override fun bind(drawable: Drawable, bindingAssembler: BindingAssembler, prop: DrawableBindingProperty.Level, path: String, mode: BindingMode, converter: Any?): Unit = when(mode) {
        BindingMode.OneWay -> bindingAssembler.addOneWayPropertyBinding(OneWayPropertyBinding<Int, Any>(path, Action1<Int> { t -> drawable.setLevel(t) }, converter as? OneWayConverter<Int>))
        BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for drawable level")
        BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for drawable level")
    }
}

val BindingPropertyProvider.clickProp: ViewBindingProperty.Click get() = ViewBindingProperty.Click()
class ClickBindingExtension: BindingExtension<ViewBindingProperty.Click, View, Any> {
    override fun bind(view: View, bindingAssembler: BindingAssembler, prop: ViewBindingProperty.Click, path: String): Unit {
        bindingAssembler.addCommandBinding(path, view.clicks(), view.enabled())
    }
}

val BindingPropertyProvider.enabledProp: ViewBindingProperty.Enabled get() = ViewBindingProperty.Enabled()
class EnabledBindingExtension: BindingExtension<ViewBindingProperty.Enabled, View, Boolean> {
    override fun bind(view: View, bindingAssembler: BindingAssembler, prop: ViewBindingProperty.Enabled, path: String, mode: BindingMode, converter: Any?): Unit {
        when(mode) {
            BindingMode.OneWay -> bindingAssembler.addOneWayPropertyBinding(path, view.enabled(), converter as? OneWayConverter<Boolean>)
            BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for enabled")
            BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for enabled")
        }
    }
    override fun bind(view: View, bindingAssembler: BindingAssembler, prop: ViewBindingProperty.Enabled, paths: List<String>, converter: MultipleConverter<Boolean>): Unit {
        bindingAssembler.addMultiplePropertyBinding(paths, view.enabled(), converter)
    }
}

val BindingPropertyProvider.visibilityProp: ViewBindingProperty.Visibility get() = ViewBindingProperty.Visibility()
class VisibilityBindingExtension: BindingExtension<ViewBindingProperty.Visibility, View, Boolean> {
    override fun bind(view: View, bindingAssembler: BindingAssembler, prop: ViewBindingProperty.Visibility, path: String, mode: BindingMode, converter: Any?): Unit {
        when(mode) {
            BindingMode.OneWay -> bindingAssembler.addOneWayPropertyBinding(path, view.visibility(), converter as? OneWayConverter<Boolean>)
            BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for visibility")
            BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for visibility")
        }
    }
    override fun bind(view: View, bindingAssembler: BindingAssembler, prop: ViewBindingProperty.Visibility, paths: List<String>, converter: MultipleConverter<Boolean>): Unit {
        bindingAssembler.addMultiplePropertyBinding(OneWayPropertyBinding<Boolean, Any>(paths, view.visibility(), converter))
    }
}

val BindingPropertyProvider.textColorProp: TextViewBindingProperty.TextColor get() = TextViewBindingProperty.TextColor()
class TextColorBindingExtension: BindingExtension<TextViewBindingProperty.TextColor, TextView, Int> {
    override fun bind(view: TextView, bindingAssembler: BindingAssembler, prop: TextViewBindingProperty.TextColor, path: String, mode: BindingMode, converter: Any?): Unit {
        when(mode) {
            BindingMode.OneWay -> bindingAssembler.addOneWayPropertyBinding(path, view.color(), converter as? OneWayConverter<Int>)
            BindingMode.OneWayToSource -> throw UnsupportedOperationException("OneWayToSource not allowed for text color")
            BindingMode.TwoWay -> throw UnsupportedOperationException("TwoWay not allowed for text color")
        }
    }
    override fun bind(view: TextView, bindingAssembler: BindingAssembler, prop: TextViewBindingProperty.TextColor, paths: List<String>, converter: MultipleConverter<Int>): Unit {
        bindingAssembler.addMultiplePropertyBinding(OneWayPropertyBinding<Int, Any>(paths, view.color(), converter))
    }
}

val BindingPropertyProvider.textProp: TextViewBindingProperty.Text get() = TextViewBindingProperty.Text()
class TextBindingExtension: BindingExtension<TextViewBindingProperty.Text, TextView, CharSequence> {
    override fun bind(view: TextView, bindingAssembler: BindingAssembler, prop: TextViewBindingProperty.Text, path: String, mode: BindingMode, converter: Any?): Unit {
        when(mode) {
            BindingMode.OneWay -> bindingAssembler.addOneWayPropertyBinding(path, view.text(), converter as? OneWayConverter<CharSequence>)
            BindingMode.OneWayToSource -> bindingAssembler.addOneWayPropertyBinding<String, Any>(path, view.textChanges().map { it.toString() }.skip(1), converter as? OneWayConverter<Any>)
            BindingMode.TwoWay -> bindingAssembler.addTwoWayPropertyBinding(path, view.textChanges().map { it.toString() }.skip(1), view.text(), converter as? TwoWayConverter<String, String>)
        }
    }
    override fun bind(view: TextView, bindingAssembler: BindingAssembler, prop: TextViewBindingProperty.Text, paths: List<String>, converter: MultipleConverter<CharSequence>): Unit {
        bindingAssembler.addMultiplePropertyBinding(paths, view.text(), converter)
    }
}

