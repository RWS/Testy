package com.sdl.weblocator.bootstrap.table;

import com.sdl.selenium.bootstrap.form.CheckBox;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.web.table.Table;
import com.sdl.selenium.web.table.TableCell;
import com.sdl.selenium.web.table.TableRow;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TableDemoTest extends TestBase {

    private Form form = new Form("Form Table");
    private Table table = new Table(form);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void selectUser() {
        TableRow row = table.getRow(new TableCell(2, "Peter"), new TableCell(3, "Parker"));
        CheckBox checkBox = new CheckBox(row);

        checkBox.click();
    }

}
