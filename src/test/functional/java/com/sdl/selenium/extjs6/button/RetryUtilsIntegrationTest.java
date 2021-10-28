package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RetryUtilsIntegrationTest extends TestBase {

    private final Panel loginPanel = new Panel(null, "Login");
    private final Button register = new Button(loginPanel, "Registerx");
    private final Button login = new Button(loginPanel, "Loginx");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-login");
        driver.switchTo().frame("examples-iframe");
        register.ready(Duration.ofSeconds(10));
        Utils.sleep(1000);
    }

    @Test
    void retryUntilOneIs() {
        Boolean notPresent = RetryUtils.retryUntilOneIs(2, new Button(loginPanel, "Registerx")::isPresent, new Button(loginPanel, "Loginx")::isPresent);
        assertThat(notPresent, is(false));
        Boolean present = RetryUtils.retryUntilOneIs(2, new Button(loginPanel, "Registerx")::isPresent, new Button(loginPanel, "Login")::isPresent);
        assertThat(present, is(true));
    }
}
