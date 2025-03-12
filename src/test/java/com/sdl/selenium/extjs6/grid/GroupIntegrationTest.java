package com.sdl.selenium.extjs6.grid;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.WebLocatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
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
        group.expand();
        List<Row> rows = group.getRows();
        String text = rows.get(1).getCell(1).getText();
        assertThat(text, equalTo("Cheesecake Factory"));
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
                    List.of("Cuisine: American (10 Items)", ""),
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
        Group group = new Group(grid, "Cuisine: American");
        group.collapse();
        List<String> groupsName = grid.getGroupsName();
        List<String> lists = List.of("Cuisine: American (10 Items)", "Cuisine: Asian (1 Item)", "Cuisine: Bagels (1 Item)", "Cuisine: Cajun (1 Item)", "Cuisine: Californian (1 Item)", "Cuisine: Caribbean (2 Items)", "Cuisine: Chinese (3 Items)", "Cuisine: Coffee (4 Items)", "Cuisine: English (1 Item)", "Cuisine: French (2 Items)", "Cuisine: Indian (5 Items)", "Cuisine: Italian (7 Items)", "Cuisine: Mediterranean (7 Items)", "Cuisine: Mexican (2 Items)", "Cuisine: Pizza (5 Items)", "Cuisine: Salad (2 Items)", "Cuisine: Sandwiches (1 Item)", "Cuisine: Sushi (3 Items)", "Cuisine: Tapas (1 Item)", "Cuisine: Thai (4 Items)", "Cuisine: Vegan (2 Items)", "Cuisine: Vietnamese (2 Items)");
        assertThat(lists, contains(groupsName.toArray()));
    }

    @Test
    void rowTest4() {
        Group group = grid.getGroup(2);
        String nameGroup = group.getNameGroup();
        assertThat(nameGroup, equalTo("Cuisine: Asian (1 Item)"));
    }

    @Test
    void rowTest5() {
        String htmlTree = WebLocatorUtils.getOuterHTMLTree(grid);
        log.info(htmlTree);
        Group group = new Group(grid, "American");
        group.expand();
        Row old = group.getRow(new Cell(1, "Old Pro"));
        String text = old.getCell(1).getText();
        assertThat(text, equalTo("Old Pro"));
    }
}