package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TreeIntegrationTest extends TestBase {

    private final Tree tree = new Tree().setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#check-tree");
        driver.switchTo().frame("examples-iframe");
        tree.ready(Duration.ofSeconds(20));
        Utils.sleep(1000);
    }

    @Test
    void treeTest() {
        boolean selected = tree.select("Grocery List", "Energy foods", "Coffee");
        assertThat(selected, is(true));
        boolean isSelected = tree.isSelected("Coffee");
        assertThat(isSelected, is(true));
    }

    @Test(dependsOnMethods = "treeTest")
    void treeExpanderTest() {
        driver.get(InputData.EXTJS_EXAMPLE_URL + "#tree-xml");
        driver.switchTo().frame("examples-iframe");
        tree.ready(Duration.ofSeconds(20));
        tree.expandAllNodes();
        tree.scrollTop();
        List<List<String>> values = tree.getValues();
        assertThat(values.size(), is(439));
    }

    @Test(dependsOnMethods = "treeExpanderTest")
    void treeSelectTest() {
        tree.scrollTop();
        tree.select("Ext JS", "app", "bindinspector", "noconflict", "BaseModel.js");
        boolean isSelected = tree.isSelected("BaseModel.js");
        assertThat(isSelected, is(true));
    }

    @Test(dependsOnMethods = "treeSelectTest")
    void treeSelectTest2() {
        tree.scrollTop();
        tree.select("Ext JS", "app", "domain", "Controller.js");
        boolean isSelected = tree.isSelected("Ext JS", "app", "domain", "Controller.js");
        assertThat(isSelected, is(true));
    }

    @Test(dependsOnMethods = "treeSelectTest2")
    void treeSelectTest3() {
        tree.scrollTop();
        tree.select("Ext JS", "app", "Controller.js");
        boolean isSelected = tree.isSelected("Ext JS", "app", "Controller.js");
        assertThat(isSelected, is(true));
    }
}
