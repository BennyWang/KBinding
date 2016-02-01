package com.benny.library.kbinding.bind

import android.util.Log

/**
 * Created by benny on 12/15/15.
 */
open class PropertyBinding {
    companion object {
        var bindingCount: Int = 0
    }

    protected fun LogBind(property: String, mode: String) {
        ++bindingCount
        Log.d("BindingRecord", "for bind property: $property : mode: $mode --- count is $bindingCount")
    }

    protected fun LogBind(properties: List<String>, mode: String) {
        ++bindingCount
        Log.d("BindingRecord", "for bind property: " + properties.joinToString(",") + " : mode: $mode --- count is $bindingCount")
    }

    protected fun LogUnbind(property: String, mode: String) {
        --bindingCount
        Log.d("BindingRecord", "for unbind property: $property : mode: $mode --- count is $bindingCount")
    }

    protected fun LogUnbind(properties: List<String>, mode: String) {
        --bindingCount
        Log.d("BindingRecord", "for unbind property: " + properties.joinToString(",") + " : mode: $mode --- count is $bindingCount")
    }

    protected fun <T> LogOnNext(propertie: String, data: T) {
        Log.d("BindingRecord", "for onNext property: $propertie : data: $data")
    }

    protected fun LogError(error: Throwable) {
        Log.d("BindingRecord", "for onError: $error")
    }
}