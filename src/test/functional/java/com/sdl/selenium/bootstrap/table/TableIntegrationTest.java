package com.sdl.selenium.bootstrap.table;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.button.Button;
import com.sdl.selenium.bootstrap.form.CheckBox;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.table.Cell;
import com.sdl.selenium.web.table.Row;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TableIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableIntegrationTest.class);

    private Form form = new Form(null, "Form Table");
    private Table table = new Table(form);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void verifyIfCheckBoxIsPresent() {
        Cell cell = table.getCell(1, new Cell(2, "John", SearchType.EQUALS), new Cell(3, "Carter", SearchType.EQUALS));
        assertTrue(new CheckBox(cell).isElementPresent());
    }

    @Test
    public void verifyIfButtonsIsPresent() {
        Row row = table.getRow(1, new Cell(2, "John", SearchType.EQUALS), new Cell(3, "Carter", SearchType.EQUALS));
        Button first = new Button(row).withText("First", SearchType.CONTAINS);
        Button second = new Button(row, "Second");
        assertTrue(first.isElementPresent());
        assertTrue(second.isElementPresent());
    }

    @Test
    public void verifyHeaderName() {
        Cell row = new Cell(1, "Row", SearchType.EQUALS).withTag("th");
        Cell firstName = new Cell(2, "First Name", SearchType.EQUALS).withTag("th");
        Cell lastName = new Cell(3, "Last Name", SearchType.EQUALS).withTag("th");
        Cell email = new Cell(4, "Email", SearchType.EQUALS).withTag("th");
        Cell buttons = new Cell(5, "Buttons", SearchType.EQUALS).withTag("th");
        assertTrue(table.getRow(row, firstName, lastName, email, buttons).ready());
    }

    @Test
    public void getAllTexts() {
        List<List<String>> listOfList = new ArrayList<>();

        listOfList.add(Arrays.asList("", "John", "Carter", "johncarter@mail.com", "First (1) Second"));
        listOfList.add(Arrays.asList("", "Peter", "Parker", "peterparker@mail.com", "First (2) Second"));
        listOfList.add(Arrays.asList("", "John", "Rambo", "johnrambo@mail.com", "First (3) Second"));
        listOfList.add(Arrays.asList("", "John1", "Rambo1", "johnrambo@mail.com", "First (3) Second"));
        listOfList.add(Arrays.asList("", "John2", "Rambo2", "johnrambo@mail.com", "First (3) Second"));
        listOfList.add(Arrays.asList("", "John3", "Rambo3", "johnrambo@mail.com", "First (3) Second"));

        List<List<String>> cellsText = table.getCellsText();
        StringBuffer stringBuffer = new StringBuffer();
        for (List<String> listEl : cellsText) {
            stringBuffer.append("\n| ");
            for (String el : listEl) {
                stringBuffer.append(el).append(" | ");
            }
        }
        LOGGER.info("test {}", stringBuffer);
        assertEquals(cellsText, listOfList);
    }

    @Test
    public void getAllTextsFromRow() {
        List<String> listOfList = Arrays.asList("", "John", "Carter", "johncarter@mail.com", "First (1) Second");

        Row row = table.getRow(new Cell(2, "John", SearchType.EQUALS), new Cell(3, "Carter", SearchType.EQUALS));
        List<String> cellsText = row.getCellsText();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n| ");
        for (String el : cellsText) {
            stringBuffer.append(el).append(" | ");
        }
        LOGGER.info("test {}", stringBuffer);
        assertEquals(cellsText, listOfList);
    }

    @Test
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

        ExecutorService pool = Executors.newFixedThreadPool(25);
        List<Future<Boolean>> executionList = new ArrayList<>();
        for (List<String> list : lists) {
            RunExeThread exeThread = new RunExeThread(list);
            Future<Boolean> future = pool.submit(exeThread);
            executionList.add(future);
        }

        boolean globalClick = true;
        for (Future<Boolean> future : executionList) {
            try {
                globalClick = globalClick && future.get();
            } catch (ExecutionException e) {
                globalClick=false;
                LOGGER.warn("Warning waiting for table click on checkboxes ", e);
            }
        }

        pool.shutdown();
        System.gc();

        assertTrue(globalClick);

//        for (List<String> list : lists) {
//            Cell cell = table.getCell(1, new Cell(2, list.get(0), SearchType.EQUALS), new Cell(3, list.get(1), SearchType.EQUALS));
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
            Cell cell = table.getCell(1, new Cell(2, list.get(0), SearchType.EQUALS), new Cell(3, list.get(1), SearchType.EQUALS));
            CheckBox check = new CheckBox(cell);
            check.click();
//            check.isSelected();
        }
        long endMs1 = System.currentTimeMillis();
        LOGGER.info(String.format("Click thread1 took %s ms", endMs1 - startMs1));
    }

    public class RunExeThread implements Callable<Boolean> {
        private List<String> list;
        private CheckBox check;

        public RunExeThread(List<String> list) {
            this.list = list;
        }

        @Override
        public Boolean call() throws Exception {
            Cell cell = table.getCell(1, new Cell(2, list.get(0), SearchType.EQUALS), new Cell(3, list.get(1), SearchType.EQUALS));
            check = new CheckBox(cell);
            return check.click();
        }

        public Boolean isSelected() {
            return check.isSelected();
        }
    }
}
