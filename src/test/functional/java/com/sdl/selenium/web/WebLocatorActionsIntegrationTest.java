package com.sdl.selenium.web;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.TextField;
import com.sdl.selenium.utils.config.WebDriverConfig;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class WebLocatorActionsIntegrationTest extends TestBase {

    private WebLocator locator = new WebLocator().setId("loginButton").setVisibility(true);
    private TextField email = new TextField().setLabel("Email:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.LOGIN_URL);
    }

    @Test
    public void actionsTest() {
        assertThat(locator.isPresent(), is(true));
        assertThat(locator.getText(true), equalTo("Login"));
        startTests();
        String text = locator.getText(true);
        assertThat(text, equalTo("Login"));
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
        assertThat(locator.getAttributeClass(), equalTo("btn tile-btn btn-warning login-btn"));
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
        assertThat(email.sendKeys(mail), is(email));
        assertThat(email.getValue(), equalTo(mail));
    }

    @Test (dependsOnMethods = "sendKeysTest")
    public void clearTest() {
        assertThat(email.clear(), is(true));
        assertThat(email.getValue(), equalTo(""));
    }

    @Test (dependsOnMethods = "clearTest")
    public void pasteInValueTest() {
        String value = "test";
        email.pasteInValue(value);
        assertThat(email.getValue(), is(value));
    }

    @Test
    public void mouseOverTest() {
        assertThat(locator.mouseOver(), is(true));
    }

    @Test
    public void blurTest() {
        assertThat(locator.blur(), is(true));
    }

    @Test
    public void focusTest() {
        assertThat(locator.focus(), is(locator));
    }

    @Test
    public void doubleClickAtTest() {
        assertThat(locator.doubleClickAt(), is(true));
    }

    @Test
    public void sizeTest() {
        assertThat(locator.size(), is(1));
    }

    @Test
    public void getLocationTest() {
        assertThat(locator.getLocation(), WebDriverConfig.isChrome() ? is(new Point(136, 161)) : is(new Point(136, 162)));
    }

    @Test
    public void getSizeTest() {
        assertThat(locator.getSize(), is(new Dimension(61, 30)));
    }

    @Test
    public void readyTest() {
        assertThat(locator.ready(), is(true));
    }

    @Test
    public void isEnabledTest() {
        assertThat(locator.isEnabled(), is(true));
    }

    @Test
    public void isDisplayedTest() {
        assertThat(locator.isDisplayed(), is(true));
    }
}
