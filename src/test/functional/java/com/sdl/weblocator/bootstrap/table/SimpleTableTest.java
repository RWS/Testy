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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SimpleTableTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTableTest.class);

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

    @Test
    public void getAllTexts() {
        List<List<String>> listOfList = new ArrayList<List<String>>();

        List<String> list = new ArrayList<String>(); // one inner list
        list.add("");
        list.add("John");
        list.add("Carter");
        list.add("johncarter@mail.com");
        list.add("First (1) Second");
        listOfList.add(list);

        list = new ArrayList<String>(); // and another one
        list.add("");
        list.add("Peter");
        list.add("Parker");
        list.add("peterparker@mail.com");
        list.add("First (2) Second");
        listOfList.add(list);

        list = new ArrayList<String>(); // and another one
        list.add("");
        list.add("John");
        list.add("Rambo");
        list.add("johnrambo@mail.com");
        list.add("First (3) Second");
        listOfList.add(list);

        for (List<String> listEl : table.getCellsText()) {
            System.out.println();
            System.out.print("| ");
            for (String el : listEl) {
                System.out.print(el + " | ");
            }
        }
        System.out.println();

        assertEquals(table.getCellsText(), listOfList);
    }
}
