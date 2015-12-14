package com.benny.app.sample.viewmodel

import java.lang.RuntimeException
import com.benny.library.neobinding.kotlin.bind.ViewModel
import com.benny.library.neobinding.kotlin.bind.Command
import kotlin.properties.Delegates

/**
 * Created by benny on 11/19/15.
 */
class LoginViewModel(val delegate: LoginViewModel.LoginDelegate) : ViewModel<String>() {

    var level: Int by Delegates.bindProperty("level", 3)
    var name: String by Delegates.bindProperty("name", "xxxxxxx@xxxxx.com")
    var password: String by Delegates.bindProperty("password", "xxxxxxxxx")

    val login: Command<String> by Delegates.bindCommand("login", { if(name.equals("wangbin")) "SUCCESS" else throw RuntimeException("Incorrect name or password") }, { t: String -> delegate.onLoginSuccess(t)}, { e -> delegate.onLoginFailed(e)})

    override fun notifyPropertyChange(t: String?) {
    }

    interface LoginDelegate {
        fun onLoginSuccess(s: String)
        fun onLoginFailed(e: Throwable)
    }
}