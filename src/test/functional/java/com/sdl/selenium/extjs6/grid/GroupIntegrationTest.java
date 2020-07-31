package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsEqual.equalTo;

public class GroupIntegrationTest extends TestBase {

    private Grid grid = new Grid().setTitle("Restaurants").setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#grouped-grid");
        driver.switchTo().frame("examples-iframe");
        grid.ready(Duration.ofSeconds(10));
        grid.ready(true);
    }

    @Test
    void rowTest() {
        Group group = new Group(grid, "Cuisine: American");
        List<Row> rows = group.getRows();
        assertThat(rows.get(0).getCell(1).getText(), equalTo("Cheesecake Factory"));
    }

    @Test
    void rowTest2() {
        List<List<String>> cellsText = grid.getCellsText("Cuisine: American");
        List<List<String>> lists;
        if ("6.0.2".equals(version)) {
            lists = Arrays.asList(
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
        } else {
            lists = Arrays.asList(
                    Arrays.asList("Cheesecake Factory", "American"),
                    Arrays.asList("Creamery", "American"),
                    Arrays.asList("Crepevine", "American"),
                    Arrays.asList("Gordon Biersch", "American"),
                    Arrays.asList("MacArthur Park", "American"),
                    Arrays.asList("Old Pro", "American"),
                    Arrays.asList("Shokolaat", "American"),
                    Arrays.asList("Slider Bar", "American"),
                    Arrays.asList("The Old Shoe", "American"),
                    Arrays.asList("University Cafe", "American")
            );
        }
        assertThat(cellsText, contains(lists.toArray()));
    }

    @Test
    void rowTest3() {
        List<String> groupsName = grid.getGroupsName();
        List<String> lists = Arrays.asList("Cuisine: American", "Cuisine: Asian", "Cuisine: Bagels", "Cuisine: Cajun", "Cuisine: Californian", "Cuisine: Caribbean", "Cuisine: Chinese", "Cuisine: Coffee", "Cuisine: English", "Cuisine: French", "Cuisine: Indian", "Cuisine: Italian", "Cuisine: Mediterranean", "Cuisine: Mexican", "Cuisine: Pizza", "Cuisine: Salad", "Cuisine: Sandwiches", "Cuisine: Sushi", "Cuisine: Tapas", "Cuisine: Thai", "Cuisine: Vegan", "Cuisine: Vietnamese");
        assertThat(lists, contains(groupsName.toArray()));
    }

    @Test
    void rowTest4() {
        Group group = grid.getGroup(2);
        assertThat(group.getNameGroup(), equalTo("Cuisine: Asian"));
    }

    @Test
    void rowTest5() {
        Group group = new Group(grid, "American");
        Row old = group.getRow(new Cell(1, "Old"));
        String text = old.getCell(1).getText();
        assertThat(text, equalTo("Old Pro"));
    }
}