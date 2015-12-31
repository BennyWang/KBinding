package test.com.benny.library.kbinding

import android.test.InstrumentationTestCase
import com.benny.library.kbinding.bind.BindingAssembler
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.ViewModel
import com.benny.library.kbinding.bind.oneWayPropertyBinding
import rx.functions.Action1
import kotlin.properties.Delegates

/**
 * Created by benny on 12/23/15.
 */
class OneWayBindingTest : InstrumentationTestCase() {
    class TestViewModel : ViewModel() {
        var integer: Int by bindProperty("integer", 3)
        var string: String by bindProperty("string", "hello")
    }

    val bindingDisposer = BindingDisposer()
    val bindingAssembler = BindingAssembler()
    val testModel = TestViewModel()
    var integer: Int = 0
    var string: String = ""

    override fun setUp() {
        super.setUp()
        bindingAssembler.addBinding(oneWayPropertyBinding("integer", Action1<Int> { integer = it }, false))
        bindingAssembler.addBinding(oneWayPropertyBinding("string", Action1<String> { string = it }, false))
    }

    override fun tearDown() {
        super.tearDown()
        bindingDisposer.unbind()
    }

    public fun test() {
        bindingAssembler.bindTo(bindingDisposer, testModel)
        testModel.integer = 3
        assertEquals(3, integer)
        testModel.string = "hello test"
        assertEquals("hello test", string)
    }
}