KBinding
======================

Android View Model binding framework write in kotlin, base on anko, simple but powerful.

## Contents

### BindingMode

  - OneWay: Binding from model to view
  - TwoWay: Binding from model to view and view to model
  - OneWayToSource: Binding from view to model
  - OneTime: Binding from model to view, and auto release after first emit

### Simple Binding

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
    var name: String by bindProperty("name") { "Jason" }
    val hello: Command by bindCommand("hello") { params, canExecute ->
        toast("Hello, ${name}!")
    }
}
```

### Multiple Binding

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
        bind { enabled("name", "password", mode = OneWay, converter = ArrayToBooleanConverter()) }
        bind { click("login") }
    }
}
class LoginViewModel() : ViewModel() {
    var name: String by bindProperty("name") { "xxx@xxxx.com" }
    var password: String by bindProperty("password") { "xxxxxx" }
    val login: Command by bindCommand("login") { params, canExecute ->
        //login processing
    }
}
```

### View Model property depends on other properties

```kotlin
//name and price property will be updated when new stock is set
class StockViewModel() : ViewModel() {
    var stock: Stock? by bindProperty("stock")
    val name: String? by bindProperty("name", "stock") { stock!!.name }
    val price: Float by bindProperty("price", "stock") { stock!!.price }
}
```

### Wait/Until

```kotlin
//wait/until just like OneTime binding, but it need apply action, for example below, it wait for market from model, then decide how to display
relativeLayout {
    wait { until("market", converter = viewOfMarket) { inflate(it, this@verticalLayout) }  }
}
```
    
## Extend Binding Property(Depend on RxBinding heavily)

Event

```kotlin   
    fun View.click(path: String) : PropertyBinding = commandBinding(path, clicks(), enabled())
```  

Property

```kotlin
    fun View.enabled(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<Boolean> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(enabled(), false, converter, *paths) 
    
    //this implements four binding mode for TextView, if just need OneWay mode, remove last three lines, some for other mode
    fun TextView.text(vararg paths: String, mode: OneWay = BindingMode.OneWay, converter: OneWayConverter<out CharSequence> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(text(), false, converter, *paths)
    fun TextView.text(vararg paths: String, mode: OneTime, converter: OneWayConverter<out CharSequence> = EmptyOneWayConverter()) : PropertyBinding = oneWayPropertyBinding(text(), true, converter, *paths)
    fun TextView.text(path: String, mode: OneWayToSource, converter: OneWayConverter<*> = EmptyOneWayConverter<String>()) : PropertyBinding = oneWayPropertyBinding(path, textChanges2(), converter)
    fun TextView.text(path: String, mode: TwoWay, converter: TwoWayConverter<String, *> = EmptyTwoWayConverter<String, String>()) : PropertyBinding = twoWayPropertyBinding(path, textChanges2(), text(), converter) 
```

## Using with Gradle

```gradle
dependencies {
    compile 'com.benny.kbinding:library:0.1.0'
}
```

## Contribute

Now is just the beginning of KBinding, so everyone interested in this library, just fork it and pull requests to me.
Let's make it a little better.

## Discussion

### QQ Group: 516157585
