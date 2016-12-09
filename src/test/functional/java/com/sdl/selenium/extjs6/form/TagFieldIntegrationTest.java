package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;


public class TagFieldIntegrationTest extends TestBase {

    private TagField tagField = new TagField(null, "Select a state:");

    @BeforeClass
    public void startTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#form-tag");
        tagField.ready(20);
    }

    @Test
    public void tagTest() {
        assertThat(tagField.select("Alaska"), is(true));
        assertThat(tagField.getValue(), containsString("Alaska"));
        assertThat(tagField.getAllSelectedValues(), is(Arrays.asList("California", "Alaska")));
    }

    @Test(dependsOnMethods = "tagTest")
    public void selectValuesTagTest() {
        assertThat(tagField.select("Delaware", "Florida"), is(true));
        assertThat(tagField.getAllSelectedValues(), is(Arrays.asList("California", "Alaska", "Delaware", "Florida")));
    }

    @Test(dependsOnMethods = "selectValuesTagTest")
    public void removeValuesTagTest() {
        assertThat(tagField.remove("Alaska", "Delaware"), is(true));
        assertThat(tagField.getAllSelectedValues(), is(Arrays.asList("California", "Florida")));
    }

    @Test(dependsOnMethods = "removeValuesTagTest")
    public void getAllValuesTagTest() {
        assertThat(tagField.getAllValues(), is(Arrays.asList("Alabama", "Alaska", "Arizona", "Arkansas", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming")));
    }
}
