package com.sdl.selenium.extjs3.panel;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.web.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PanelTest {
    public static ExtJsComponent container = new ExtJsComponent(By.classes("container"));

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Panel(), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(new Panel()), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel("PanelTest"), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(*[contains(@class,'x-panel-header') or contains(@class, '-tl')]//*[text()='PanelTest']) > 0 and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(container), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(container, "PanelTest"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(*[contains(@class,'x-panel-header') or contains(@class, '-tl')]//*[text()='PanelTest']) > 0 and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel("testCls", container, "excludeCls"), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and contains(concat(' ', @class, ' '), ' testCls ') and not(contains(@class, 'excludeCls')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},

                {new Panel(By.container(new Panel())), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(By.title("PanelTest")), "//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(*[contains(@class,'x-panel-header') or contains(@class, '-tl')]//*[text()='PanelTest']) > 0 and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(By.container(container)), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(By.container(container), By.title("PanelTest")), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and count(*[contains(@class,'x-panel-header') or contains(@class, '-tl')]//*[text()='PanelTest']) > 0 and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
                {new Panel(By.classes("testCls"), By.container(container), By.excludeClasses("excludeCls")), "//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(concat(' ', @class, ' '), ' x-panel ') and contains(concat(' ', @class, ' '), ' testCls ') and not(contains(@class, 'excludeCls')) and not(contains(@class, 'x-hide-display')) and not(contains(@class, 'x-masked'))]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Panel panel, String expectedXpath) {
        Assert.assertEquals(panel.getPath(), expectedXpath);
    }

}
