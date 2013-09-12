package com.extjs.selenium.window;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.button.Button;
import com.sdl.selenium.web.WebLocator;
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

    private static Window messageBoxWindow = new Window(true).setCls("x-window-dlg").setInfoMessage("MessageBox");

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
        mbTextElement.setRenderMillis(0);
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
        if (WebLocator.isIE() && !messageBoxWindow.isVisible()) {
            return null;
        }
        String msg = getMessage();
        Button button = new Button(messageBoxWindow, buttonText);
        if (msg != null) {
            logger.info("Click on button " + buttonText + " in the window with message: " + msg);
            button.click();
        } else {
            logger.warn("There is no Message or Dialog");
        }
        return msg;
    }

    public static String pressOK() {
        return press(BUTTON_OK);
    }

    public static String pressYes() {
        return press(BUTTON_YES);
    }

    public static String pressNo() {
        return press(BUTTON_NO);
    }
}
