package com.benny.library.neobinding.kotlin.bind

import rx.functions.Action1

/**
 * Created by benny on 9/13/15.
 */

class CommandHandler<T1, T2>(val onErrorCall: (it:T1) -> Unit, val onSuccessCall: (it:T2) -> Unit) {
    fun onError(error: T1) {
        onErrorCall(error)
    }

    fun onSuccess(data: T2) {
        onSuccessCall(data)
    }
}