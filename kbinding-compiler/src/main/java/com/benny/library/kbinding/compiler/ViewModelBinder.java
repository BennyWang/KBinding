package com.benny.library.kbinding.compiler;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by benny on 3/3/16.
 */

public interface ViewModelBinder {
    void generateCode(MethodSpec.Builder builder);
    FieldSpec.Builder generateConstantField();
}
