package com.sdl.selenium.materialui.list;

import com.sdl.selenium.materialui.Base;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@Slf4j
public class ListSteps extends Base {

    @And("I verify if List is present")
    public void IVerifyIfListIsPresent() {
        List list = new List(getContainer());
        assertThat(list.isPresent(), is(true));
    }

    @And("I verify if List has items {list}")
    public void iVerifyIfListHasItems(ArrayList<String> items) {
        List list = new List(getContainer()).setResultIdx(2);
        ArrayList<String> actualValues = list.getValues();
        assertThatList("Actual values: ", actualValues, containsInAnyOrder(items.toArray()));
    }
}
