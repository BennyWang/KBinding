package com.benny.app.sample.ui.activity

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import com.benny.app.sample.R
import org.jetbrains.anko.*

import com.benny.app.sample.viewmodel.LoginViewModel
import com.benny.library.kbinding.bind.BindingMode
import com.benny.library.kbinding.converter.ArrayToBooleanConverter
import com.benny.library.kbinding.drawable.borderRoundRect
import com.benny.library.kbinding.drawable.stateList
import com.benny.library.kbinding.extension.bind
import com.benny.library.kbinding.extension.click
import com.benny.library.kbinding.extension.enabled
import com.benny.library.kbinding.extension.text
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView

class LoginActivity : BaseActivity(), LoginViewModel.LoginDelegate {
    val viewModel = LoginViewModel(this)

    override fun onLoginSuccess(user: String) {
        toast("Login success with user " + user)
    }

    override fun onLoginFailed(e: Throwable) {
        toast(e.message ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginFragmentUI().setContentView(this).sBindTo(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LoginFragment", "onDestroy")
    }

    class LoginFragmentUI : ViewBinderComponent<LoginActivity> {
        val AnkoContext<*>.editTextStyle: (View) -> Unit get() = {
            v: View ->
            with(this) {
                if(v is EditText) with(v) {
                    textSizeDimen = R.dimen.font_38
                    verticalPadding = dip(8)
                    horizontalPadding = 0
                    background = null
                }
            }
        }

        val AnkoContext<*>.bgButton: Drawable get() = with(this) {
            stateList {
                borderRoundRect(dip(2).toFloat(), resources.getColor(R.color.color_20blue)) {
                    drawableState = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed)
                }
                borderRoundRect(dip(2).toFloat(), resources.getColor(R.color.color_8b)) {
                    drawableState = intArrayOf(-android.R.attr.state_enabled)
                }
                borderRoundRect(dip(2).toFloat(), resources.getColor(R.color.color_blue)) {
                    drawableState = intArrayOf(android.R.attr.state_enabled)
                }
            }
        }

        override fun builder(): AnkoContext<*>.() -> Unit = {
            verticalLayout {
                verticalLayout {
                    backgroundColor = Color.WHITE
                    leftPadding = dip(14)
                    editText {
                        hint = "请输入手机号或者电子邮箱地址"
                        bind { text(path="name", mode = BindingMode.TwoWay) }
                    }.lparams(width = matchParent)
                    view { backgroundResource = R.color.color_f2 }.lparams(width = matchParent, height = 1)
                    editText {
                        hint = "请输入密码"
                        bind { text(path="password", mode = BindingMode.TwoWay) }
                    }.lparams(width = matchParent)
                }.lparams(width = matchParent)
                textView {
                    text = "登录"
                    textSizeDimen = R.dimen.font_38
                    textColor = Color.WHITE
                    background = bgButton
                    verticalPadding = dip(10.4f)
                    isClickable = true
                    bind { click("login") }
                    bind { enabled(paths=listOf("name", "password"), converter = ArrayToBooleanConverter()) }
                }.lparams(width = matchParent) { margin = dip(14) }.let { it.gravity = Gravity.CENTER }
            }.style(editTextStyle)
        }
    }
}
