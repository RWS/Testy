package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.materialui.Base;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatListVertical;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@Slf4j
public class SplitButtonSteps extends Base {
    private final Panel panel = new Panel(null, "Panel with toolbar with diverse contents");
    private final SplitButton splitButton = new SplitButton(panel, "Button w/ Menu");

    @And("in ExtJs I verify if SplitButton is present")
    public void InExtJsIVerifyIfSegmentedButtonIsPresent() {
        boolean present = splitButton.ready();
        assertThat(present, is(true));
    }

    @And("in ExtJs I verify if SplitButton has following values:")
    public void inExtJsIVerifyIfSplitButtonHasFollowingValues(List<String> values) {
        List<String> allMenuValues = splitButton.getMenuValuesExtended();
        assertThatListVertical("Actual values", allMenuValues, containsInAnyOrder(values.toArray()));
    }
}
