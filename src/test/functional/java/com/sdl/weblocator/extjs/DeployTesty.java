package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.conditions.MessageBoxSuccessCondition;
import com.extjs.selenium.form.TextField;
import com.extjs.selenium.grid.GridPanel;
import com.extjs.selenium.tab.TabPanel;
import com.extjs.selenium.window.MessageBox;
import com.extjs.selenium.window.Window;
import com.sdl.bootstrap.form.Form;
import com.sdl.selenium.conditions.Condition;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.ElementRemovedSuccessCondition;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.SimpleTextField;
import com.sdl.selenium.web.table.SimpleTable;
import com.sdl.selenium.web.table.TableCell;
import com.sdl.selenium.web.utils.Utils;
import com.sdl.weblocator.TestBase;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class DeployTesty extends TestBase {
    private static final Logger logger = Logger.getLogger(DeployTesty.class);

    private WebLocator loginEl = new WebLocator().setElPath("//span/a[.//*[text()='log in']]");
    private WebLocator logOutEl = new WebLocator().setElPath("//span/a[.//*[text()='log out']]");
    private Form loginForm = new Form().setName("login");
    private SimpleTextField login = new SimpleTextField(loginForm).setName("j_username");
    private SimpleTextField pass = new SimpleTextField(loginForm).setName("j_password");
    private WebLocator logInButton = new WebLocator(loginForm).setId("yui-gen1-button");
    private SimpleTable table = new SimpleTable().setId("main-table");
    private WebLocator buildNow = new WebLocator(table, "//a[@class='task-link' and text()='Build Now']");
    private SimpleTable buildHistory = new SimpleTable().setId("buildHistory");
    private WebLocator buildNowEl = new WebLocator(buildHistory).setClasses("build-row", "no-wrap", "transitive").setPosition(1);

    private WebLocator logInNexus = new WebLocator().setId("head-link-r");
    private Window nexusLogInWindow = new Window("Nexus Log In");
    private TextField userName = new TextField(nexusLogInWindow, "Username:");
    private TextField password = new TextField(nexusLogInWindow, "Password:");
    private Button logIn = new Button(nexusLogInWindow, "Log In");
    private WebLocator viewRepositories = new WebLocator().setId("view-repositories");
    private GridPanel repositoryGridPanel = new GridPanel(viewRepositories);
    private TabPanel browseStorage = new TabPanel(viewRepositories, "Browse Storage");
    private SimpleTable table1 = new SimpleTable(browseStorage).setCls("x-toolbar-ct");
    private WebLocator testyDir = new WebLocator().setElPath("//a[@class='x-tree-node-anchor' and count(.//span[text()='Testy']) > 0]");
    private TableCell tableCell = table1.getTableCell(4, new TableCell(3, "Path Lookup:", SearchType.EQUALS));
    private TextField searchField = new TextField(tableCell);

    @BeforeClass
    public void startTests() {
        driver.get("http://cluj-jenkins01:8080/job/testy/");
    }

    @Test
    public void loginJenkins() {
        loginEl.click();
        loginForm.ready(10);
        login.setValue("user-ul de domeniu");
        pass.setValue("parola de domeniu");
        logInButton.click();
        logOutEl.ready(10);
    }

    @Test(dependsOnMethods = "loginJenkins")
    public void deployOnJenkins() {
        buildNow.click();
        String testyDir = System.getProperty("user.home") + "\\.m2\\repository\\com\\sdl\\lt\\Testy";
        cleanDir(testyDir);
        waitRenderEl(buildNowEl, 5000);
        waitRemovedEl(buildNowEl, 120000);
    }

    @Test(dependsOnMethods = "deployOnJenkins")
    public void loginAsAdminSDLNexus() {
        driver.get("http://cfg-mgmt-server:8081/nexus/index.html#view-repositories;oss-sonatype-snapshots");
        logInNexus.click();
        userName.setValue("admin");
        password.setValue("admin123");
        logIn.click();
    }

    @Test(dependsOnMethods = "loginAsAdminSDLNexus")
    public void removeFormSDLNexus() {
        repositoryGridPanel.rowSelect("oss-sonatype-snapshots");
        browseStorage.setActive();
        searchField.setValue("com/sdl/lt/Testy");
        testyDir.ready(10);
        Actions action = new Actions(driver);
        action.contextClick(testyDir.currentElement).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
        confirmYesMSG("Delete the selected \"/com/sdl/lt/Testy/\" folder?");
        Utils.sleep(1000);
    }

    private void cleanDir(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            logger.debug("not found" + path);
        }
    }

    private boolean waitRemovedEl(WebLocator el, long time) {
        return new ConditionManager(time).add(new ElementRemovedSuccessCondition(el)).execute().isSuccess();
    }

    private boolean waitRenderEl(WebLocator el, long time) {
        return new ConditionManager(time).add(new RenderSuccessCondition(el)).execute().isSuccess();
    }

    private boolean confirmYesMSG(String msg) {
        boolean success = false;
        Condition condition = new ConditionManager(16000).add(new MessageBoxSuccessCondition(msg)).execute();
        if (condition.isSuccess()) {
            MessageBox.pressYes();
            success = true;
        }
        return success;
    }
}
