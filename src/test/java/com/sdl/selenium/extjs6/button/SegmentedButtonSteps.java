package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.extjs6.form.FieldContainer;
import com.sdl.selenium.materialui.Base;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.sdl.selenium.utils.MatcherAssertList.assertThatList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

@Slf4j
public class SegmentedButtonSteps extends Base {
    private final FieldContainer fieldContainer = new FieldContainer(null, "Toggle Group:");
    private final SegmentedButton segmentedButton = new SegmentedButton(fieldContainer);

    @And("in ExtJs I verify if SegmentedButton is present")
    public void InExtJsIVerifyIfSegmentedButtonIsPresent() {
        boolean present = segmentedButton.ready();
        assertThat(present, is(true));
    }

    @And("in ExtJs I verify if SegmentedButton has {list} values")
    public void inExtJsIVerifyIfSegmentedButtonHasValues(List<String> buttons) {
        List<String> actualButtons = segmentedButton.getButtons();
        assertThatList("Actual values", actualButtons, containsInAnyOrder(buttons.toArray()));
    }

    @And("in ExtJs I press on SegmentedButton with {string} value")
    public void inExtJsIClickOnSegmentedButtonWithValue(String value) {
        segmentedButton.press(value);
    }
}
