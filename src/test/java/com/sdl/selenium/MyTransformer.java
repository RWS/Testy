package com.sdl.selenium;

import com.sdl.selenium.web.Browser;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyTransformer implements IAnnotationTransformer {

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
