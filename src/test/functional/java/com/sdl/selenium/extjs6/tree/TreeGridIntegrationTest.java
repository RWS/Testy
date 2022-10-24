package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.grid.Cell;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class TreeGridIntegrationTest extends TestBase {

    private final Tree tree = new Tree().setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#tree-grid");
        driver.switchTo().frame("examples-iframe");
        tree.ready(Duration.ofSeconds(20));
        Utils.sleep(1000);
    }

    @Test
    void treeTest0() {
        Tree.Row treeNode = tree.getNode(List.of("Project: Shopping", "Housewares"));
        Cell cell = treeNode.getCell(4);
        cell.check(true);
        assertThat(cell.isChecked(), is(true));
    }

    @Test
    void treeTest() {
        List<List<String>> expectedValues = List.of(
                List.of("Project: Testing", "2 hours", "Core Team"),
                List.of("Mac OSX", "45 mins", "Tommy Maintz"),
                List.of("FireFox", "15 mins", "Tommy Maintz"),
                List.of("Safari", "15 mins", "Tommy Maintz"),
                List.of("Chrome", "15 mins", "Tommy Maintz")
        );
        List<List<String>> actualValues = tree.getNodesValues(Arrays.asList("Project: Testing", "Mac OSX", "FireFox"), 4, 5);
        actualValues.forEach(System.out::println);
        assertThat(actualValues, containsInAnyOrder(expectedValues.toArray()));
    }

    @Test
    void treeTest1() {
        List<List<String>> expectedValues = List.of(
                List.of("Project: Testing", "2 hours", "Core Team"),
                List.of("Mac OSX", "45 mins", "Tommy Maintz"),
                List.of("FireFox", "15 mins", "Tommy Maintz"),
                List.of("Safari", "15 mins", "Tommy Maintz"),
                List.of("Chrome", "15 mins", "Tommy Maintz")
        );
        List<List<String>> actualValues = tree.getNodesValues(Arrays.asList("Project: Testing", "Mac OSX"), 4, 5);
        actualValues.forEach(System.out::println);
        assertThat(actualValues, containsInAnyOrder(expectedValues.toArray()));
    }

    @Test
    void treeTest2() {
        List<List<String>> selected = tree.getValues(4, 5);
        assertThat(selected.size(), is(8));
    }
}
