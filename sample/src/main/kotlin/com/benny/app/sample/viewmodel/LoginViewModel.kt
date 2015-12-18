package com.benny.app.sample.viewmodel

import com.benny.library.neobinding.bind.ViewModel
import com.benny.library.neobinding.bind.Command
import kotlin.properties.Delegates

/**
 * Created by benny on 11/19/15.
 */
class LoginViewModel(val delegate: LoginViewModel.LoginDelegate) : ViewModel() {

    var level: Int by Delegates.bindProperty("level", 3)
    var name: String by Delegates.bindProperty("name", "xxxxxxx@xxxxx.com")
    var password: String by Delegates.bindProperty("password", "xxxxxxxxx")

    val login: Command by Delegates.bindCommand("login", Command { it ->
        if(name.equals("wangbin")) delegate.onLoginSuccess("wangbin")
        else delegate.onLoginFailed(RuntimeException("incorrect name or password"))
    })

    interface LoginDelegate {
        fun onLoginSuccess(s: String)
        fun onLoginFailed(e: Throwable)
    }
}