package com.sdl.selenium.extjs3;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.WebLocatorUtils;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.ElementRemovedSuccessCondition;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.conditions.MessageBoxSuccessCondition;
import com.sdl.selenium.extjs3.grid.GridPanel;
import com.sdl.selenium.extjs3.tab.TabPanel;
import com.sdl.selenium.extjs3.window.MessageBox;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.TextField;
import com.sdl.selenium.web.link.WebLink;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

@Slf4j
public class DeployTesty extends TestBase {

    // Rulati acest test dupa ce ati oprit orice test!!!!

    private static final String DOMAIN_USER = "vculea";
    private static final String DOMAIN_PASS = "VCinit**";

    private static final String JENKINS_JOB_URL = "https://cluj-jenkins02.global.sdl.corp:8443/job/testy/";

    private static final String NEXUS_REPOSITORY_URL = "http://cljeng-nexus02:8081/nexus/#view-repositories;oss-sonatype-snapshots";

    private WebLocator loginContainer = new WebLocator().setClasses("login");
    private WebLink loginEl = new WebLink(loginContainer, "log in", SearchType.DEEP_CHILD_NODE_OR_SELF);
    private WebLink logOutEl = new WebLink(loginContainer).setAttribute("href", "/logout");
    private Form loginForm = new Form().setName("login");
    private TextField login = new TextField(loginForm).setName("j_username");
    private TextField pass = new TextField(loginForm).setName("j_password");
    private com.sdl.selenium.web.button.Button logInButton = new com.sdl.selenium.web.button.Button(loginForm).setId("yui-gen1-button");
    private WebLocator table = new WebLocator().setId("tasks");
    private WebLink buildNow = new WebLink(table, "Build Now").setClasses("task-link");
    private Table buildHistory = new Table().setClasses("pane", "stripped");
    private Row buildNowEl = new Row(buildHistory).setClasses("build-row", "transitive");

    private WebLocator logInNexus = new WebLocator().setId("head-link-r");
    private Window nexusLogInWindow = new Window("Nexus Repository Manager Log In");
    private com.sdl.selenium.extjs3.form.TextField userName = new com.sdl.selenium.extjs3.form.TextField(nexusLogInWindow, "Username:");
    private com.sdl.selenium.extjs3.form.TextField password = new com.sdl.selenium.extjs3.form.TextField(nexusLogInWindow, "Password:");
    private Button logIn = new Button(nexusLogInWindow, "Log In");
    private WebLocator viewRepositories = new WebLocator().setId("view-repositories");
    private GridPanel repositoryGridPanel = new GridPanel(viewRepositories);
    private TabPanel browseStorage = new TabPanel(viewRepositories, "Browse Storage");
    private Table table1 = new Table(browseStorage).setCls("x-toolbar-ct");
    private WebLocator testyDir = new WebLocator().setElPath("//a[@class='x-tree-node-anchor' and count(.//span[text()='Testy']) > 0]");
    private Cell cell = table1.getCell(4, new Cell(3, "Path Lookup:", SearchType.EQUALS));
    private com.sdl.selenium.extjs3.form.TextField searchField = new com.sdl.selenium.extjs3.form.TextField(cell);
    private WebLocator menuList = new WebLocator().setClasses("x-menu").setExcludeClasses("x-hide-offsets");
    private WebLocator deleteEl = new WebLocator(menuList).setClasses("x-menu-item-text").setText("Delete");

    @BeforeClass
    public void startTests() {
        driver.get(JENKINS_JOB_URL);
    }

    @Test
    public void loginJenkins() {
        loginEl.click();
        loginForm.ready(10);
        WebLocatorUtils.scrollToWebLocator(loginForm);
        login.setValue(DOMAIN_USER);
        pass.setValue(DOMAIN_PASS);
        logInButton.click();
        logOutEl.ready(10);
    }

    @Test(dependsOnMethods = "loginJenkins")
    public void deployOnJenkins() {
        buildNow.click();
        String testyDir = System.getProperty("user.home") + "\\.m2\\repository\\com\\sdl\\lt\\Testy";
        cleanDir(testyDir);
        waitRenderEl(buildNowEl, Duration.ofSeconds(5));
        waitRemovedEl(buildNowEl, Duration.ofSeconds(750));
    }

    @Test(dependsOnMethods = "deployOnJenkins")
    public void loginAsAdminSDLNexus() {
        driver.get(NEXUS_REPOSITORY_URL);
        logInNexus.ready();
        Utils.sleep(1000);
        logInNexus.click();
        Utils.sleep(1000);
        boolean ready = userName.ready(10);
        if(!ready){
            logInNexus.click();
            Utils.sleep(1000);
        }
        userName.setValue(DOMAIN_USER);
        password.setValue(DOMAIN_PASS);
        logIn.click();
    }

    @Test(dependsOnMethods = "loginAsAdminSDLNexus")
    public void removeFormSDLNexus() {
        repositoryGridPanel.setTimeout(10);
        repositoryGridPanel.rowSelect("OSS Sonatype Snapshots");
        browseStorage.setActive();
        searchField.setValue("com/sdl/lt/Testy");
        testyDir.ready(10);
        Utils.sleep(1000);
        Actions action = new Actions(driver);
        action.contextClick(testyDir.currentElement).perform();
        deleteEl.click();
        confirmYesMSG("Delete the selected \"/com/sdl/lt/Testy/\" folder?");
        Utils.sleep(1000);
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

    private boolean confirmYesMSG(String msg) {
        boolean success = false;
        Condition condition = new ConditionManager(Duration.ofSeconds(16)).add(new MessageBoxSuccessCondition(msg)).execute();
        if (condition.isSuccess()) {
            MessageBox.pressYes();
            success = true;
        }
        return success;
    }
}