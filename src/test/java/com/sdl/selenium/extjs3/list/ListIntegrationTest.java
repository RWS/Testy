package com.sdl.selenium.extjs3.list;

import com.sdl.selenium.Ignores;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.window.Window;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.sdl.selenium.Ignores.Driver.CHROME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ListIntegrationTest extends TestBase {

    private Window multiSelectWindow = new Window("MultiSelectWindow");
    private List multiSelectList = new List(multiSelectWindow);

    @BeforeClass
    public void startTests() {
        Button multiSelectButton = new Button(null, "MultiSelect");
        multiSelectButton.click();
    }

    @AfterClass
    public void endTests() {
        multiSelectWindow.close();
    }

    @Ignores(value = {CHROME}, reason = "Nu se seleacteaza in Chrome")
    @Test
    public void select() {
        assertThat(multiSelectList.selectRows("English", "French", "Spanish"), is(true));
        assertThat(multiSelectList.isSelectedRows("English", "French", "Spanish"), is(true));
    }

    @Test
    public void selectWithJs() {
        assertThat(multiSelectList.selectRowsWithJs("German", "Japanese", "Russian"), is(true));
        assertThat(multiSelectList.isSelectedRows("German", "Japanese", "Russian"), is(true));
    }
}
