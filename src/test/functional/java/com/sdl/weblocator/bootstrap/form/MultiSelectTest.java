package com.sdl.weblocator.bootstrap.form;

import com.sdl.bootstrap.form.Form;
import com.sdl.bootstrap.form.MultiSelect;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertTrue;

public class MultiSelectTest extends TestBase {
    private static final Logger logger = Logger.getLogger(MultiSelectTest.class);

    Form form = new Form(null, "Form Title");
    MultiSelect multiSelect = new MultiSelect(form, "Source:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void selectTwo() {
        assertTrue(multiSelect.select("Cheese", "Tomatoes"));
        List<String> list = multiSelect.getValueSelected();
        assertThat(list, contains(Arrays.asList("Cheese", "Tomatoes").toArray()));

    }

    @Test (dependsOnMethods = "selectTwo")
    public void selectAll() {
        assertTrue(multiSelect.select("Select all"));
        List<String> list = multiSelect.getValueSelected();
        assertThat(list, contains(Arrays.asList("Cheese", "Tomatoes", "Mozzarella", "Mushrooms", "Pepperoni", "Onions", "Carrots").toArray()));
    }
}
