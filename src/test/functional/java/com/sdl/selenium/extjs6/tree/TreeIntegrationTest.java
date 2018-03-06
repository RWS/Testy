package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TreeIntegrationTest extends TestBase {

    private Tree tree = new Tree().setVisibility(true);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.EXTJS_EXAMPLE_URL +"#check-tree");
        tree.ready(20);
        Utils.sleep(1000);
    }

    @Test
    void treeTest() {
        boolean selected = tree.select("Grocery List", "Energy foods", "Coffee");
        assertThat(selected, is(true));
    }
}
