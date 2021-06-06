package com.lzg.spring.assemble.myimport;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author lzg
 * @Date 2021-06-05 16:43
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.lzg.spring.assemble.myimport.Test1"};
    }
}
