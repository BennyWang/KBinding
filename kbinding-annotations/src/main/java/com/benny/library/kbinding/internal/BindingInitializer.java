package com.benny.library.kbinding.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by benny on 3/4/16.
 */

public class BindingInitializer {
    public static Map<Class<?>, BindingBuilder> initializers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static void init(Object obj) {
        BindingBuilder builder = initializers.get(obj.getClass());
        if(builder == null) {
            try {
                builder = (BindingBuilder) Class.forName(obj.getClass().getCanonicalName() + "$$KB").newInstance();
            }
            catch (Exception e) {
                builder = new MockBindingBuilder();
            }
            initializers.put(obj.getClass(), builder);
        }
        builder.build(obj);
    }

    public static class MockBindingBuilder implements BindingBuilder<Object> {
        @Override
        public void build(Object target) {
        }
    }
}
