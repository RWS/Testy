package com.sdl.selenium.extjs6.tree;

import com.sdl.selenium.extjs6.grid.Options;
import com.sdl.selenium.materialui.Base;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.Matchers.containsInAnyOrder;

@Slf4j
public class TreeSteps extends Base {
    private final Tree tree = new Tree().setTitle("Files");

    @Then("I verify if Tree has values:")
    public void iVerifyIfTreeHasValues(List<List<String>> values) {
        long startMs = System.currentTimeMillis();
        Options<List<String>> options = new Options<>(values.get(0));
        List<List<String>> cellsText = tree.getCellsText(options);
        long endMs = System.currentTimeMillis();
        long rez = endMs - startMs;
        log.info("performance took {} ms", rez);
        assertThatList("Actual values", cellsText, containsInAnyOrder(values.toArray()));
    }

    @When("I expand all nodes in Tree")
    public void iExpandAllNodesInTree() {
        tree.expandAllNodes();
        tree.scrollTop();
    }
}
