package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;


public class TagIntegrationTest extends TestBase {

    private Tag tag = new Tag(null, "Select a state:");

    @BeforeClass
    public void startTest() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#form-tag");
        tag.ready(20);
    }

    @Test
    public void tagTest() {
        assertThat(tag.select("Alaska"), is(true));
        assertThat(tag.getValue(), containsString("Alaska"));
        assertThat(tag.getAllValues(), is(Arrays.asList("California", "Alaska")));
    }

    @Test(dependsOnMethods = "tagTest")
    public void selectValuesTagTest() {
        assertThat(tag.select("Delaware", "Florida"), is(true));
        assertThat(tag.getAllValues(), is(Arrays.asList("California", "Alaska", "Delaware", "Florida")));
    }

    @Test(dependsOnMethods = "selectValuesTagTest")
    public void removeValuesTagTest() {
        assertThat(tag.remove("Alaska", "Delaware"), is(true));
        assertThat(tag.getAllValues(), is(Arrays.asList("California", "Florida")));
    }

}
