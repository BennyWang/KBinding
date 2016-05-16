package com.benny.library.kbinding.bind

/**
 * Created by benny on 3/4/16.
 */

object BindingInitializer {
    var builders: MutableMap<Class<*>, BindingBuilder<*>> = mutableMapOf()

    @SuppressWarnings("unchecked")
    fun init(obj: Any) {
        getOrCreateBuilder(obj.javaClass).build(obj)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getOrCreateBuilder(clazz: Class<T>): BindingBuilder<T> {
        return builders.getOrPut(clazz, {
            try {
                return Class.forName(clazz.name + "\$\$KB").newInstance() as BindingBuilder<T>
            } catch (e: Exception) {
                return MockBindingBuilder()
            }
        }) as BindingBuilder<T>
    }

    class MockBindingBuilder<T> : BindingBuilder<T> {
        override fun build(target: T) {
        }
    }
}
