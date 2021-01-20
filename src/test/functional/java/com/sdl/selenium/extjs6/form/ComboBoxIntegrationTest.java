package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class ComboBoxIntegrationTest extends TestBase {

    private ComboBox comboBox = new ComboBox(null, "Select State:");

    @BeforeClass
    public void startTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#simple-combo");
        driver.switchTo().frame("examples-iframe");
        comboBox.ready(Duration.ofSeconds(20));
        Utils.sleep(2000);
    }

    @Test
    public void comboBoxTest() {
        assertThat(comboBox.select("New York"), is(true));
    }

    @Test(dependsOnMethods = "comboBoxTest")
    public void verifyComboBoxValue() {
        assertThat(comboBox.getValue(), equalTo("New York"));
    }

    @Test(dependsOnMethods = "verifyComboBoxValue")
    public void comboBoxTest2() {
        assertThat(comboBox.select("New York"), is(true));
    }

    @Test
    public void comboBoxGetValues() {
        List<String> allValues = comboBox.getAllValues();
        assertThat(allValues, is(Arrays.asList("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming")));
    }
}