package com.sdl.selenium.extjs;

import com.sdl.selenium.web.Browser;
import com.sdl.selenium.Ignores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyTransformer implements IAnnotationTransformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyTransformer.class);

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        Ignores ignores = testMethod.getAnnotation(Ignores.class);
        if (ignores != null) {
            for (Ignores.Driver ignore : ignores.value()) {
                if (Browser.CHROME.name().equals(ignore.name())) {
                    annotation.setEnabled(false);
                } else if (Browser.FIREFOX.name().equals(ignore.name())) {
                    annotation.setEnabled(false);
                } else if (Browser.IEXPLORE.name().equals(ignore.name())) {
                    annotation.setEnabled(false);
                } else if ("ALL".equals(ignore.name())) {
                    annotation.setEnabled(false);
                }
            }
        }
    }
}
