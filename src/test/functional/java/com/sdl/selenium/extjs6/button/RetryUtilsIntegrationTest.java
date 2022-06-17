package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.form.TextField;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.utils.Result;
import com.sdl.selenium.web.utils.RetryUtils;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class RetryUtilsIntegrationTest extends TestBase {

    private final Panel loginPanel = new Panel(null, "Login");
    private final Button register = new Button(loginPanel, "Register");
    private final Button login = new Button(loginPanel, "Login");
    private final TextField userId = new TextField(loginPanel, "User ID");
    private final TextField password = new TextField(loginPanel, "Password");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#form-login");
        driver.switchTo().frame("examples-iframe");
        register.ready(Duration.ofSeconds(10));
        Utils.sleep(1000);
    }

    @Test
    void retryUntilOneIs() {
        Result<Boolean> notPresent = RetryUtils.retryUntilOneIs(2, new Button(loginPanel, "Registerx")::isPresent, new Button(loginPanel, "Loginx")::isPresent);
        assertThat(notPresent.result(), is(false));
        assertThat(notPresent.position(), is(0));
    }

    @Test
    void retryUntilOneIs1() {
        Result<Boolean> present = RetryUtils.retryUntilOneIs(2, new Button(loginPanel, "Registerx")::isPresent, new Button(loginPanel, "Login")::isPresent);
        assertThat(present.result(), is(true));
        assertThat(present.position(), is(2));
    }

    @Test
    void retryUntilOneIs2() {
        Result<Boolean> present2 = RetryUtils.retryUntilOneIs(2, new Button(loginPanel, "Registerx")::isPresent, new Button(loginPanel, "Loginy")::isPresent, new Button(loginPanel, "Login")::isPresent);
        assertThat(present2.result(), is(true));
        assertThat(present2.position(), is(3));
    }

    @Test
    void retryUntilOneIs3() {
        userId.setValue("test");
        Result<String> notPresent = RetryUtils.retryUntilOneIs(2, userId::getValue, password::getValue);
        assertThat(notPresent.result(), equalTo("test"));
        assertThat(notPresent.position(), is(1));
    }

    @Test
    void retryUntilOneIs4() {
        userId.setValue("");
        password.setValue("test");
        Result<String> present = RetryUtils.retryUntilOneIs(2, userId::getValue, password::getValue);
        assertThat(present.result(), equalTo("test"));
        assertThat(present.position(), is(2));
    }

    @Test
    void retryUntilOneIs5() {
        userId.setValue("");
        password.setValue("");
        Result<String> present = RetryUtils.retryUntilOneIs(2, userId::getValue, password::getValue);
        assertThat(present.result(), equalTo(""));
        assertThat(present.position(), is(0));
    }

    @Test
    void retryUntilOneIs6() {
        userId.setValue("");
        String present = RetryUtils.retry(Duration.ofSeconds(100), userId::getValue);
        assertThat(present, equalTo(""));
    }
}
