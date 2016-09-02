KBinding
======================
使用kotlin实现的Android MVVM框架，基于anko，简单好用。
它依赖我的另一个项目[AutoAdapter](https://github.com/BennyWang/AutoAdapter)（简化adapter创建的库）

## 内容

### BindingMode（绑定模式）

  - OneWay: Binding from model to view（从model到view）
  - TwoWay: Binding from model to view and view to model（双向绑定）
  - OneWayToSource: Binding from view to model（从view到mode）
  - OneTime: Binding from model to view, and auto release after first emit（从model到view）

### Simple Binding（简单绑定）
viewModel中的单个属性或方法与view中的属性或事件绑定

```kotlin
verticalLayout {
    editText {
        bind { text("name", mode = TwoWay) }
    }
    button {
        bind { click("hello") }
    }
}
class SimpleViewModel() : ViewModel() {
    @delegate:Property
    var name: String by Delegates.property("Jason")

    // all the parameter for Command is optional, first parameter pass by event Observable, second parameter is lambda (Boolean) -> Unit
    @Command
    val hello(canExecute: (Boolean) -> Unit) {
        toast("Hello, ${name}!")
    }
}
```

### Multiple Binding（多重绑定）
viewModel中的多个属性与view中的一个属性绑定

```kotlin
//login button enabled only when name and password not empty
class ArrayToBooleanConverter : MultipleConverter<Boolean> {
    override fun convert(params: Array<Any>): Boolean {
        params.forEach {
            if(it.toString().isEmpty()) return false
        }
        return true
    }
}
verticalLayout {
    editText {
        bind { text("name", mode = TwoWay) }
    }
    editText {
            bind { text("password", mode = TwoWay) }
        }
    button {
        //按钮的enable属性同时与viewModel中的name和password绑定
        bind { enabled("name", "password", mode = OneWay, converter = ArrayToBooleanConverter()) }
        bind { click("login") }
    }
}
class LoginViewModel() : ViewModel() {
    @delegate:Property
    var name: String by Delegates.property("xxx@xxxx.co")

    @delegate:Property
    var password: String by Delegates.property("xxxxxx")

    @Command
    val login() {
        //login processing
    }
}
```

### DependencyProperty and ExtractProperty
```kotlin
class StockViewModel() : ViewModel() {
    //将生成绑定为stock属性，会为其类中所属的name和price属性生成绑定，如果hasPrefix = true，绑定的字符串会变为stock.name和stock.price
    @delegate:ExtractProperty(
        "name", "price",
        hasPrefix = false
    )
    var stock: Stock? by Delegates.property()


    //将生成绑定为nameAndSymbol属性，其依赖stock属性，stock变化时nameAndSymbol也会随之产生变化
    @delegate:DependencyProperty("stock")
    var nameAndSymbol: String by Delegates.property { stock?.name + stock?.symbol }
}
```

### Wait/Until

```kotlin
//wait/until just like OneTime binding, but it need apply action, for example below, it wait for market from model, then decide how to display
relativeLayout {
    wait { until("market", converter = viewOfMarket) { inflate(it, this@verticalLayout) }  }
}
```

## Extend Binding Property

### Event

```kotlin
    fun View.click(path: String) : PropertyBinding = commandBinding(path, clicks(), enabled())
```

Property

```kotlin
    fun View.enabled(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, enabled(), false, converter)

    //this implements four binding mode for TextView, if just need OneWay mode, remove last three lines, some for other mode
    fun TextView.text(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<out CharSequence> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, text(), false, converter)
    fun TextView.text(vararg paths: String, mode: OneTime, converter: OneWayConverter<out CharSequence> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(paths, text(), true, converter)
    fun TextView.text(path: String, mode: OneWayToSource, converter: OneWayConverter<*> = EmptyOneWayConverter<String>()) : PropertyBinding = oneWayPropertyBinding(path, textChanges2(), converter)
    fun TextView.text(path: String, mode: TwoWay, converter: TwoWayConverter<String, *> = EmptyTwoWayConverter<String, String>()) : PropertyBinding = twoWayPropertyBinding(path, textChanges2(), text(), converter)
```

## Using with Gradle
```gradle
// library with Annotation process dose not upload to bintray yet, coming soon!
dependencies {
    compile 'com.benny.library:kbinding:0.2.0'
    kapt 'com.benny.library:kbinding-compiler:0.2.2'

    // for common bindings, View, TextView, and ...
    compile 'com.benny.library:kbinding-common:0.2.2'
    // for recyclerview bindings
    compile 'com.benny.library:kbinding-recyclerview-v7:0.2.2'
    // for support v4 bindings
    compile 'com.benny.library:kbinding-support-v4:0.2.2'
}
```

## Contribute
KBinding仅仅是个开始，所有人都可以fork it and pull requests to me.

## Discussion

### QQ群: 516157585
