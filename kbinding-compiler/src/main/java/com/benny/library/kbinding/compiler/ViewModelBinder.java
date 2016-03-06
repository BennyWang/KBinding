package com.benny.library.kbinding.compiler;

import com.squareup.javapoet.MethodSpec;

/**
 * Created by benny on 3/3/16.
 */

public interface ViewModelBinder {
    void generateCode(MethodSpec.Builder builder);
}
