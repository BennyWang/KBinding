# KBinding
Android View Model binding framework write in kotlin, Simple but powerful.

## Usage

### compile 'com.benny.kbinding:library:0.1.0'

UI Component

    class MainActivityUI : ViewBinderComponent<MainActivity> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            verticalLayout {
                verticalLayout {
                    backgroundColor = Color.WHITE
                    leftPadding = dip(14)
                    editText {
                        bind { text(path="name", mode = BindingMode.TwoWay) }
                    }.lparams(width = matchParent)
                    view { backgroundResource = R.color.color_f2 }.lparams(width = matchParent, height = 1)
                    editText {
                        bind{ text(path="password", mode = BindingMode.TwoWay) }
                    }.lparams(width = matchParent)
                }.lparams(width = matchParent)
                textView {
                    text = "Login"
                    textSizeDimen = R.dimen.font_38
                    textColor = Color.WHITE
                    verticalPadding = dip(10.4f)
                    isClickable = true
                    bind { click("login") }
                    bind { enabled(paths=listOf("name", "password"), converter = ArrayToBooleanConverter()) }
                }.lparams(width = matchParent) { margin = dip(14) }.let { it.gravity = Gravity.CENTER }
            }
        }
    }
    
ViewModel

    class LoginViewModel() : ViewModel<String>() {
        var name: String by Delegates.bindProperty("name", "xxxxxxx@xxxxx.com")
        var password: String by Delegates.bindProperty("password", "xxxxxxxxx")
        val login: Command by Delegates.bindCommand("login", Command { it ->
            // xxx Do what you want to do
        })
    }
    
Binding

    MainActivityUI().setContentView(activity).bindTo(LoginViewModel())
    
## Extend Binding Property 

Event
    
    fun View.click(path: String) : PropertyBinding = commandBinding(path, clicks(), enabled())

OneWay Property

    fun View.enabled(path: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, enabled(), converter)
    fun View.enabled(paths: List<String>, converter: MultipleConverter<Boolean>) : PropertyBinding = multiplePropertyBinding(paths, enabled(), converter)
    
TwoWay Property

    fun TextView.text(path: String, mode: OneWay, converter: OneWayConverter<CharSequence> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(path, text(), converter)
    fun TextView.text(path: String, mode: OneWayToSource, converter: OneWayConverter<*> = EmptyOneWayConverter<String>()) : PropertyBinding = oneWayPropertyBinding(path, textChanges2(), converter)
    fun TextView.text(path: String, mode: TwoWay, converter: TwoWayConverter<String, *> = EmptyTwoWayConverter<String, String>()) : PropertyBinding = twoWayPropertyBinding(path, textChanges2(), text(), converter)
    fun TextView.text(paths: List<String>, converter: MultipleConverter<out CharSequence>) : PropertyBinding = multiplePropertyBinding(paths, text(), converter)    


## Contribute

Now is just the beginning of KBinding, so everyone interested in this library, just fork it and pull requests to me.
Let's make it a little better.

## Discussion

###QQ Group: 516157585
