package com.sdl.selenium.extjs3.button;

import com.sdl.demo.extjs3.form.SimpleForm;
import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.extjs3.window.MessageBoxIntegrationTest;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ButtonIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonIntegrationTest.class);

    private Window dateFieldWindow = new Window("DateFieldWindow");
    private Button closeButton = new Button(dateFieldWindow, "Close");
    private WebLocator close = new WebLocator().withId("close");
    private Button dateFieldButton = new Button(null, "DateField");

    private SimpleForm simpleForm = new SimpleForm();
    private Panel panel = new Panel("Find Elements when contains quotes");

    private Button dontAcceptButton = new Button(panel, "Don't Accept");
    private Button dontAcceptButton1 = new Button(panel, "Don'\"t Accept").withSearchTextType(SearchType.CONTAINS);
    private Button dontAcceptButton2 = new Button(panel, "It was \"good\" ok!");
    private Button dontAcceptButton3 = new Button(panel, "Don't do it \"now\" ok!");
    private Button dontAcceptItButton = new Button(panel, "Don't do \"it\" :)");

    private Button dontAcceptButton4 = new Button(panel, "Don't Accept").withSearchTextType(SearchType.CASE_INSENSITIVE);
    private Button dontAcceptButton5 = new Button(panel, "Don't Accept").withSearchTextType(SearchType.CASE_INSENSITIVE, SearchType.CONTAINS);
    private Button dontAcceptButton6 = new Button(panel, "Don't Accept").withSearchTextType(SearchType.CASE_INSENSITIVE, SearchType.STARTS_WITH);

    private Button dontAcceptButton7 = new Button(panel, "Don'\"t Accept").withSearchTextType(SearchType.CASE_INSENSITIVE);
    private Button dontAcceptButton8 = new Button(panel, "Don'\"t Accept").withSearchTextType(SearchType.CASE_INSENSITIVE, SearchType.CONTAINS);
    private Button dontAcceptButton9 = new Button(panel, "Don'\"t Accept").withSearchTextType(SearchType.CASE_INSENSITIVE, SearchType.STARTS_WITH);

    private Button dontAcceptButton10 = new Button(panel, "It was \"good\" ok!").withSearchTextType(SearchType.CASE_INSENSITIVE);
    private Button dontAcceptButton11 = new Button(panel, "It was \"good\" ok!").withSearchTextType(SearchType.CASE_INSENSITIVE, SearchType.CONTAINS);
    private Button dontAcceptButton12 = new Button(panel, "It was \"good\" ok!").withSearchTextType(SearchType.CASE_INSENSITIVE, SearchType.STARTS_WITH);

    private Button dontAcceptButton13 = new Button(panel, "Don't do it \"now\" ok!").withSearchTextType(SearchType.CASE_INSENSITIVE);
    private Button dontAcceptButton14 = new Button(panel, "Don't do it \"now\" ok!").withSearchTextType(SearchType.CASE_INSENSITIVE, SearchType.CONTAINS);
    private Button dontAcceptButton15 = new Button(panel, "Don't do it \"now\" ok!").withSearchTextType(SearchType.CASE_INSENSITIVE, SearchType.STARTS_WITH);

    private Button dontAcceptButton16 = new Button(panel, "Don't Accept").withSearchTextType(SearchType.TRIM);
    private Button dontAcceptButton17 = new Button(panel, "Don'\"t Accept").withSearchTextType(SearchType.TRIM);
    private Button dontAcceptButton18 = new Button(panel, "It was \"good\" ok!").withSearchTextType(SearchType.TRIM);
    private Button dontAcceptButton19 = new Button(panel, "Don't do it \"now\" ok!").withSearchTextType(SearchType.TRIM);

    private Window buttonsWindow = new Window("Buttons Window");
    private SplitButton splitButton = new SplitButton(buttonsWindow, "Export");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_URL);
    }

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
            simpleForm.cancelButton.click();
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
        assertThat(dontAcceptItButton.isElementPresent(), is(true));
        assertThat(dontAcceptButton4.isElementPresent(), is(true));
        assertThat(dontAcceptButton5.isElementPresent(), is(true));
        assertThat(dontAcceptButton6.isElementPresent(), is(true));
        assertThat(dontAcceptButton7.isElementPresent(), is(true));
        assertThat(dontAcceptButton8.isElementPresent(), is(true));
        assertThat(dontAcceptButton9.isElementPresent(), is(true));
        assertThat(dontAcceptButton10.isElementPresent(), is(true));
        assertThat(dontAcceptButton11.isElementPresent(), is(true));
        assertThat(dontAcceptButton12.isElementPresent(), is(true));
        assertThat(dontAcceptButton13.isElementPresent(), is(true));
        assertThat(dontAcceptButton14.isElementPresent(), is(true));
        assertThat(dontAcceptButton15.isElementPresent(), is(true));
        assertThat(dontAcceptButton16.isElementPresent(), is(true));
        assertThat(dontAcceptButton17.isElementPresent(), is(true));
        assertThat(dontAcceptButton18.isElementPresent(), is(true));
        assertThat(dontAcceptButton19.isElementPresent(), is(true));
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
        Button button = new Button(panel, "ButtonThatDoesNotExist").withRenderMillis(millis);

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
