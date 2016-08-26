package com.benny.library.kbinding.compiler;

import com.benny.library.kbinding.bind.BindingBuilder;
import com.squareup.javapoet.*;

import java.util.ArrayList;
import java.util.List;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by benny on 3/3/16.
 */

public class ViewModelClass {
    public static final String BIND_PROPERTY_CALL = "bindPropertyV2";
    public static final String BIND_COMMAND_CALL = "bindCommandV2";
    public static final String VIEW_MODEL_CLASS_SUFFIX = "$$KB";

    private final String classPackage;
    private final String className;
    private final String targetClass;

    private List<ViewModelBinder> binders = new ArrayList<>();

    public ViewModelClass(String classPackage, String className, String targetClass) {
        this.classPackage = classPackage;
        this.className = className;
        this.targetClass = targetClass;
    }

    public void addBinder(ViewModelBinder binder) {
        binders.add(binder);
    }

    public JavaFile brewJava() {
        TypeSpec.Builder result = TypeSpec.classBuilder(className)
                .addModifiers(PUBLIC);

        result.addSuperinterface(ParameterizedTypeName.get(ClassName.get(BindingBuilder.class), TypeVariableName.get(targetClass)));
        result.addMethod(createBuildMethod());
        createBuildField(result);

        return JavaFile.builder(classPackage, result.build())
                .addFileComment("Generated code from KBinding. Do not modify!")
                .build();
    }

    private MethodSpec createBuildMethod() {
        MethodSpec.Builder result = MethodSpec.methodBuilder("build")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .addParameter(TypeVariableName.get(targetClass), "target", FINAL);

        for (ViewModelBinder binder : binders) {
            binder.generateCode(result);
        }
        return result.build();
    }

    private void createBuildField(TypeSpec.Builder builder){
        for (ViewModelBinder binder : binders) {
            builder.addField(binder.generateConstantField().build());
        }
    }
}
