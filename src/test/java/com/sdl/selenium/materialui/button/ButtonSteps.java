package com.sdl.selenium.materialui.button;

import com.sdl.selenium.materialui.Base;
import com.sdl.selenium.web.WebLocator;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Slf4j
public class ButtonSteps extends Base {

    @And("I verify if button is present")
    public void IVerifyIfButtonIsPresent() {
        Button button = new Button(getContainer(), "Contained");
        boolean present = button.ready(Duration.ofSeconds(1));
        assertThat(present, is(true));
    }

    @And("I verify if {string} button is {status}")
    public void iVerifyIfButtonIsDisabled(String buttonName, Boolean status) {
        Button button = new Button(getContainer(), buttonName).setResultIdx(1);
        boolean enabled = button.isEnabled();
        assertThat(enabled, is(status));
    }

    @And("in MaterialUI I click on button identify by icon")
    public void inMaterialUIIClickOnButtonIdentifyByIcon() {
        Button iconButton = new Button(getContainer()){
            public <T extends com.sdl.selenium.web.button.Button> T setIconCls(final String icon) {
                WebLocator svgIcon = new WebLocator().setAttribute("data-testid", icon);
                setChildNodes(svgIcon);
                return (T) this;
            }
        }.setIconCls("AlarmIcon");
        boolean click = iconButton.click();
        assertThat(click, is(true));
    }
}
