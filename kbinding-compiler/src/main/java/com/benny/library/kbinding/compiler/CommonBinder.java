package com.benny.library.kbinding.compiler;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;

/**
 * Created by ldy on 16/8/18.
 */
public abstract class CommonBinder implements ViewModelBinder {
    protected String property;
    public CommonBinder(String property){
        this.property = property;
    }

    @Override
    public FieldSpec.Builder generateConstantField() {
        return FieldSpec.builder(TypeVariableName.get("String"),"k_"+property, Modifier.PUBLIC,Modifier.STATIC,Modifier.FINAL)
                .initializer("$S",property);
    }
}
