package com.sdl.weblocator.bootstrap.table;

import com.sdl.bootstrap.button.Button;
import com.sdl.bootstrap.form.CheckBox;
import com.sdl.bootstrap.form.Form;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.table.SimpleTable;
import com.sdl.selenium.web.table.TableCell;
import com.sdl.selenium.web.table.TableRow;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class SimpleTableTest extends TestBase {
    private static final Logger LOGGER = Logger.getLogger(SimpleTableTest.class);

    private Form form = new Form(null, "Form Table");
    private SimpleTable table = new SimpleTable(form);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void verifyIfCheckBoxIsPresent() {
        TableCell cell = table.getTableCell(1, new TableCell(2, "John", SearchType.EQUALS), new TableCell(3, "Carter", SearchType.EQUALS));
        assertTrue(new CheckBox(cell).isElementPresent());
    }

    @Test
    public void verifyIfButtonsIsPresent() {
        TableRow row = table.getRow(1, new TableCell(2, "John", SearchType.EQUALS), new TableCell(3, "Carter", SearchType.EQUALS));
        Button first = new Button(row).setText("First", SearchType.CONTAINS);
        Button second = new Button(row, "Second");
        assertTrue(first.isElementPresent());
        assertTrue(second.isElementPresent());
    }

    @Test
    public void verifyHeaderName() {
        TableCell row = new TableCell(1, "Row", SearchType.EQUALS).setTag("th");
        TableCell firstName = new TableCell(2, "First Name", SearchType.EQUALS).setTag("th");
        TableCell lastName = new TableCell(3, "Last Name", SearchType.EQUALS).setTag("th");
        TableCell email = new TableCell(4, "Email", SearchType.EQUALS).setTag("th");
        TableCell buttons = new TableCell(5, "Buttons", SearchType.EQUALS).setTag("th");
        assertTrue(table.getRow(row, firstName, lastName, email, buttons).ready());
    }
}
