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