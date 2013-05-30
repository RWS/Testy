package com.sdl.weblocator.tests;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.list.List;
import com.extjs.selenium.window.Window;
import com.sdl.weblocator.TestBase;
import org.junit.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

    //@Test
    public void select() {
        assertTrue(multiSelectList.selectRows(new String[]{"English", "French", "Spanish"}));
        assertTrue(multiSelectList.isSelectedRows(new String[]{"English", "French", "Spanish"}));
    }

    @Test
    public void selectWithJs() {
        assertTrue(multiSelectList.selectRowsWithJs(new String[]{"German", "Japanese", "Russian"}));
        assertTrue(multiSelectList.isSelectedRows(new String[]{"German", "Japanese", "Russian"}));
    }

}
