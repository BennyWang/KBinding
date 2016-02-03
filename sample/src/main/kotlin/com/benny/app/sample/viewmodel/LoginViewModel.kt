package com.benny.app.sample.viewmodel

import com.benny.library.kbinding.bind.Command
import com.benny.library.kbinding.bind.ViewModel

/**
 * Created by benny on 11/19/15.
 */

class LoginViewModel(val loginDelegate: LoginViewModel.LoginDelegate) : ViewModel() {

    var name: String by bindProperty("name") { "xxxxxxx@xxxxx.com" }
    var password: String by bindProperty("password") { "xxxxxxxxx" }

    val login: Command<Unit> by bindCommand("login") { params, canExecute ->
        if (name.equals("wangbin")) loginDelegate.onLoginSuccess("wangbin")
        else loginDelegate.onLoginFailed(RuntimeException("incorrect name or password"))
    }

    interface LoginDelegate {
        fun onLoginSuccess(s: String)
        fun onLoginFailed(e: Throwable)
    }
}

