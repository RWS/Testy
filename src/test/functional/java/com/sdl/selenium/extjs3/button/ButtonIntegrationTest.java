package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.window.MessageBoxIntegrationTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ButtonIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonIntegrationTest.class);

    private Window dateFieldWindow = new Window("DateFieldWindow");
    private Button closeButton = new Button(dateFieldWindow, "Close");
    private WebLocator close = new WebLocator().setId("close");
    private Button dateFieldButton = new Button(null, "DateField");

    private Button cancelButton = new Button(new Panel("Simple Form"), "Cancel");
    private Panel panel = new Panel("Find Elements when contains quotes");

    private Button dontAcceptButton = new Button(panel, "Don't Accept");
    private Button dontAcceptButton1 = new Button(panel, "Don'\"t Accept").setSearchTextType(SearchType.CONTAINS);
    private Button dontAcceptButton2 = new Button(panel, "It was \"good\" ok!");
    private Button dontAcceptButton3 = new Button(panel, "Don't do it \"now\" ok!");

    private Window buttonsWindow = new Window("Buttons Window");
    private SplitButton splitButton = new SplitButton(buttonsWindow, "Export");

    @Test
    public void isDisplayed() {
        dateFieldButton.click();
        assertThat(closeButton.isDisplayed(), is(true));
        assertThat(close.isDisplayed(), is(true));
        dateFieldWindow.close();
    }

    @Test
    public void performanceTestClick() {
        long startMs = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            cancelButton.click();
        }
        long endMs = System.currentTimeMillis();
        LOGGER.info(String.format("performanceTestClick took %s ms", endMs - startMs));
    }

    @Test(dependsOnMethods = "performanceTestClick")
    public void findButtonWithQuotes() {
        assertThat(dontAcceptButton.isElementPresent(), is(true));
        assertThat(dontAcceptButton1.isElementPresent(), is(true));
        assertThat(dontAcceptButton2.isElementPresent(), is(true));
        assertThat(dontAcceptButton3.isElementPresent(), is(true));
    }

    @DataProvider
    public Object[][] renderMillis() {
        return new Object[][]{
                {1000},
                {3000},
                {5000}
        };
    }

    @Test(dataProvider = "renderMillis")
    void tryToClickOnButtonThatDoesNotExist(long millis) {
        Button button = new Button(panel, "ButtonThatDoesNotExist").setRenderMillis(millis);

        long startMs = System.currentTimeMillis();
        boolean clicked = button.click();
        long endMs = System.currentTimeMillis();

        LOGGER.info(String.format("took %s ms", endMs - startMs));
        assertThat("Nu trebuia sa faca click", clicked, is(false));
        assertThat("Took too long", endMs - startMs < millis + 500);
        assertThat("Did not waited expected time", endMs - startMs >= millis);
    }

    @Test
    void testSplitButton() {
        showComponent("Buttons");

        splitButton.assertClick();
        MessageBoxIntegrationTest.assertThatMessageBoxExists("You selected Export");

        boolean clicked = splitButton.clickOnMenu("PDF");
        assertThat("Could not click on button", clicked);
        MessageBoxIntegrationTest.assertThatMessageBoxExists("You selected PDF");

        clicked = splitButton.clickOnMenu("EXCEL");
        assertThat("Could not click on button", clicked);
        MessageBoxIntegrationTest.assertThatMessageBoxExists("You selected EXCEL");
    }
}
