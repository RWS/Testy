package com.sdl.selenium.bootstrap.link;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.bootstrap.form.Form;
import com.sdl.selenium.bootstrap.form.SelectPicker;
import com.sdl.selenium.web.link.WebLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class WebLinkIntegrationTest extends TestBase {

    private Form form = new Form(null, "Form Title");
    private WebLink link = new WebLink(form, "Link");
    private SelectPicker selectPicker = new SelectPicker(form, "Tech:");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void openNewTab() {
        selectPicker.select("No ADB");
        assertThat(link.openInNewWindow(), is(true));
        assertThat(selectPicker.getValue(), equalTo("Auto"));
    }

    @Test (dependsOnMethods = "openNewTab")
    public void clickWith() {
        assertThat(link.returnDefaultWindow(), is(true));
        assertThat(selectPicker.getValue(), equalTo("No ADB"));
    }
}
