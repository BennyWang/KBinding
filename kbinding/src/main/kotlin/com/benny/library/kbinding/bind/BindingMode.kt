package com.benny.library.kbinding.bind

/**
 * Created by benny on 12/14/15.
 */

open class BindingMode {
    public companion object {
        public val OneWay: OneWay = OneWay()
        public val TwoWay: TwoWay = TwoWay()
        public val OneWayToSource: OneWayToSource = OneWayToSource()
    }
}

class OneWay : BindingMode()
class TwoWay : BindingMode()
class OneWayToSource : BindingMode()