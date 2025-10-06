package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.grid.Options;
import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TreeIntegrationTest extends TestBase {

    private final Tree tree = new Tree().setVisibility(true);

    @BeforeClass
    public void startTests() {
        openEXTJSUrl("#check-tree", tree);
    }

    @Test
    void treeTest() {
        boolean selected = tree.select(List.of("Grocery List", "Energy foods", "Coffee"));
        assertThat(selected, is(true));
        boolean isSelected = tree.isSelected("Coffee");
        assertThat(isSelected, is(true));
    }

    @Test(dependsOnMethods = "treeTest")
    void treeExpanderTest() {
        openEXTJSUrl("#tree-xml", tree);
        tree.expandAllNodes();
        tree.scrollTop();
        Options<List<String>> options = new Options<>(new ArrayList<>());
        options.setResetIndex(5);
        List<List<String>> values = tree.getCellsValues(options);
        assertThat(values.size(), is(439));
    }

    @Test(dependsOnMethods = "treeExpanderTest")
    void treeSelectTest() {
        tree.scrollTop();
        tree.select(List.of("Ext JS", "app", "bindinspector", "noconflict", "BaseModel.js"));
        boolean isSelected = tree.isSelected("BaseModel.js");
        assertThat(isSelected, is(true));
    }

    @Test(dependsOnMethods = "treeSelectTest")
    void treeSelectTest2() {
        tree.scrollTop();
        tree.select(List.of("Ext JS", "app", "domain", "Controller.js"));
        boolean isSelected = tree.isSelected(List.of("Ext JS", "app", "domain", "Controller.js"));
        assertThat(isSelected, is(true));
    }

    @Test(dependsOnMethods = "treeSelectTest2")
    void treeSelectTest3() {
        WebDriverConfig.getDriver().navigate().refresh();
        driver.switchTo().frame("examples-iframe");
        tree.ready();
        boolean select = tree.select(List.of("Ext JS", "app", "Controller.js"), SearchType.EQUALS);
        boolean isSelected = tree.isSelected(List.of("Ext JS", "app", "Controller.js"), SearchType.EQUALS);
        assertThat(isSelected, is(true));
    }

    @Test(dependsOnMethods = "treeSelectTest3")
    void treeSelectTest4() {
        tree.scrollTop();
        List<String> nodes = List.of("Ext JS", "grid", "plugin", "Editing.js");
        tree.select(true, nodes, SearchType.EQUALS);
        tree.scrollTop();
        boolean selected = tree.isSelected(nodes, SearchType.EQUALS);
        assertThat(selected, is(true));
    }
}
