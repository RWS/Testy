package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.RetryUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class ComboBoxIntegrationTest extends TestBase {

    private final ComboBox comboBox = new ComboBox(null, "Select State:");

    @BeforeClass
    public void startTest() {
        openEXTJSUrl("#simple-combo", comboBox);
    }

    public static Function<String, Boolean> select(Duration duration, SearchType... searchTypes) {
        return value -> {
            WebLocator boundList = new WebLocator("x-boundlist").setExcludeClasses("x-masked").setVisibility(true);
            WebLocator option = new WebLocator(boundList).setTag("li").setText(value, searchTypes).setRender(duration).setInfoMessage(value);
            Boolean click = RetryUtils.retry(2, () -> {
                boolean doClick = option.doClick();
                return doClick && !option.ready(Duration.ofMillis(200));
            });
            return click;
        };
    }

    @Test
    public void comboBoxTest() {
        boolean select = comboBox.select("New York", select(Duration.ofSeconds(1), SearchType.CONTAINS));
        assertThat(select, is(true));
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