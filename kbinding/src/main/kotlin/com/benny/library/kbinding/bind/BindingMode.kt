package com.benny.library.kbinding.bind

/**
 * Created by benny on 12/14/15.
 */

open class BindingMode {
    companion object {
        val OneWay: OneWay = OneWay()
        val TwoWay: TwoWay = TwoWay()
        val OneWayToSource: OneWayToSource = OneWayToSource()
        val OneTime: OneTime = OneTime()
    }
}

class OneWay : BindingMode()
class TwoWay : BindingMode()
class OneWayToSource : BindingMode()
class OneTime : BindingMode()
