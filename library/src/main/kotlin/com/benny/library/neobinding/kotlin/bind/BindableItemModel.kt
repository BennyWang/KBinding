package com.benny.library.neobinding.kotlin.bind;

import java.util.*

/**
 * Created by benny on 11/17/15.
 */
public abstract class BindableItemModel<T> : BindableModel<T>() {
    val PROPERTY_ITEM_POSITION = "property_item_position"

    override fun initProperty() {
        addProperty(PROPERTY_ITEM_POSITION, Property<Int>())
        initItemProperty()
    }

    override fun initCommand() {
        initItemCommand()
    }

    public fun notifyPropertyChange(t: T?, position: Int) {
        property<Int>(PROPERTY_ITEM_POSITION).value = position
        notifyPropertyChange(t)
    }

    abstract fun initItemProperty()
    abstract fun initItemCommand()
}
