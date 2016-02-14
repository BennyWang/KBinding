package com.benny.app.sample.ui.fragment

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.benny.app.sample.R
import com.benny.app.sample.viewmodel.LoginViewModel
import com.benny.library.kbinding.bind.BindingMode
import com.benny.library.kbinding.bind.Command
import com.benny.library.kbinding.common.bindings.click
import com.benny.library.kbinding.common.bindings.enabled
import com.benny.library.kbinding.common.bindings.text
import com.benny.library.kbinding.common.borderRoundRect
import com.benny.library.kbinding.common.stateList
import com.benny.library.kbinding.common.style
import com.benny.library.kbinding.common.viewStyle
import com.benny.library.kbinding.converter.ArrayToBooleanConverter
import com.benny.library.kbinding.dsl.TwoWay
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast

class LoginFragment : BaseFragment(), LoginViewModel.LoginDelegate {
    var contentView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("LoginFragment", "onCreateView this:" + this)

        if(contentView == null) {
            contentView = LoginFragmentUI().createViewBinder(act, this).bindTo(this)
        }
        return contentView
    }

    override fun onLoginSuccess(user: String) {
        toast("Login success with user " + user)
    }

    var name: String? by bindProperty("name") { "xxxxxxx@xxxxx.com" }
    var password: String? by bindProperty("password") { "xxxxxxxxx" }

    val login: Command<Unit> by bindCommand("login") { params, canExecute ->
        if (name.equals("wangbin")) onLoginSuccess("wangbin")
        else onLoginFailed(RuntimeException("incorrect name or password"))
    }

    override fun onLoginFailed(e: Throwable) {
        toast(e.message ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LoginFragment", "onCreate")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LoginFragment", "onDestroyView")

    }

    class LoginFragmentUI : ViewBinderComponent<LoginFragment> {
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
                val tvStyle = viewStyle<TextView> {
                    textSizeDimen = R.dimen.font_38
                    verticalPadding = dip(8)
                    horizontalPadding = 0
                    background = null
                    setBackgroundDrawable(null)
                }
                verticalLayout {
                    backgroundColor = Color.WHITE
                    leftPadding = dip(14)
                    editText {
                        hintResource = R.string.name_hint
                        style = tvStyle
                        bind { text(path="name", mode = TwoWay) }
                    }.lparams(width = matchParent)
                    view { backgroundResource = R.color.color_f2 }.lparams(width = matchParent, height = 1)
                    editText {
                        hintResource = R.string.password_hint
                        style = tvStyle
                        bind { text(path="password", mode = BindingMode.TwoWay) }
                    }.lparams(width = matchParent)
                }.lparams(width = matchParent)
                textView {
                    textResource = R.string.log_in
                    textSizeDimen = R.dimen.font_38
                    textColor = Color.WHITE
                    background = bgButton
                    backgroundDrawable = bgButton
                    verticalPadding = dip(10.4f)
                    isClickable = true
                    gravity = Gravity.CENTER
                    bind { click("login") }
                    bind { enabled("name", "password", converter = ArrayToBooleanConverter()) }
                }.lparams(width = matchParent) { margin = dip(14) }
            }
        }
    }
}
