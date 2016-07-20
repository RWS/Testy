package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class TreeIntegrationTest extends TestBase {

    private Tree tree = new Tree();

    @BeforeClass
    public void startTests() {
        driver.get("http://examples.sencha.com/extjs/6.0.2/examples/kitchensink/#check-tree");
    }

    @Test
    void treeTest() {
        tree.ready(20);

        boolean selected = tree.select("Grocery List", "Energy foods", "Coffee");
        assertThat(selected, is(true));
    }
}
