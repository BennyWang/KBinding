package com.benny.library.neobinding.kotlin.bind

import rx.functions.Action1

/**
 * Created by benny on 9/13/15.
 */

class CommandHandler<T1, T2>(val onError: (it:T1) -> Unit, val onSuccess: (it:T2) -> Unit) {
}