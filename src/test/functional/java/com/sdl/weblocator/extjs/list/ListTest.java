package com.sdl.weblocator.extjs.list;

import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.extjs3.list.List;
import com.sdl.selenium.extjs3.window.Window;
import com.sdl.weblocator.Ignores;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.sdl.weblocator.Ignores.Driver.CHROME;
import static org.testng.Assert.assertTrue;

public class ListTest extends TestBase {

    Window multiSelectWindow = new Window("MultiSelectWindow");
    List multiSelectList = new List(multiSelectWindow);

    @BeforeClass
    public void startTests() {
        Button multiSelectButton = new Button(null, "MultiSelect");
        multiSelectButton.click();
    }

    @AfterClass
    public void endTests() {
        multiSelectWindow.close();
    }

    @Ignores (value = {CHROME}, reason = "Nu se seleacteaza in Chrome")
    @Test
    public void select() {
        assertTrue(multiSelectList.selectRows("English", "French", "Spanish"));
        assertTrue(multiSelectList.isSelectedRows("English", "French", "Spanish"));
    }

    @Test
    public void selectWithJs() {
        assertTrue(multiSelectList.selectRowsWithJs("German", "Japanese", "Russian"));
        assertTrue(multiSelectList.isSelectedRows("German", "Japanese", "Russian"));
    }
}
