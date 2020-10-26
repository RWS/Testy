package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.form.ComboBox;
import com.sdl.selenium.extjs6.form.DateField;
import com.sdl.selenium.extjs6.form.TextField;
import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class CellEditingIntegrationTest extends TestBase {

    private Grid grid;

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#cell-editing");
        driver.switchTo().frame("examples-iframe");
        grid = new Grid().setTitle("Cell Editing Plants").setVisibility(true);
        grid.ready(Duration.ofSeconds(10));
        grid.ready(true);
    }

    @Test
    void cellEditingTest() {
        TextField textField = grid.getEditor(grid.getRow(1).getCell(1));
        textField.setValue("test");
        textField.sendKeys(Keys.ENTER);
    }

    @Test
    void cellEditingComboBoxTest() {
        ComboBox comboBox = grid.getEditor(grid.getRow(1).getCell(2));
        comboBox.select("Sunny");
        comboBox.sendKeys(Keys.ENTER);
    }

    @Test
    void cellEditingNumberFieldTest() {
        TextField textField = grid.getEditor(grid.getRow(1).getCell(3));
        textField.setValue("19");
        textField.sendKeys(Keys.ENTER);
    }

    @Test
    void cellEditingDateFieldTest() {
        DateField textField = grid.getEditor(grid.getRow(1).getCell(4));
        textField.select("19/03/2019");
        textField.doSendKeys(Keys.ENTER);
    }
}
