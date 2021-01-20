package com.sdl.selenium.extjs6.slider;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SliderIntegrationTest extends TestBase {

    private Slider slider = new Slider().setLabel("Sound Effects:", SearchType.DEEP_CHILD_NODE_OR_SELF);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#slider-field");
        driver.switchTo().frame("examples-iframe");
    }

    @Test
    void sliderTest() {
        slider.ready(Duration.ofSeconds(25));

        slider.move(60);
        assertThat(slider.getValue(), equalTo(60));
        slider.move(0);
        assertThat(slider.getValue(), equalTo(0));
        slider.move(40);
        assertThat(slider.getValue(), equalTo(40));
        slider.move(3);
        assertThat(slider.getValue(), equalTo(3));
        slider.move(80);
        assertThat(slider.getValue(), equalTo(80));
        slider.move(30);
        assertThat(slider.getValue(), equalTo(30));
        slider.move(0);
        assertThat(slider.getValue(), equalTo(0));
        slider.move(99);
        assertThat(slider.getValue(), equalTo(99));
//        slider.move(10);
//        assertThat(slider.getValue(), equalTo(10));
        slider.move(57);
        assertThat(slider.getValue(), equalTo(57));
        slider.move(58);
        assertThat(slider.getValue(), equalTo(58));
        slider.move(57);
        assertThat(slider.getValue(), equalTo(57));
        slider.move(0);
        assertThat(slider.getValue(), equalTo(0));
//        slider.move(1);
//        assertThat(slider.getValue(), equalTo(1));
//        slider.move(0);
//        assertThat(slider.getValue(), equalTo(0));
    }
}
