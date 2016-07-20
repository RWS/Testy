package com.sdl.selenium.extjs6.slider;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SliderIntegrationTest extends TestBase {

    private Slider slider = new Slider().setLabel("Sound Effects:", SearchType.DEEP_CHILD_NODE_OR_SELF);

    @BeforeClass
    public void startTests() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#slider-field");
    }

    @Test
    void sliderTest() {
        slider.ready(20);

        slider.move(60);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("60"));
        slider.move(0);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("0"));
        slider.move(40);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("40"));
        slider.move(80);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("80"));
        slider.move(30);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("30"));
        slider.move(0);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("0"));
        slider.move(99);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("99"));
        slider.move(1);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("1"));
        slider.move(57);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("57"));
        slider.move(58);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("58"));
        slider.move(57);
        assertThat(slider.getAttribute("aria-valuenow"), equalTo("57"));
    }
}
