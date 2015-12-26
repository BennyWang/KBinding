package com.benny.app.sample.ui.fragment

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.benny.app.sample.R
import com.benny.app.sample.SampleApplication
import com.benny.app.sample.ui.dialog.SampleDialog
import org.jetbrains.anko.*

import com.benny.app.sample.viewmodel.LoginViewModel
import com.benny.library.kbinding.bind.BindingMode
import com.benny.library.kbinding.converter.ArrayToBooleanConverter
import com.benny.library.kbinding.dsl.*
import com.benny.library.kbinding.view.ViewBinderComponent
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast

class LoginFragment : BaseFragment(), LoginViewModel.LoginDelegate {
    val viewModel = LoginViewModel(this)
    var contentView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("LoginFragment", "onCreateView")

        if(contentView == null) {
            contentView = LoginFragmentUI().createViewBinder(act, this).bindTo(viewModel)
        }
        return contentView
    }

    override fun onLoginSuccess(user: String) {
        toast("Login success with user " + user)

        val dialogFragment: SampleDialog = SampleDialog(viewModel);
        dialogFragment.show(childFragmentManager, "Sample Fragment");
    }

    override fun onLoginFailed(e: Throwable) {
        toast(e.message ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LoginFragment", "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingDisposer.unbind()
        Log.d("LoginFragment", "onDestroy")
        SampleApplication.getRefWatcher(context).watch(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LoginFragment", "onDestroyView")

    }

    class LoginFragmentUI : ViewBinderComponent<LoginFragment> {
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
                        bind { text(path="name", mode = TwoWay) }
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
