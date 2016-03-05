package com.benny.library.kbinding.compiler;

import com.benny.library.kbinding.annotation.Command;
import com.benny.library.kbinding.annotation.DependencyProperty;
import com.benny.library.kbinding.annotation.ExtractProperty;
import com.benny.library.kbinding.annotation.Property;
import com.google.auto.common.SuperficialValidation;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;

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
        Map<TypeElement, ViewModelClass> targetClassMap = new LinkedHashMap<>();

        for (Element element : env.getElementsAnnotatedWith(Property.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;

            parsePropertyBinder(element, targetClassMap);
        }

        for (Element element : env.getElementsAnnotatedWith(ExtractProperty.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            parseExtractPropertyBinder(element, targetClassMap);
        }

        for (Element element : env.getElementsAnnotatedWith(DependencyProperty.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;

            parseDependencyPropertyBinder(element, targetClassMap);
        }

        for (Element element : env.getElementsAnnotatedWith(Command.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;

            parseCommandBinder(element, targetClassMap);
        }

        for (Map.Entry<TypeElement, ViewModelClass> entry : targetClassMap.entrySet()) {
            TypeElement typeElement = entry.getKey();
            ViewModelClass bindingClass = entry.getValue();

            try {
                bindingClass.brewJava().writeTo(filer);
            } catch (IOException e) {
                error(typeElement, "Unable to write view binder for type %s: %s", typeElement,
                        e.getMessage());
            }
        }

        return true;
    }

    private void parsePropertyBinder(Element element, Map<TypeElement, ViewModelClass> targetClassMap) {
        ViewModelClass viewModelClass = getOrCreateViewModelClass(targetClassMap, (TypeElement)element.getEnclosingElement());
        viewModelClass.addBinder(new PropertyBinder(element.getSimpleName().toString()));
    }

    private void parseDependencyPropertyBinder(Element element, Map<TypeElement, ViewModelClass> targetClassMap) {
        ViewModelClass viewModelClass = getOrCreateViewModelClass(targetClassMap, (TypeElement)element.getEnclosingElement());
        DependencyProperty dependencyProperty = element.getAnnotation(DependencyProperty.class);
        viewModelClass.addBinder(new DependencyPropertyBinder(element.getSimpleName().toString(), dependencyProperty.value()));
    }

    private void parseExtractPropertyBinder(Element element, Map<TypeElement, ViewModelClass> targetClassMap) {
        ViewModelClass viewModelClass = getOrCreateViewModelClass(targetClassMap, (TypeElement)element.getEnclosingElement());
        ExtractProperty dependencyProperty = element.getAnnotation(ExtractProperty.class);
        viewModelClass.addBinder(new ExtractPropertyBinder(element.getSimpleName().toString(), dependencyProperty.value(), dependencyProperty.hasPrefix()));
    }

    private void parseCommandBinder(Element element, Map<TypeElement, ViewModelClass> targetClassMap) {
        if (!(element instanceof ExecutableElement) || element.getKind() != ElementKind.METHOD) {
            throw new IllegalStateException("@Command annotation must be on a method.");
        }
        ExecutableElement executableElement = (ExecutableElement) element;
        ViewModelClass viewModelClass = getOrCreateViewModelClass(targetClassMap, (TypeElement)element.getEnclosingElement());
        List<TypeName> parameterTypes = new ArrayList<>();
        for (VariableElement ve : executableElement.getParameters()) {
            parameterTypes.add(TypeName.get(ve.asType()));
        }
        viewModelClass.addBinder(new CommandBinder(executableElement.getSimpleName().toString(), parameterTypes));
    }

    private ViewModelClass getOrCreateViewModelClass(Map<TypeElement, ViewModelClass> targetClassMap, TypeElement enclosingElement) {
        ViewModelClass bindingClass = targetClassMap.get(enclosingElement);
        if (bindingClass == null) {
            String targetType = enclosingElement.getQualifiedName().toString();
            String classPackage = getPackageName(enclosingElement);
            String className = getClassName(enclosingElement, classPackage) + ViewModelClass.VIEW_MODEL_CLASS_SUFFIX;

            bindingClass = new ViewModelClass(classPackage, className, targetType);
            targetClassMap.put(enclosingElement, bindingClass);
        }
        return bindingClass;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            add(Property.class.getCanonicalName());
            add(DependencyProperty.class.getCanonicalName());
            add(ExtractProperty.class.getCanonicalName());
            add(Command.class.getCanonicalName());
        }};
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}