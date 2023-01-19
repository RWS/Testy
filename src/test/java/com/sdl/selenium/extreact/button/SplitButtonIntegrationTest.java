package com.sdl.selenium.extreact.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SplitButtonIntegrationTest extends TestBase {

    private final SplitButton normal = new SplitButton(null, "Normal").setResultIdx(3);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTREACT_EXAMPLE_URL + "buttons/splitbutton");
        driver.switchTo().frame("examples-iframe");
        normal.ready(Duration.ofSeconds(10));
        Utils.sleep(1000);
    }

    @Test
    void splitButton() {
        normal.clickOnMenu("Menu Item 3");
    }


    @Test(dependsOnMethods = "splitButton")
    void splitButtonIsEnabled() {
        assertThat(normal.isEnabled(), is(true));
        Utils.sleep(1000);
    }

    @Test(dependsOnMethods = "splitButtonIsEnabled")
    public void getAllMenuValuesTest() {
        assertThat(normal.getAllMenuValues(), is(Arrays.asList("Menu Item 1", "Menu Item 2", "Menu Item 3")));
    }

//    @Test(dependsOnMethods = "getAllMenuValuesTest")
//    public void getAllEnabledMenuValuesTest() {
//        assertThat(normal.getAllEnabledValues(), is(Arrays.asList("Menu Item 1", "Menu Item 2", "Menu Item 3")));
//    }
}
