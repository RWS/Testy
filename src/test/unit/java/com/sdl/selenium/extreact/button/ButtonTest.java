package com.sdl.selenium.extreact.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ButtonTest {

    public static WebLocator container = new WebLocator("container");
    private static final String CONTAINER_PATH = "//*[contains(concat(' ', @class, ' '), ' container ')]";

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Button(), "//div[contains(concat(' ', @class, ' '), ' x-button ')]"},
                {new Button().setClasses("ButtonClass"), "//div[contains(concat(' ', @class, ' '), ' x-button ') and contains(concat(' ', @class, ' '), ' ButtonClass ')]"},
                {new Button(container), CONTAINER_PATH + "//div[contains(concat(' ', @class, ' '), ' x-button ')]"},
                {new Button(null, "Normal"), "//div[contains(concat(' ', @class, ' '), ' x-button ') and (.='Normal' or count(*//text()[.='Normal']) > 0)]"},
                {new Button(container, "Normal").setSearchTextType(SearchType.CONTAINS), CONTAINER_PATH + "//div[contains(concat(' ', @class, ' '), ' x-button ') and (contains(.,'Normal') or count(*//text()[contains(.,'Normal')]) > 0)]"},
                {new Button(container, "Normal", SearchType.CONTAINS), CONTAINER_PATH + "//div[contains(concat(' ', @class, ' '), ' x-button ') and (contains(.,'Normal') or count(*//text()[contains(.,'Normal')]) > 0)]"},
                {new Button(container).setId("ID"), CONTAINER_PATH + "//div[@id='ID' and contains(concat(' ', @class, ' '), ' x-button ')]"},
                {new Button(container).setIconCls("swap-filters"), CONTAINER_PATH + "//div[contains(concat(' ', @class, ' '), ' x-button ') and count(.//*[contains(concat(' ', @class, ' '), ' swap-filters ')]) > 0]"},

                {new Button(container, "Don't Accept"), CONTAINER_PATH + "//div[contains(concat(' ', @class, ' '), ' x-button ') and (.=\"Don't Accept\" or count(*//text()[.=\"Don't Accept\"]) > 0)]"},
                {new Button(container, "Don'\"t Accept"), CONTAINER_PATH + "//div[contains(concat(' ', @class, ' '), ' x-button ') and (.=concat(\"Don'\", '\"', \"t Accept\") or count(*//text()[.=concat(\"Don'\", '\"', \"t Accept\")]) > 0)]"},
                {new Button(container, "It was \"good\" ok!"), CONTAINER_PATH + "//div[contains(concat(' ', @class, ' '), ' x-button ') and (.='It was \"good\" ok!' or count(*//text()[.='It was \"good\" ok!']) > 0)]"},
                {new Button(container, "Don't do it \"now\" ok!"), CONTAINER_PATH + "//div[contains(concat(' ', @class, ' '), ' x-button ') and (.=concat(\"Don't do it \", '\"', \"now\", '\"', \" ok!\") or count(*//text()[.=concat(\"Don't do it \", '\"', \"now\", '\"', \" ok!\")]) > 0)]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Button button, String expectedXpath) {
        assertThat(button.getXPath(), equalTo(expectedXpath));
    }
}