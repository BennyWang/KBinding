package com.benny.library.neobinding.kotlin.bind

import android.view.View

/**
 * Created by benny on 11/18/15.
 */

public class ViewModelBinder<T> (val bindingContext: BindingContext<*>, val bindableModelFactory: () -> BindableModel<T> , val bindableViewFactory: () -> BindableView ) {

    fun bind(view: View): BindableModel<T> {
        val bindableModel = bindableModelFactory()
        val bindableView = bindableViewFactory()

        bindableView.inject(bindingContext, view)

        bindableView.oneWayPropertyBindings().forEach { propertyBinding -> bindableModel.bindProperty(bindingContext, propertyBinding) }
        bindableView.twoWayPropertyBindings().forEach { propertyBinding -> bindableModel.bindProperty(bindingContext, propertyBinding) }
        bindableView.multiplePropertyBindings().forEach { propertyBinding -> bindableModel.bindProperties(bindingContext, propertyBinding) }
        bindableView.commandBindings().forEach { commandBinding -> bindableModel.bindCommand(bindingContext, commandBinding) }

        return bindableModel
    }
}