package com.sdl.weblocator.extjs;

import com.sdl.weblocator.Ignores;
import com.sdl.weblocator.InputData;
import org.apache.log4j.Logger;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyTransformer implements IAnnotationTransformer {
    private static final Logger logger = Logger.getLogger(MyTransformer.class);

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        String browser = InputData.BROWSER;
        Ignores ignores = testMethod.getAnnotation(Ignores.class);
        if (ignores != null) {
            for (Ignores.Driver ignore : ignores.value()) {
                if ("CHROME".equals(ignore.name()) && "*chrome".equals(browser)) {
                    annotation.setEnabled(false);
                } else if ("FIREFOX".equals(ignore.name()) && "*firefox".equals(browser)) {
                    annotation.setEnabled(false);
                } else if ("IE".equals(ignore.name()) && "*iexplore".equals(browser)) {
                    annotation.setEnabled(false);
                } else if ("ALL".equals(ignore.name())) {
                    annotation.setEnabled(false);
                }
            }
        }
    }
}
