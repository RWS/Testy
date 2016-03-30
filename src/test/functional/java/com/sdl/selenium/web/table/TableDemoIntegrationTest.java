package com.sdl.selenium.web.table;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.CheckBox;
import com.sdl.selenium.bootstrap.form.Form;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TableDemoIntegrationTest extends TestBase {

    private Form form = new Form("Form Table");
    private Table table = new Table(form);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void selectUser() {
        Row row = table.getRow(new Cell(2, "Peter"), new Cell(3, "Parker"));
        CheckBox checkBox = new CheckBox(row);

        checkBox.click();
    }

}
