package com.sdl.selenium.extjs6.window;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.conditions.ConditionManager;
import com.sdl.selenium.conditions.RenderSuccessCondition;
import com.sdl.selenium.extjs6.button.Button;
import com.sdl.selenium.web.SearchType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MessageBoxIntegrationTest extends TestBase {

    private final Button dialogButton = new Button().setText("Confirm Dialog", SearchType.DEEP_CHILD_NODE_OR_SELF);

    @BeforeClass
    public void startTests() {
        openEXTJSUrl("#message-box", dialogButton);
        dialogButton.click();
    }

    @Test
    void messageBoxTest() {
        MessageBox messageBox = new MessageBox("Confirm", "Are you sure you want to do that?");
        ConditionManager conditionManager = new ConditionManager(Duration.ofSeconds(10));
        conditionManager.add(new RenderSuccessCondition(messageBox));

        assertThat(conditionManager.execute().isSuccess(), is(true));
        messageBox.pressYes();
    }
}
