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

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TableIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableIntegrationTest.class);

    private final Form form = new Form(null, "Form Table");
    private final Table table = new Table(form);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void verifyIfCheckBoxIsPresent() {
        Cell cell = table.getCell(1, new Cell(2, "John", SearchType.EQUALS), new Cell(3, "Carter", SearchType.EQUALS));
        assertThat(new CheckBox(cell).isPresent(), is(true));
    }

    @Test
    public void verifyIfCellByTextIsPresent() {
        Row row = table.getRow(new Cell("Carter", SearchType.EQUALS));
        assertThat(row.isPresent(), is(true));
    }

    @Test
    public void verifyIfButtonsIsPresent() {
        Row row = table.getRow(1, new Cell(2, "John", SearchType.EQUALS), new Cell(3, "Carter", SearchType.EQUALS));
        Button first = new Button(row).setText("Details", SearchType.CONTAINS);
        Button second = new Button(row, "Remove");
        assertThat(first.isPresent(), is(true));
        assertThat(second.isPresent(), is(true));
    }

    @Test
    public void verifyHeaderName() {
        Cell row = new Cell(1, "Row", SearchType.EQUALS).setTag("th");
        Cell firstName = new Cell(2, "First Name", SearchType.EQUALS).setTag("th");
        Cell lastName = new Cell(3, "Last Name", SearchType.EQUALS).setTag("th");
        Cell email = new Cell(4, "Email", SearchType.EQUALS).setTag("th");
        Cell buttons = new Cell(5, "Actions", SearchType.EQUALS).setTag("th");
        assertThat(table.getRow(row, firstName, lastName, email, buttons).ready(), is(true));
    }

    @Test
    public void getAllTexts() {
        List<List<String>> listOfList = List.of(
                List.of("John", "Carter", "johncarter@mail.com"),
                List.of("Peter", "Parker", "peterparker@mail.com"),
                List.of("John", "Moore", "johnmoore@mail.com"),
                List.of("David", "Miller", "davidmiller@mail.com"),
                List.of("Nick", "White", "nickwhite@mail.com"),
                List.of("Bob", "Smith", "bobsmith@mail.com")
        );

        List<List<String>> cellsText = table.getCellsText(1, 5);
        assertThatList("Actual values: ", cellsText, contains(listOfList.toArray()));
    }

    @Test
    public void getAllTextsFromRow() {
        List<String> listOfList = List.of("John", "Carter", "johncarter@mail.com", "Details Remove");

        Row row = table.getRow(new Cell(2, "John", SearchType.EQUALS), new Cell(3, "Carter", SearchType.EQUALS));
        List<String> cellsText = row.getCellsText(1);
        assertThatList("Actual values: ", cellsText, equalTo(listOfList));
    }

    @Test
    public void ThreadTest() throws InterruptedException {
        ArrayList<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("John", "Carter"));
        lists.add(Arrays.asList("Peter", "Parker"));
        lists.add(Arrays.asList("John", "Moore"));
        lists.add(Arrays.asList("David", "Miller"));
        lists.add(Arrays.asList("Nick", "White"));
        lists.add(Arrays.asList("Bob", "Smith"));

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
                globalClick = false;
                LOGGER.warn("Warning waiting for table click on checkboxes ", e);
            }
        }

        pool.shutdown();
        System.gc();

        assertThat(globalClick, is(true));

//        for (List<String> list : lists) {
//            Cell cell = table.getCell(1, new Cell(2, list.get(0), SearchType.EQUALS), new Cell(3, list.get(1), SearchType.EQUALS));
//            CheckBox check = new CheckBox(cell);
//            exeThread = new RunExeThread(check);
//            assertThat(exeThread.isSelected());
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
        private final List<String> list;
        private CheckBox check;

        public RunExeThread(List<String> list) {
            this.list = list;
        }

        @Override
        public Boolean call() {
            Cell cell = table.getCell(1, new Cell(2, list.get(0), SearchType.EQUALS), new Cell(3, list.get(1), SearchType.EQUALS));
            check = new CheckBox(cell);
            return check.click();
        }

        public Boolean isSelected() {
            return check.isChecked();
        }
    }
}
