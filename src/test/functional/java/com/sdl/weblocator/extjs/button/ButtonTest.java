package com.sdl.weblocator.extjs.button;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.button.SplitButton;
import com.sdl.selenium.extjs3.panel.Panel;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.selenium.web.SearchType;
import com.sdl.weblocator.TestBase;
import com.sdl.weblocator.extjs.window.MessageBoxTest;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ButtonTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonTest.class);
    
    Window dateFieldWindow = new Window("DateFieldWindow");
    Button closeButton = new Button(dateFieldWindow, "Close");
    Button dateFieldButton = new Button(null, "DateField");

    Button cancelButton = new Button(new Panel("Simple Form"), "Cancel");
    Panel panel = new Panel("Find Elements when contains quotes");

    Button dontAcceptButton = new Button(panel, "Don't Accept");
    Button dontAcceptButton1 = new Button(panel, "Don'\"t Accept").setSearchTextType(SearchType.CONTAINS);
    Button dontAcceptButton2 = new Button(panel, "It was \"good\" ok!");
    Button dontAcceptButton3 = new Button(panel, "Don't do it \"now\" ok!");
    
    Window buttonsWindow = new Window("Buttons Window");
    SplitButton splitButton = new SplitButton(buttonsWindow, "Export");

    @Test
    public void isDisplayed() {
        dateFieldButton.click();
        assertTrue(driver.findElement(By.xpath(closeButton.getPath())).isDisplayed());
        assertTrue(driver.findElement(By.id("close")).isDisplayed());
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

    @Test (dependsOnMethods = "performanceTestClick")
    public void findButtonWithQuotes() {
        assertTrue(dontAcceptButton.isElementPresent());
        assertTrue(dontAcceptButton1.isElementPresent());
        assertTrue(dontAcceptButton2.isElementPresent());
        assertTrue(dontAcceptButton3.isElementPresent());
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
        assertFalse(clicked, "Nu trebuia sa faca click");
        assertTrue(endMs - startMs < millis + 500, "Took too long");
        assertTrue(endMs - startMs >= millis, "Did not waited expected time");
    }
    
    @Test
    void testSplitButton() {
        showComponent("Buttons");
        
        splitButton.assertClick();
        MessageBoxTest.assertThatMessageBoxExists("You selected Export");

        boolean clicked = splitButton.clickOnMenu("PDF");
        assertTrue(clicked, "Could not click on button");
        MessageBoxTest.assertThatMessageBoxExists("You selected PDF");

        clicked = splitButton.clickOnMenu("EXCEL");
        assertTrue(clicked, "Could not click on button");
        MessageBoxTest.assertThatMessageBoxExists("You selected EXCEL");
    }
}
