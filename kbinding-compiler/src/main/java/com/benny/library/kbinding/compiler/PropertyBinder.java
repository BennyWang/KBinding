package com.benny.library.kbinding.compiler;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by benny on 3/3/16.
 */

public class PropertyBinder extends CommonBinder {

    public static String stripDelegate(String value) {
        return value.substring(0, value.indexOf('$'));
    }

    public static String stripReadWriteProperty(TypeName typeName) {
        String type = typeName.toString();
        return type.substring(type.indexOf(',') + 1, type.indexOf('>'));
    }

    public PropertyBinder(String property) {
        super(stripDelegate(property));
    }

    @Override
    public void generateCode(MethodSpec.Builder builder) {
        if (property.startsWith("is")) {
            builder.addStatement("target.$L($S, target.$L())", ViewModelClass.BIND_PROPERTY_CALL, property, property);
        }else {
            builder.addStatement("target.$L($S, target.get$L())", ViewModelClass.BIND_PROPERTY_CALL, property, StringUtils.capitalize(property));
        }
    }
}
