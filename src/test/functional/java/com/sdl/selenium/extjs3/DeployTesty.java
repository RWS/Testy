package com.sdl.selenium.extjs3;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.ElementRemovedSuccessCondition;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.InputButton;
import com.sdl.selenium.web.form.TextField;
import com.sdl.selenium.web.link.WebLink;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.table.Table;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class DeployTesty extends TestBase {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DeployTesty.class);
    // Rulati acest test dupa ce ati oprit orice test!!!!

    private static final String DOMAIN_USER = "user";
    private static final String DOMAIN_PASS = "pass";

    private static final String JENKINS_JOB_URL = "https://cluj-jenkins03:8443/job/testy/";

    private final WebLocator loginContainer = new WebLocator().setClasses("login");
    private final WebLink logOutEl = new WebLink(loginContainer).setAttribute("href", "/logout");
    private final Form loginForm = new Form().setName("login");
    private final TextField username = new TextField(loginForm).setName("j_username");
    private final TextField pass = new TextField(loginForm).setName("j_password");
    private final InputButton logInButton = new InputButton(loginForm).setName("Submit");
    private final WebLocator table = new WebLocator().setId("tasks");
    private final WebLink buildNow = new WebLink(table, "Build Now").setClasses("task-link");
    private final Table buildHistory = new Table().setClasses("pane", "stripped");
    private final Row buildNowEl = new Row(buildHistory).setClasses("build-row", "transitive");

    @BeforeClass
    public void startTests() {
        driver.get(JENKINS_JOB_URL);
    }

    @Test
    public void loginJenkins() {
        loginForm.ready(Duration.ofSeconds(10));
        WebLocatorUtils.scrollToWebLocator(loginForm);
        username.setValue(DOMAIN_USER);
        pass.setValue(DOMAIN_PASS);
        logInButton.click();
        logOutEl.ready(Duration.ofSeconds(10));
    }

    @Test(dependsOnMethods = "loginJenkins")
    public void deployOnJenkins() {
        buildNow.click();
        String testyDir = System.getProperty("user.home") + "\\.m2\\repository\\com\\sdl\\lt\\Testy";
        cleanDir(testyDir);
        waitRenderEl(buildNowEl, Duration.ofSeconds(5));
        waitRemovedEl(buildNowEl, Duration.ofSeconds(950));
    }

    private void cleanDir(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IllegalArgumentException | IOException e) {
            log.debug("not found " + path);
        }
    }

    private boolean waitRemovedEl(WebLocator el, Duration duration) {
        return new ConditionManager(duration).add(new ElementRemovedSuccessCondition(el)).execute().isSuccess();
    }

    private boolean waitRenderEl(WebLocator el, Duration duration) {
        return new ConditionManager(duration).add(new RenderSuccessCondition(el)).execute().isSuccess();
    }
}