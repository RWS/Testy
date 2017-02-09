package com.sdl.selenium.web.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.button.Button;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ButtonIntegrationTest extends TestBase {

    private Button loginWithCss = new Button().setElCssSelector("#loginButton");
    private Button loginWithXPath = new Button().setElPath("//*[@id='loginButton']");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.LOGIN_URL);
    }

    @Test
    public void findElement() {
        assertThat(loginWithCss.getText(), equalTo("Login"));
        assertThat(loginWithXPath.getText(), equalTo("Login"));
    }
}
