package com.benny.library.neobinding.kotlin.bind

import android.os.Build
import android.view.View
import com.benny.library.neobinding.kotlin.view.ViewFinder
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by benny on 11/18/15.
 */

public abstract class BindableView {
    private val oneWayPropertyBindings = ArrayList<OneWayPropertyBinding<*, *>>()
    private val multiplePropertyBindings = ArrayList<OneWayPropertyBinding<*, *>>()
    private val twoWayPropertyBindings = ArrayList<TwoWayPropertyBinding<*, *>>()
    private var commandBindings = ArrayList<CommandBinding<*>>()

    public companion object {
        private val sNextGeneratedId: AtomicInteger = AtomicInteger(1);
        /**
         * 动态生成View ID
         * API LEVEL 17 以上View.generateViewId()生成
         * API LEVEL 17 以下需要手动生成
         */
        public fun generateViewId(): Int {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                while (true) {
                    val result: Int = sNextGeneratedId.get();
                    // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                    var newValue = result + 1;
                    if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                    if (sNextGeneratedId.compareAndSet(result, newValue)) {
                        return result;
                    }
                }
            }

            return View.generateViewId();
        }
    }


    fun oneWayPropertyBindings(): List<OneWayPropertyBinding<*, *>> {
        return oneWayPropertyBindings
    }

    fun twoWayPropertyBindings(): List<TwoWayPropertyBinding<*, *>> {
        return twoWayPropertyBindings
    }

    fun multiplePropertyBindings(): List<OneWayPropertyBinding<*, *>> {
        return multiplePropertyBindings
    }

    fun commandBindings(): List<CommandBinding<*>> {
        return commandBindings
    }

    protected fun addOneWayPropertyBinding(oneWayPropertyBinding: OneWayPropertyBinding<*, *>) {
        oneWayPropertyBindings.add(oneWayPropertyBinding)
    }

    protected fun addTwoWayPropertyBinding(twoWayPropertyBinding: TwoWayPropertyBinding<*, *>) {
        twoWayPropertyBindings.add(twoWayPropertyBinding)
    }

    protected fun addMultiplePropertyBinding(multiplePropertyBinding: OneWayPropertyBinding<*, *>) {
        multiplePropertyBindings.add(multiplePropertyBinding)
    }

    protected fun addCommandBinding(commandBinding: CommandBinding<*>) {
        commandBindings.add(commandBinding)
    }

    abstract fun inject(bindingContext: BindingContext<*>, viewFinder: ViewFinder)
}