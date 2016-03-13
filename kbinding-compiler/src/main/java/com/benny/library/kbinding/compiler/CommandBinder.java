package com.benny.library.kbinding.compiler;

import com.benny.library.kbinding.viewmodel.Command;
import com.squareup.javapoet.*;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.apache.commons.lang3.StringUtils;
import rx.functions.Action1;

import javax.lang.model.element.Modifier;
import java.util.List;

/**
 * Created by benny on 3/3/16.
 */

public class CommandBinder implements ViewModelBinder {
    private String property;
    private List<TypeName> parameterTypes;

    public CommandBinder(String property, List<TypeName> parameterTypes) {
        this.property = property;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public void generateCode(MethodSpec.Builder builder) {
        TypeSpec command;
        TypeSpec executor = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(Function1.class, Boolean.class, Unit.class))
                .addMethod(MethodSpec.methodBuilder("invoke")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Boolean.class, "p0")
                        .returns(Unit.class)
                        .addStatement("canExecute.call(p0)")
                        .addStatement("return Unit.INSTANCE")
                        .build())
                .build();

        if(hasParameter()) {
            TypeSpec.Builder commandBuilder = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(ParameterizedTypeName.get(Command.class, Object.class));

            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("execute")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(Object.class, "params")
                        .addParameter(ParameterizedTypeName.get(ClassName.get(Action1.class), WildcardTypeName.supertypeOf(ClassName.get(Boolean.class))), "canExecute", Modifier.FINAL);

            if(parameterTypes.isEmpty()) {
                command = commandBuilder.addMethod(methodBuilder.addStatement("target.$L()", property).build()).build();
            }
            else {
                command = commandBuilder.addMethod(methodBuilder.addStatement("target.$L($L)", property, executor).build()).build();
            }
        }
        else {
            TypeSpec.Builder commandBuilder = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Command.class), parameterTypes.get(0).box()));

            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("execute")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addParameter(parameterTypes.get(0).box(), "params")
                    .addParameter(ParameterizedTypeName.get(ClassName.get(Action1.class), WildcardTypeName.supertypeOf(ClassName.get(Boolean.class))), "canExecute", Modifier.FINAL);

            if(parameterTypes.size() == 1) {
                command = commandBuilder.addMethod(methodBuilder.addStatement("target.$L(params)", property).build()).build();
            }
            else {
                command = commandBuilder.addMethod(methodBuilder.addStatement("target.$L(params, $L)", property, executor).build()).build();
            }
        }
        builder.addStatement("target.$L($S, $L)", ViewModelClass.BIND_COMMAND_CALL, property, command);
    }

    private boolean hasParameter() {
        return parameterTypes.isEmpty() || (parameterTypes.size() == 1 && parameterTypes.get(0).toString().contains("Function1"));
    }
}
