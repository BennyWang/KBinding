package com.benny.library.kbinding.compiler;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import kotlin.jvm.functions.Function0;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Modifier;

/**
 * Created by benny on 3/3/16.
 */

public class ExtractPropertyBinder implements ViewModelBinder {
    private String[] properties;
    private String property;

    public ExtractPropertyBinder(String property, String[] properties) {
        this.property = PropertyBinder.stripDelegate(property);
        this.properties = properties;
    }

    @Override
    public void generateCode(MethodSpec.Builder builder) {
        builder.addStatement("target.$L($S, target.get$L())", ViewModelClass.BIND_PROPERTY_CALL, property, StringUtils.capitalize(property));

        for(String childProperty : properties) {
            TypeSpec getter = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(ParameterizedTypeName.get(Function0.class, Object.class))
                    .addMethod(MethodSpec.methodBuilder("invoke")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(Object.class)
                            .addStatement("return target.get$L() != null ? target.get$L().get$L() : null", StringUtils.capitalize(property),
                                    StringUtils.capitalize(property), StringUtils.capitalize(childProperty))
                            .build())
                    .build();

            builder.addStatement("target.$L($S, new String[] {$S}, $L)",
                    ViewModelClass.BIND_PROPERTY_CALL, property + "." + childProperty, property, getter);
        }
    }
}
