KBinding
======================

Android View Model binding framework write in kotlin, base on anko, simple but powerful.

## Contents

### View Model Class

```kotlin
class LoginViewModel() : ViewModel<String>() {
    var name: String by Delegates.bindProperty("name", "xxxxxxx@xxxxx.com")
    var password: String by Delegates.bindProperty("password", "xxxxxxxxx")
    val login: Command by Delegates.bindCommand("login", Command { it ->
        // xxx Do what you want to do
    })
    val hello: Command by Delegates.bindCommand("hello", Command { it ->
        toast("Hello, ${name}!")
    })
}
```

### Bind

```kotlin
verticalLayout {
    editText {
        bind { text(path="name", mode = TwoWay) }
    }
    button {
        bind { click("hello") }
    }
}
```

### Wait/Until

```kotlin
relativeLayout {
    //wait market and then decide which to inflate, just one time binding. 
    wait { until("market", converter = viewOfMarket) { inflate(it, this@verticalLayout) }  }
}
```
    
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

###QQ Group: 516157585
