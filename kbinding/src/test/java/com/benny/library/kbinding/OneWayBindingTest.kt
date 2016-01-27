package com.benny.library.kbinding

import android.os.Build
import com.benny.library.kbinding.bind.BindingAssembler
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.bind.ViewModel
import com.benny.library.kbinding.bind.oneWayPropertyBinding
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import rx.functions.Action1

/**
 * Created by benny on 12/23/15.
 */

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class,sdk = intArrayOf(Build.VERSION_CODES.KITKAT))
class OneWayBindingTest  {
    class TestViewModel : ViewModel() {
        var integer: Int by bindProperty("integer") { 3 }
        var string: String by bindProperty("string") { "hello" }
    }

    val bindingDisposer = BindingDisposer()
    val bindingAssembler = BindingAssembler()
    val testModel = TestViewModel()
    var integer: Int = 0
    var string: String = ""

    @Before
    fun setUp() {
        bindingAssembler.addBinding(oneWayPropertyBinding(arrayOf("integer"), Action1<Int> { integer = it }, false))
        bindingAssembler.addBinding(oneWayPropertyBinding(arrayOf("string"), Action1<String> { string = it }, false))
    }

    @After
    fun tearDown() {
        bindingDisposer.unbind()
    }

    @Test
    public fun test() {
        bindingAssembler.bindTo(bindingDisposer, testModel)
        testModel.integer = 3
        Assert.assertEquals(3, integer)
        testModel.string = "hello test"
        Assert.assertEquals("hello test", string)
    }
}