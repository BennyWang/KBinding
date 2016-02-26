package com.benny.library.kbinding.converter

/**
 * Created by benny on 11/18/15.
 */

/*interface MultipleConverter<T> : OneWayConverter<T> {
    fun convert(params: Array<Any?>): T

    override fun convert(source: Any?): T {
        throw UnsupportedOperationException()
    }
}
*/
class ArrayToBooleanConverter : OneWayConverter<Array<Any?>, Boolean> {
    override fun convert(params: Array<Any?>): Boolean {
        params.forEach {
            if(it.toString().isEmpty()) return false
        }
        return true
    }
}