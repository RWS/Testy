package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.form.ComboBox;
import com.sdl.selenium.extjs6.form.DateField;
import com.sdl.selenium.extjs6.form.TextField;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class CellEditingIntegrationTest extends TestBase {

    private Grid grid;

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#cell-editing");
        driver.switchTo().frame("examples-iframe");
        grid = new Grid().setTitle("Cell Editing Plants").setVisibility(true);
        grid.ready(10);
        grid.ready(true);
    }

    @Test
    void cellEditingTest() {
        grid.getRow(1).getCell(1).click();
        TextField textField = grid.getEditor();
        textField.setValue("test");
        textField.sendKeys(Keys.ENTER);
    }

    @Test
    void cellEditingComboBoxTest() {
        grid.getRow(1).getCell(2).click();
        ComboBox comboBox = grid.getEditor();
        comboBox.select("Sunny");
        comboBox.sendKeys(Keys.ENTER);
    }

    @Test
    void cellEditingNumberFieldTest() {
        grid.getRow(1).getCell(3).click();
        TextField textField = grid.getEditor();
        textField.setValue("19");
        textField.sendKeys(Keys.ENTER);
    }

    @Test
    void cellEditingDateFieldTest() {
        grid.getRow(1).getCell(4).click();
        DateField textField = grid.getEditor();
        textField.select("19/03/2019");
        textField.sendKeys(Keys.ENTER);
    }
}
