package com.benny.library.neobinding.kotlin.bind

import rx.functions.Action1

/**
 * Created by benny on 9/13/15.
 */

class CommandHandler<T1, T2>(val onError: Action1<T1>, val onSuccess: Action1<T2>) {

    fun onError(error: T1) {
        onError.call(error)
    }

    fun onSuccess(data: T2) {
        onSuccess.call(data)
    }
}