package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.utils.Utils;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TableViewerTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableViewerTest.class);

    private TableViewer table = new TableViewer();

    @BeforeClass
    public void startTests() {
        driver.get("http://rap.eclipsesource.com/demo/release/rapdemo/#tableviewer");
    }

    @Test
    public void verifyIfCheckBoxIsPresent() {
        TableViewerCell cell = table.getTableCell(2, new TableViewerCell(3, "Carbon", SearchType.EQUALS));
        assertEquals("C", cell.getHtmlText());
    }

    @Test
    public void verifyIfTextField() {
        Utils.sleep(5000);
        driver.get("http://rap.eclipsesource.com/demo/release/rapdemo/#input");
        TextFieldBogdan textFieldBogdan = new TextFieldBogdan().setLabel("First Name:");
        textFieldBogdan.setValue("Bogdan");
        assertEquals("Bogdan", textFieldBogdan.getValue());
    }
}
