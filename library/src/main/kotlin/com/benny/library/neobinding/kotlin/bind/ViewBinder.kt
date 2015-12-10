package com.benny.library.neobinding.kotlin.bind

import android.view.View
import com.benny.library.neobinding.kotlin.view.ViewFinder

/**
 * Created by benny on 11/18/15.
 */

public class ViewBinder<T : BindableModel<*> > (val bindingContext: BindingContext<*>, val bindableModelFactory: () -> T , val bindableViewFactory: () -> BindableView ) {

    fun bind(viewFinder: ViewFinder): T {
        val bindableModel = bindableModelFactory()
        val bindableView = bindableViewFactory()

        bindableView.inject(bindingContext, viewFinder)

        bindableView.oneWayPropertyBindings().forEach { propertyBinding -> bindableModel.bindProperty(bindingContext, propertyBinding) }
        bindableView.twoWayPropertyBindings().forEach { propertyBinding -> bindableModel.bindProperty(bindingContext, propertyBinding) }
        bindableView.multiplePropertyBindings().forEach { propertyBinding -> bindableModel.bindProperties(bindingContext, propertyBinding) }
        bindableView.commandBindings().forEach { commandBinding -> bindableModel.bindCommand(bindingContext, commandBinding) }

        return bindableModel
    }
}