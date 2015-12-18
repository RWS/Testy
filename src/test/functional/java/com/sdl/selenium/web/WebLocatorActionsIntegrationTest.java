package com.sdl.selenium.web;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.TextField;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class WebLocatorActionsIntegrationTest extends TestBase {

    private WebLocator locator = new WebLocator().setId("loginButton");
    private TextField email = new TextField().setLabel("Email:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.LOGIN_URL);
    }

    @Test
    public void actionsTest() {
        assertThat(locator.isElementPresent(), is(true));
        assertThat(locator.getHtmlText(true), equalTo("Login"));
        startTests();
        assertThat(locator.getHtmlText(true), equalTo("Login"));
    }

    @Test
    public void getTagNameTest() {
        assertThat(locator.getTagName(), equalTo("button"));
    }

    @Test
    public void getAttributeIdTest() {
        assertThat(locator.getAttributeId(), equalTo("loginButton"));
    }

    @Test
    public void getAttributeClassTest() {
        assertThat(locator.getAttributeClass(), equalTo("btn tile-btn btn-warning"));
    }

    @Test
    public void clickAtTest() {
        assertThat(locator.clickAt(), is(true));
    }

    @Test
    public void clickTest() {
        assertThat(locator.click(), is(true));
    }

    @Test
    public void sendKeysTest() {
        String mail = "eu@fast.com";
        assertThat((TextField) email.sendKeys(mail), is(email));
        assertThat(email.getValue(), equalTo(mail));
    }

    @Test (dependsOnMethods = "sendKeysTest")
    public void clearTest() {
        assertThat(email.clear(), is(true));
        assertThat(email.getValue(), equalTo(""));
    }

    @Test
    public void mouseOverTest() {
        assertThat(email.mouseOver(), is(true));
    }
}
