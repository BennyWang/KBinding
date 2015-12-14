package com.benny.app.sample

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import com.benny.library.neobinding.bind.BindingContext
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.support.RxFragmentActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.*

import com.benny.app.sample.viewmodel.LoginViewModel
import com.benny.library.neobinding.drawable.*
import com.benny.library.neobinding.view.*
import com.benny.library.neobinding.bind.BindingMode
import com.benny.library.neobinding.converter.*


class MainActivity : RxFragmentActivity(), LoginViewModel.LoginDelegate {
    val viewModel = LoginViewModel(this)

    protected val bindingContext: BindingContext<ActivityEvent> by lazy {
        BindingContext(this, lifecycle(), ActivityEvent.DESTROY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainActivityUI().setContentView(this).bindTo(bindingContext, viewModel)
    }

    override fun onLoginSuccess(user: String) {
        toast("Login success with user " + user)
    }

    override fun onLoginFailed(e: Throwable) {
        toast(e.message ?: "")
    }

    class MainActivityUI : AnkoComponent<MainActivity> {
        val AnkoContext<MainActivity>.editTextStyle: (View) -> Unit get() = {
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

        val AnkoContext<MainActivity>.bgButton: Drawable get() = with(this) {
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

        val AnkoContext<MainActivity>.levelBackground: Drawable get() = with(this) {
            levelList {
                minLevel = 0
                oval {
                    colorResource = R.color.color_red
                    drawableLevel = 1
                }
                oval {
                    colorResource = R.color.color_blue
                    drawableLevel = 2
                }
                oval {
                    colorResource = R.color.color_black
                    drawableLevel = 3
                }
            }
        }

        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            bindableLayout {
                verticalLayout {
                    verticalLayout {
                        backgroundColor = Color.WHITE
                        leftPadding = dip(14)
                        editText {
                            hint = "请输入手机号或者电子邮箱地址"
                            bind(textProp, path="name", mode = BindingMode.TwoWay)
                        }.lparams(width = matchParent)
                        view { backgroundResource = R.color.color_f2 }.lparams(width = matchParent, height = 1)
                        editText {
                            hint = "请输入密码"
                            bind(textProp, path="password", mode = BindingMode.TwoWay)
                        }.lparams(width = matchParent)
                    }.lparams(width = matchParent)
                    textView {
                        text = "登录"
                        textSizeDimen = R.dimen.font_38
                        textColor = Color.WHITE
                        background = bgButton
                        verticalPadding = dip(10.4f)
                        isClickable = true
                        bind(clickProp, path="login")
                        bind(enabledProp, paths=listOf("name", "password"), converter = ArrayToBooleanConverter())
                    }.lparams(width = matchParent) { margin = dip(14) }.let { it.gravity = Gravity.CENTER }
                }.style(editTextStyle)
            }
        }
    }
}
