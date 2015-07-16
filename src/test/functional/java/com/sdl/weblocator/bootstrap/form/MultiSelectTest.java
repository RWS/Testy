package com.sdl.weblocator.bootstrap.form;

import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.bootstrap.form.MultiSelect;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class MultiSelectTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiSelectTest.class);

    private Form form = new Form(null, "Form Title");
    private MultiSelect multiSelect = new MultiSelect(form, "Source:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void selectTwo() {
        assertThat(multiSelect.select("Cheese", "Tomatoes"), is(true));
        List<String> list = multiSelect.getValueSelected();
        assertThat(list, contains(Arrays.asList("Cheese", "Tomatoes").toArray()));
    }

    @Test (dependsOnMethods = "selectTwo")
    public void selectAll() {
        assertThat(multiSelect.select("Select all"), is(true));
        List<String> list = multiSelect.getValueSelected();
        assertThat(list, contains(Arrays.asList("Cheese", "Tomatoes", "Mozzarella", "Mushrooms", "Pepperoni", "Onions", "Carrots").toArray()));
    }
}
