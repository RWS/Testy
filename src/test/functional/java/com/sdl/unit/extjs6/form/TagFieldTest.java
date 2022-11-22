package com.sdl.unit.extjs6.form;

import com.sdl.selenium.extjs6.form.TagField;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TagFieldTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TagField(),                         "//input[contains(concat(' ', @class, ' '), ' x-tagfield-input-field ')]"},
                {new TagField().setClasses("TagClass"),  "//input[contains(concat(' ', @class, ' '), ' x-tagfield-input-field ') and contains(concat(' ', @class, ' '), ' TagClass ')]"},
                {new TagField(container),                "//*[contains(concat(' ', @class, ' '), ' container ')]//input[contains(concat(' ', @class, ' '), ' x-tagfield-input-field ')]"},
                {new TagField(container, "TagText"),"//*[contains(concat(' ', @class, ' '), ' container ')]//label[(contains(.,'TagText') or count(*//text()[contains(.,'TagText')]) > 0)]//following-sibling::*//input[contains(concat(' ', @class, ' '), ' x-tagfield-input-field ')]"},
                {new TagField(container, "TagText", SearchType.EQUALS),"//*[contains(concat(' ', @class, ' '), ' container ')]//label[(.='TagText' or count(*//text()[.='TagText']) > 0)]//following-sibling::*//input[contains(concat(' ', @class, ' '), ' x-tagfield-input-field ')]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TagField combo, String expectedXpath) {
        assertThat(combo.getXPath(), equalTo(expectedXpath));
    }
}
