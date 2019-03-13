package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
public class CheckBoxTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        WebLocatorConfig.setExtJsVersion("6.0.2");
        return new Object[][]{
                {new CheckBox(), "//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox().setClasses("CheckBoxClass"), "//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ') and contains(concat(' ', @class, ' '), ' CheckBoxClass ')]"},
                {new CheckBox(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox(container).setElPath("//table//tr[1]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new CheckBox(container, "CheckBox"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'CheckBox') or count(*//text()[contains(.,'CheckBox')]) > 0)]//following-sibling::*//*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
                {new CheckBox("BoxLabel", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='BoxLabel']/../*[contains(concat(' ', @class, ' '), ' x-form-checkbox ')]"},
        };
    }

//    @Test(dataProvider = "testConstructorPathDataProvider")
//    public void getPathSelectorCorrectlyFromConstructors(CheckBox checkBox, String expectedXpath) {
//        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
//    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
//        WebLocatorConfig.setExtJsVersion("6.7.0");
        return new Object[][]{
                {new @Version CheckBox(), "//input[@type='checkbox']"},
                {new @Version CheckBox().setClasses("CheckBoxClass"), "//input[contains(concat(' ', @class, ' '), ' CheckBoxClass ') and @type='checkbox']"},
                {new @Version CheckBox(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//input[@type='checkbox']"},
                {new @Version CheckBox(container).setElPath("//table//tr[1]"), "//*[contains(concat(' ', @class, ' '), ' container ')]//table//tr[1]"},
                {new @Version CheckBox(container, "CheckBox"), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'CheckBox') or count(*//text()[contains(.,'CheckBox')]) > 0)]//following-sibling::*//input[@type='checkbox']"},
                {new @Version CheckBox("BoxLabel", container), "//*[contains(concat(' ', @class, ' '), ' container ')]//label[text()='BoxLabel']/..//input[@type='checkbox']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(CheckBox checkBox, String expectedXpath) {
        assertThat(checkBox.getXPath(), equalTo(expectedXpath));
    }

    @Version(version = "6.0.2")
    private CheckBox checkBox  = new CheckBox();

    @Test
    public void test() {
        showAnnotation(this);
        checkBox = new CheckBox();
        log.debug("{}", checkBox.getXPath());
    }

    private static <T> void showAnnotation(T t) {
        Class<? extends Object> aClass = t.getClass();
        if (aClass.isAnnotationPresent(Version.class)) {
            Version version = aClass.getAnnotation(Version.class);
        }

        for (Field f : aClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(Version.class)) {
                Version annotation = f.getAnnotation(Version.class);
            }
        }

        for (Constructor f : aClass.getDeclaredConstructors()) {
            if (f.isAnnotationPresent(Version.class)) {
                Version annotation = (Version) f.getAnnotation(Version.class);
            }
        }

        for (Version f : aClass.getDeclaredAnnotationsByType(Version.class)) {
            f.version();
        }

    }
}
