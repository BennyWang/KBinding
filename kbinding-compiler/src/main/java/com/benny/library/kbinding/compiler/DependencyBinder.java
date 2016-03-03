package com.benny.library.kbinding.compiler;

import com.squareup.javapoet.TypeName;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by benny on 3/3/16.
 */

public class DependencyBinder implements ViewModelBinder {
    private String property;
    private String[] dependsOn;
    private TypeName type;

    public DependencyBinder(String property, String[] dependsOn, TypeName type) {
        this.property = property;
        this.dependsOn = dependsOn;
        this.type = type;
    }

    @Override
    public String generateCode() {
        return "target." + ViewModelClass.BIND_PROPERTY_CALL + "(\"" + property + "\", new String[] {\"" + StringUtils.join(Arrays.asList(dependsOn), "\",\"") + "\"}, new Function0<" + type + ">() {\n" +
                "    @Override\n" +
                "    public String invoke() {\n" +
                "        target." + property + ";\n" +
                "    }\n" +
                "});";
    }
}
