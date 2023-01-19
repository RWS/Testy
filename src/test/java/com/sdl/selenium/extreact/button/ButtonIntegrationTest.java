package com.sdl.selenium.extreact.button;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class ButtonIntegrationTest extends TestBase {

    private final Button normal = new Button(null, "Normal");
//    private final Button icon = new Button(table).setIconCls("button-home-small").setVisibility(true);
//    private final Button iconAndText = new Button(table, "Medium").setIconCls("button-home-medium").setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTREACT_EXAMPLE_URL + "buttons/button");
        driver.switchTo().frame("examples-iframe");
        normal.ready(Duration.ofSeconds(10));
        Utils.sleep(1000);
    }

    @Test
    void buttonTest() {
        normal.click();
    }

//    @Test
//    public void iconButtonTest() {
//        icon.click();
//    }
//
//    @Test
//    public void iconAndTextButtonTest() {
//        iconAndText.click();
//    }
}
