package com.extjs.selenium.window;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.button.Button;
import com.sdl.selenium.web.WebDriverConfig;
import org.apache.log4j.Logger;

/**
 * The Info/Error/Warning Message Box from ExtJs
 */
public class MessageBox {
    private static final Logger logger = Logger.getLogger(MessageBox.class);

    public static String BUTTON_OK = "OK";
    public static String BUTTON_CANCEL = "Cancel";
    public static String BUTTON_YES = "Yes";
    public static String BUTTON_NO = "No";

    private static Window messageBoxWindow = new Window(true).setClasses("x-window-dlg").setInfoMessage("MessageBox");

    private static Button okButton = new Button(messageBoxWindow, BUTTON_OK);
    private static Button cancelButton = new Button(messageBoxWindow, BUTTON_CANCEL);
    private static Button yesButton = new Button(messageBoxWindow, BUTTON_YES);
    private static Button noButton = new Button(messageBoxWindow, BUTTON_NO);

    private MessageBox(){}

    /**
     * instant get MessageBox text if is present
     *
     * @return
     */
    public static String getMessage() {
        return getMessage(0);
    }

    /**
     * Wait MessageBox to appear and get text.
     * Use this method when you realy expect some message box but don't know exact time when it appear
     *
     * @param waitSeconds
     * @return
     */
    public static String getMessage(int waitSeconds) {
        ExtJsComponent mbTextElement = new ExtJsComponent("ext-mb-text", messageBoxWindow);
        mbTextElement.setInfoMessage("MessageBox ext-mb-text");
        String msg;
        if (waitSeconds == 0) {
            msg = mbTextElement.getHtmlText(true);
        } else {
            msg = mbTextElement.waitTextToRender(waitSeconds);
        }
        return msg;
    }

    public boolean close() {
        return messageBoxWindow.close();
    }

    public static String press(String buttonText) {
        Button button;
        if(BUTTON_OK.equals(buttonText)){
            button = okButton;
        } else if(BUTTON_CANCEL.equals(buttonText)){
            button = cancelButton;
        } else if(BUTTON_YES.equals(buttonText)){
            button = yesButton;
        } else if(BUTTON_NO.equals(buttonText)){
            button = noButton;
        } else {
            button = new Button(messageBoxWindow, buttonText);
        }
        return press(button);
    }

    private static String press(final Button button) {
        if (WebDriverConfig.isIE() && !messageBoxWindow.isVisible()) {
            return null;
        }
        String msg = getMessage();
        if (msg != null) {
            logger.info("Click on button " + button.getText() + " in the window with message: " + msg);
            button.click();
        } else {
            logger.warn("There is no Message or Dialog");
        }
        return msg;
    }

    public static String pressOK() {
        return press(okButton);
    }

    public static String pressYes() {
        return press(yesButton);
    }

    public static String pressNo() {
        return press(noButton);
    }
}
