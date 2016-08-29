package com.sdl.selenium.extjs3.window;

import com.sdl.selenium.extjs3.ExtJsComponent;
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.utils.config.WebDriverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>This class represents the Ext.MessageBox singleton window</p>
 * <p>In UI is used like:</p>
 * <pre>
 *     Ext.Msg.alert(...)
 *     Ext.Msg.prompt(...)
 *     Ext.Msg.show(...)
 *     Ext.Msg.confirm(...)
 * </pre>
 */
public class MessageBoxWindow extends Window {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBox.class);
    
    public MessageBoxWindow() {
        super(true);
        withClassName("MessageBoxWindow");
        withInfoMessage("MessageBoxWindow");
        withClasses("x-window-dlg");
    }

    public MessageBoxWindow(String title) {
        this();
        withTitle(title);
    }

    public MessageBoxWindow(String title, boolean isInternationalized) {
        this();
        withTitle(title, isInternationalized);
    }

    public static String OK_TEXT = "OK";
    public static String CANCEL_TEXT = "Cancel";
    public static String YES_TEXT = "Yes";
    public static String NO_TEXT = "No";

    private Button okButton = new Button(this, OK_TEXT);
    private Button cancelButton = new Button(this, CANCEL_TEXT);
    private Button yesButton = new Button(this, YES_TEXT);
    private Button noButton = new Button(this, NO_TEXT);

    public Button getOkButton() {
        return okButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }


    /**
     * instant get MessageBox text if is present
     *
     * @return MessageBox text if is present
     */
    public String getMessage() {
        return getMessage(0);
    }
    
    public String getMessage(int waitSeconds) {
        // TODO verify if can be be simplified using WebLocator instead of ExtJsComponent
        ExtJsComponent mbTextElement = new ExtJsComponent("ext-mb-text", this);
        mbTextElement.withInfoMessage("MessageBox ext-mb-text");
        String msg;
        if (waitSeconds == 0) {
            msg = mbTextElement.getText(true);
        } else {
            msg = mbTextElement.waitTextToRender(waitSeconds);
        }
        return msg;
    }

    public String press(String buttonText) {
        Button button;
        if(OK_TEXT.equals(buttonText)){
            button = okButton;
        } else if(CANCEL_TEXT.equals(buttonText)){
            button = cancelButton;
        } else if(YES_TEXT.equals(buttonText)){
            button = yesButton;
        } else if(NO_TEXT.equals(buttonText)){
            button = noButton;
        } else {
            button = new Button(this, buttonText);
        }
        return press(button);
    }

    private String press(final Button button) {
        if (WebDriverConfig.isIE() && !isVisible()) {
            LOGGER.warn("messageBoxWindow is not visible");
            return null;
        }
        String msg = getMessage();
        if (msg != null) {
            LOGGER.info("Click on button " + button.getPathBuilder().getText() + " in the window with message: " + msg);
            button.click();
        } else {
            LOGGER.warn("There is no Message or Dialog");
        }
        return msg;
    }

    public String pressOK() {
        return press(okButton);
    }

    public String pressYes() {
        return press(yesButton);
    }

    public String pressNo() {
        return press(noButton);
    }
}
