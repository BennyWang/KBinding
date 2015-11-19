package com.benny.library.neobinding.kotlin.bind

import android.view.View
import com.benny.library.neobinding.kotlin.factory.Factory

/**
 * Created by benny on 11/18/15.
 */

public class ViewModelBinder<T> (val bindingContext: BindingContext<*>, val bindableModelFactory: Factory<BindableModel<T>> , val bindableViewFactory: Factory<BindableView> ) {

    fun bind(view: View): BindableModel<T> {
        val bindableModel = bindableModelFactory.create()
        val bindableView = bindableViewFactory.create()

        bindableView.inject(bindingContext, view)

        bindableView.oneWayPropertyBindings().forEach { propertyBinding -> bindableModel.bindProperty(bindingContext, propertyBinding) }
        bindableView.twoWayPropertyBindings().forEach { propertyBinding -> bindableModel.bindProperty(bindingContext, propertyBinding) }
        bindableView.multiplePropertyBindings().forEach { propertyBinding -> bindableModel.bindProperties(bindingContext, propertyBinding) }
        bindableView.commandBindings().forEach { commandBinding -> bindableModel.bindCommand(bindingContext, commandBinding) }

        return bindableModel
    }
}