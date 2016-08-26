package com.benny.library.kbinding.compiler;

import com.squareup.javapoet.*;
import kotlin.jvm.functions.Function0;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Modifier;
import java.util.Arrays;

/**
 * Created by benny on 3/3/16.
 */

public class DependencyPropertyBinder extends CommonBinder {
    private String[] dependsOn;

    public DependencyPropertyBinder(String property, String[] dependsOn) {
        super(PropertyBinder.stripDelegate(property));
        this.dependsOn = dependsOn;
    }

    @Override
    public void generateCode(MethodSpec.Builder builder) {
        TypeSpec getter = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(Function0.class, Object.class))
                .addMethod(MethodSpec.methodBuilder("invoke")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(Object.class)
                        .addStatement("return target.get$L()", StringUtils.capitalize(property))
                        .build())
                .build();

        builder.addStatement("target.$L($S, new String[] {$S}, $L)",
                ViewModelClass.BIND_PROPERTY_CALL, property, StringUtils.join(Arrays.asList(dependsOn), "\",\""), getter);
    }
}
