# NeoBinding
Android View Model binding framework, Simple but powerful.

## Usage

UI Component

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
    
ViewModel

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
    
Binding

    MainActivityUI().setContentView(activity).bindTo(bindingContext, LoginViewModel(delegate object))
    
