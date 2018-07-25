package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class GroupIntegrationTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupIntegrationTest.class);

    private Grid grid = new Grid().setTitle("Restaurants").setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#grouped-grid");
        grid.ready(10);
        grid.ready(true);
    }

    @Test
    void rowTest() {
        Group group = new Group(grid, "Cuisine: American");
        List<Row> rows = group.getRows();
        assertThat(rows.get(0).getCell(1).getText(), equalTo("Cuisine: American (9 Items)"));
    }
}