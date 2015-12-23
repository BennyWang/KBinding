package com.benny.app.sample.ui.dialog

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.benny.app.sample.R
import com.benny.library.neobinding.bind.BindingDisposer
import com.benny.library.neobinding.bind.BindingMode
import com.benny.library.neobinding.bind.ViewModel
import com.benny.library.neobinding.converter.ArrayToBooleanConverter
import com.benny.library.neobinding.drawable.borderRoundRect
import com.benny.library.neobinding.drawable.stateList
import com.benny.library.neobinding.extension.bind
import com.benny.library.neobinding.extension.click
import com.benny.library.neobinding.extension.enabled
import com.benny.library.neobinding.extension.text
import com.benny.library.neobinding.view.ViewBinderComponent
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.act

/**
 * Created by benny on 12/23/15.
 */

class SampleDialog(val viewModel: ViewModel) : DialogFragment() {
    var bindingDisposer: BindingDisposer = BindingDisposer()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinder = LoginFragmentUI().createViewBinder(act, this)
        viewBinder.bindTo(bindingDisposer, viewModel)
        return viewBinder.view
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingDisposer.unbind()
    }

    class LoginFragmentUI : ViewBinderComponent<SampleDialog> {
        val AnkoContext<*>.editTextStyle: (View) -> Unit get() = {
            v: View ->
            with(this) {
                if (v is EditText) with(v) {
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
                    bind { enabled(paths= listOf("name", "password"), converter = ArrayToBooleanConverter()) }
                }.lparams(width = matchParent) { margin = dip(14) }.let { it.gravity = Gravity.CENTER }
            }.style(editTextStyle)
        }
    }
}