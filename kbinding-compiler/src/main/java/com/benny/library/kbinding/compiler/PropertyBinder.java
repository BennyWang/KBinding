package com.benny.library.kbinding.compiler;

import com.squareup.javapoet.TypeName;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by benny on 3/3/16.
 */

public class PropertyBinder implements ViewModelBinder {
    private String property;
    private String type;

    public PropertyBinder(String property, String type) {
        this.property = property.substring(0, property.indexOf('$'));
        this.type = type.substring(type.indexOf(',') + 1, type.indexOf('>'));
    }

    @Override
    public String generateCode() {
        return "target." + ViewModelClass.BIND_PROPERTY_CALL + "(\"" + property + "\", target.get" + StringUtils.capitalize(property) + "())";
    }
}
