package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.BaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class StepDefinitions extends BaseStepDefinitions {

    private int sum;

    @When("I evaluate sum {int} and {int}")
    public void eval(Integer a, Integer b) {
        log.info("summing up {} and {}", a, b);
        sum = a + b;
        storage.set("MY CUSTOM KEY", sum);
    }

    @Then("The sum is {int}")
    public void sumIs(Integer expectedSum) {
        log.info("evaluating {} as {}", sum, expectedSum);
        assertThat(sum, is(expectedSum));
        int myCustomKey = storage.get("MY CUSTOM KEY");
        assertThat(myCustomKey, is(expectedSum));
    }
}
