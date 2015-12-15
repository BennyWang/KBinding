package com.benny.library.neobinding.bind

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.TextView

/**
 * Created by benny on 12/14/15.
 */

open class BindingProperty

interface BindingPropertyProvider {
}

class DrawableBindingProperty {
    class Level : BindingProperty()
}

class ViewBindingProperty {
    class Enabled : BindingProperty()
    class Visibility : BindingProperty()
    class Click : BindingProperty()
}

class TextViewBindingProperty {
    class Text : BindingProperty()
    class TextColor : BindingProperty()
}

val Drawable.levelProp: DrawableBindingProperty.Level get() = DrawableBindingProperty.Level()
val View.clickProp: ViewBindingProperty.Click get() = ViewBindingProperty.Click()
val View.enabledProp: ViewBindingProperty.Enabled get() = ViewBindingProperty.Enabled()
val View.visibilityProp: ViewBindingProperty.Visibility get() = ViewBindingProperty.Visibility()
val TextView.textColorProp: TextViewBindingProperty.TextColor get() = TextViewBindingProperty.TextColor()
val TextView.textProp: TextViewBindingProperty.Text get() = TextViewBindingProperty.Text()
