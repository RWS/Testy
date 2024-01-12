package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

public class GroupIntegrationTest extends TestBase {

    private final Grid grid = new Grid().setTitle("Restaurants").setVisibility(true);

    @BeforeClass
    public void startTests() {
        openEXTJSUrl("#grouped-grid", grid);
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
            lists = List.of(
                    List.of("Cheesecake Factory"),
                    List.of("Creamery"),
                    List.of("Crepevine"),
                    List.of("Gordon Biersch"),
                    List.of("MacArthur Park"),
                    List.of("Old Pro"),
                    List.of("Shokolaat"),
                    List.of("Slider Bar"),
                    List.of("University Cafe")
            );
        } else {
            lists = List.of(
                    List.of("Cheesecake Factory", "American"),
                    List.of("Creamery", "American"),
                    List.of("Crepevine", "American"),
                    List.of("Gordon Biersch", "American"),
                    List.of("MacArthur Park", "American"),
                    List.of("Old Pro", "American"),
                    List.of("Shokolaat", "American"),
                    List.of("Slider Bar", "American"),
                    List.of("The Old Shoe", "American"),
                    List.of("University Cafe", "American")
            );
        }
        assertThatList("Actual values: ", cellsText, containsInAnyOrder(lists.toArray()));
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
        Row old = group.getRow(new Cell(1, "Old Pro"));
        String text = old.getCell(1).getText();
        assertThat(text, equalTo("Old Pro"));
    }
}