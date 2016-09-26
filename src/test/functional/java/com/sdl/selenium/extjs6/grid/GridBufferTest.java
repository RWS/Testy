package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GridBufferTest extends TestBase {

    private Grid grid = new Grid();

    @BeforeClass
    public void startTests() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/classic/grid/buffer-grid.html");
        grid.ready(20);
    }

    @Test
    void gridTest() {
        grid.scrollBottom();
//        assertThat(selected, is(true));
        Utils.sleep(1);
    }
}
