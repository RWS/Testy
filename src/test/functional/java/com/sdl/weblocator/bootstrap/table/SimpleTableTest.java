package com.sdl.weblocator.bootstrap.table;

import com.sdl.bootstrap.button.Button;
import com.sdl.bootstrap.form.CheckBox;
import com.sdl.bootstrap.form.Form;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.SimpleTable;
import com.sdl.selenium.web.table.TableCell;
import com.sdl.selenium.web.table.TableRow;
import com.sdl.selenium.web.utils.Utils;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

        long startMs = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer();
        for (List<String> listEl : table.getCellsText()) {
            stringBuffer.append("\n| ");
            for (String el : listEl) {
                stringBuffer.append(el).append(" | ");
            }
        }
        LOGGER.info("test {}", stringBuffer);

        long endMs = System.currentTimeMillis();
        LOGGER.info(String.format("getAllTexts took %s ms", endMs - startMs));

        assertEquals(table.getCellsText(), listOfList);
    }

    @Test//(invocationCount = 4)
    public void ThreadTest() throws InterruptedException {
        ArrayList<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("John", "Carter"));
        lists.add(Arrays.asList("Peter", "Parker"));
        lists.add(Arrays.asList("John", "Rambo"));
        lists.add(Arrays.asList("John1", "Rambo1"));
        lists.add(Arrays.asList("John2", "Rambo2"));
        lists.add(Arrays.asList("John3", "Rambo3"));

        table.ready(true);
        long startMs = System.currentTimeMillis();
        RunExeThread exeThread = null;
        for (List<String> list : lists) {
            TableCell cell = table.getTableCell(1, new TableCell(2, list.get(0), SearchType.EQUALS), new TableCell(3, list.get(1), SearchType.EQUALS));
            CheckBox check = new CheckBox(cell);
            exeThread = new RunExeThread(check);
            exeThread.start();
        }

//        for (List<String> list : lists) {
//            TableCell cell = table.getTableCell(1, new TableCell(2, list.get(0), SearchType.EQUALS), new TableCell(3, list.get(1), SearchType.EQUALS));
//            CheckBox check = new CheckBox(cell);
//            exeThread = new RunExeThread(check);
//            assertTrue(exeThread.isSelected());
//        }
//        exeThread.interrupt();
//        exeThread.join();
        long endMs = System.currentTimeMillis();
        LOGGER.info(String.format("Click thread took %s ms", endMs - startMs));
        Utils.sleep(1500);
        long startMs1 = System.currentTimeMillis();
        for (List<String> list : lists) {
            TableCell cell = table.getTableCell(1, new TableCell(2, list.get(0), SearchType.EQUALS), new TableCell(3, list.get(1), SearchType.EQUALS));
            CheckBox check = new CheckBox(cell);
            check.click();
            check.isSelected();
        }
        long endMs1 = System.currentTimeMillis();
        LOGGER.info(String.format("Click thread1 took %s ms", endMs1 - startMs1));
    }

    public class RunExeThread extends Thread {
        private CheckBox element;

        public RunExeThread(CheckBox webElement) {
            this.element = webElement;
        }

        public void run() {
            this.element.click();
        }

        public Boolean isSelected() {
            return this.element.isSelected();
        }
    }
}
