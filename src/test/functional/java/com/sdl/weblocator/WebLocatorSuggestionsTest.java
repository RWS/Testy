package com.sdl.weblocator;

import com.sdl.selenium.WebLocatorSuggestions;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.Button;
import com.sdl.selenium.web.form.TextField;
import com.sdl.selenium.web.utils.Utils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Beni Lar on 10/27/2015.
 */
public class WebLocatorSuggestionsTest extends TestBase {

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    /**
     * Expected suggestions:
     * WebLocatorSuggestions - The parent of this webLocator was not found.
     */
    @Test
    public void parentNotFound() {
        WebLocator window = new WebLocator().setId("foo");
        TextField inputWithLabel = new TextField(window).setLabel("User Name:").setLabelPosition("//following-sibling::*//");

        WebLocatorSuggestions.getSuggestions(inputWithLabel);
    }

    /**
     * Expected suggestions:
     * WebLocatorSuggestions - Could not find the label 'User Name' using  tag 'label' and search type '[EQUALS]'
     * WebLocatorSuggestions - But found it using search types [CONTAINS, TRIM]
     */
    @Test
    public void labelNotFound() {
        TextField inputWithLabel = new TextField().setLabel("User Name").setLabelPosition("//following-sibling::*//");

        WebLocatorSuggestions.getSuggestions(inputWithLabel);
    }

    /**
     * Expected suggestions:
     * WebLocatorSuggestions - No elements found at the specified label position: //following-sibling::
     *//*//
     */
    @Test
    public void labelPositionNotFound() {
        TextField inputWithLabel = new TextField().setLabel("User Name:").setLabelPosition("//following-sibling::*//*//");

        WebLocatorSuggestions.getSuggestions(inputWithLabel);
    }

    /**
     * Expected suggestions:
     * WebLocatorSuggestions - Found the label: <label class="control-label">User Name:</label>
     * <label class="control-label">User Name:</label>
     * WebLocatorSuggestions - No 'div' elements found at the specified label position: //following-sibling:://
     * WebLocatorSuggestions - All elements found at the specified label position: <span id="userName2" data-bind="span" class="uneditable-input input-block-level"></span>
     * <input id="userName" data-bind="userName" type="text" class="input-block-level">
     * WebLocatorSuggestions - Found 2 matches by removing setTag("div") : <span id="userName2" data-bind="span" class="uneditable-input input-block-level"></span>
     * <input id="userName" data-bind="userName" type="text" class="input-block-level">
     */
    @Test
    public void tagIsWrong() {
        WebLocator inputWithLabel = new WebLocator().setLabel("User Name:").setLabelPosition("//following-sibling::*//").setTag("div");

        WebLocatorSuggestions.getSuggestions(inputWithLabel);
    }

    /**
     * Expected suggestions:
     * WebLocatorSuggestions - Found the label: <label class="control-label">User Name:</label>
     * WebLocatorSuggestions - 'input' elements found at the specified label position: <input id="userName" data-bind="userName" type="text" class="input-block-level">
     * WebLocatorSuggestions - Found 1 matches by removing setCls("foo") : <input id="userName" data-bind="userName" type="text" class="input-block-level">
     */
    @Test
    public void labelAndLabelPositionAreCorrect() {

        WebLocator window = new WebLocator().setId("myModal");
        TextField inputWithLabel = new TextField(window).setLabel("User Name:").setLabelPosition("//following-sibling::*//").setCls("foo");

        Button button = new Button().setText("Launch demo modal", SearchType.TRIM);
        button.click();
        Utils.sleep(500);

        WebLocatorSuggestions.getSuggestions(inputWithLabel);
    }
}
