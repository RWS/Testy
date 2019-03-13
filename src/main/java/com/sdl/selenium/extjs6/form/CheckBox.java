package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.ICheck;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CheckBox extends WebLocator implements ICheck {

    public CheckBox() {
        applyVersion();
        setClassName("CheckBox");
        if ("6.7.0".equals(WebLocatorConfig.getExtJsVersion())) {
            setTag("input");
            setType("checkbox");
        } else {
            setBaseCls("x-form-checkbox");
        }
    }

    public CheckBox(WebLocator container) {
        this();
        setContainer(container);
    }

    public CheckBox(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
        if (searchTypes.length == 0) {
            searchTypes = new SearchType[]{SearchType.DEEP_CHILD_NODE_OR_SELF};
        } else {
            List<SearchType> types = new ArrayList<>(Arrays.asList(searchTypes));
            types.add(SearchType.DEEP_CHILD_NODE_OR_SELF);
            searchTypes = types.toArray(new SearchType[0]);
        }
        setLabel(label, searchTypes);
    }

    public CheckBox(String boxLabel, WebLocator container) {
        this(container);
        setLabel(boxLabel);
        if ("6.7.0".equals(WebLocatorConfig.getExtJsVersion())) {
            setLabelPosition("/..//");
        } else {
            setLabelPosition("/../");
        }
    }

    @Override
    public boolean isSelected() {
        if ("6.7.0".equals(WebLocatorConfig.getExtJsVersion())) {
            return executor.isSelected(this);
        } else {
            WebLocator el = new WebLocator(this).setElPath("/../input");
            String select = el.getAttribute("aria-checked");
            return select != null && select.contains("true");
        }
    }

    @Override
    public boolean isEnabled() {
        String cls = getAttributeClass();
        return (cls != null && !cls.contains("disabled")) || getAttribute("disabled") == null;
    }

    public void applyVersion() {
        Class<?> clazz = this.getClass();
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (Annotation a : annotations) {
            if (a.annotationType().isAnnotation()) {
                Utils.sleep(1);
            }
        }
        if (clazz.isAnnotationPresent(Version.class)) {
            Version annotation = clazz.getAnnotation(Version.class);
        }
        for (Constructor c : clazz.getDeclaredConstructors()) {
            if (c.isAnnotationPresent(Version.class)) {
                c.setAccessible(true);
                Annotation annotation1 = c.getAnnotation(Version.class);
                Version annotation = (Version) annotation1;
                String version = annotation.version();
                log.debug("{}", version);
                WebLocatorConfig.setExtJsVersion(version);
            }
        }

        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Version.class)) {
                m.setAccessible(true);
            }
        }

        for (Class c : clazz.getDeclaredClasses()) {
            for (Field f : c.getDeclaredFields()) {
                if (f.isAnnotationPresent(Version.class)) {
                    f.setAccessible(true);
                }
            }
        }

        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Version.class)) {
//                f.setAccessible(true);
                Version annotation = f.getAnnotation(Version.class);
                String version = annotation.version();
                log.debug("{}", version);
                WebLocatorConfig.setExtJsVersion(version);
            }
        }
    }

//    @Version(version = "6.0.2")
//    static CheckBox c = new CheckBox();

//    public static void main(String[] args) {
////        @Version(version = "6.0.2")
//        CheckBox c = new CheckBox().setVersion("6.0.2");
//        log.debug("{}", c.getXPath());
//    }
}