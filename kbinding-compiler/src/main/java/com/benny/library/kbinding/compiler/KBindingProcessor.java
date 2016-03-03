package com.benny.library.kbinding.compiler;

import com.benny.library.kbinding.annotation.Command;
import com.benny.library.kbinding.annotation.DependsOn;
import com.benny.library.kbinding.annotation.Extract;
import com.benny.library.kbinding.annotation.Property;
import com.google.auto.common.SuperficialValidation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by benny on 3/2/16.
 */

public class KBindingProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment env) {
        super.init(env);

        elementUtils = env.getElementUtils();
        typeUtils = env.getTypeUtils();
        filer = env.getFiler();
        messager = env.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment env) {

        for (Element element : env.getElementsAnnotatedWith(Command.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;

            messager.printMessage(Diagnostic.Kind.NOTE, "Annotated Command Element is " + element + " : Parent is " + element.getEnclosingElement());
        }

        for (Element element : env.getElementsAnnotatedWith(Property.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;

            messager.printMessage(Diagnostic.Kind.NOTE, "Annotated Property is " + element + " : Parent is " + element.getEnclosingElement());
        }

        for (Element element : env.getElementsAnnotatedWith(DependsOn.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;

            messager.printMessage(Diagnostic.Kind.NOTE, "Annotated DependsOn is " + element + " : Parent is " + element.getEnclosingElement());
        }

        for (Element element : env.getElementsAnnotatedWith(Extract.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;

            messager.printMessage(Diagnostic.Kind.NOTE, "Annotated Extract is " + element + " : Parent is " + element.getEnclosingElement());
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(Property.class.getCanonicalName());
            add(DependsOn.class.getCanonicalName());
            add(Extract.class.getCanonicalName());
            add(Command.class.getCanonicalName());
        }};
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}