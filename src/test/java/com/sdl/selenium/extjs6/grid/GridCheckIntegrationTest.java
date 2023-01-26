package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GridCheckIntegrationTest extends TestBase {

    private final Grid grid = new Grid().setTitle("checkOnly: false");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#checkbox-selection");
        driver.switchTo().frame("examples-iframe");
        grid.ready(Duration.ofSeconds(10));
        grid.ready(true);
    }

    @Test
    void select() {
        boolean all = grid.selectAll(true);
        assertThat(all, is(true));
        boolean selected = grid.getRow(1).isSelected();
        assertThat(selected, is(true));
    }

    @Test
    void unSelect() {
        boolean all = grid.selectAll(false);
        assertThat(all, is(true));
        boolean selected = grid.getRow(1).isSelected();
        assertThat(selected, is(false));
    }
}
