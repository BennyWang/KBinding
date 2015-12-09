package com.benny.library.neobinding.kotlin.bind

import android.view.View

/**
 * Created by benny on 11/18/15.
 */

public class ViewBinder<T : BindableModel<*> > (val bindingContext: BindingContext<*>, val bindableModelFactory: () -> T , val bindableViewFactory: () -> BindableView ) {

    fun bind(view: View): T {
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