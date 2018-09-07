package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
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
        List<Row> rows = group.getRows("Asian");
        assertThat(rows.get(0).getCell(1).getText(), equalTo("Cuisine: American (9 Items)"));
    }

    @Test
    void rowTest2() {
        List<List<String>> cellsText = grid.getCellsText("Cuisine: American");
        List<List<String>> lists = Arrays.asList(
                Collections.singletonList("Cheesecake Factory"),
                Collections.singletonList("Creamery"),
                Collections.singletonList("Crepevine"),
                Collections.singletonList("Gordon Biersch"),
                Collections.singletonList("MacArthur Park"),
                Collections.singletonList("Old Pro"),
                Collections.singletonList("Shokolaat"),
                Collections.singletonList("Slider Bar"),
                Collections.singletonList("University Cafe")
        );
        assertThat(cellsText, contains(lists.toArray()));
    }

    @Test
    void rowTest3() {
        List<String> groupsName = grid.getGroupsName();
        List<String> lists = Arrays.asList("Cuisine: American (9 Items)", "Cuisine: Asian (1 Item)");
        assertThat(groupsName, contains(lists.toArray()));
    }
}