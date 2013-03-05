package com.extjs.selenium.window;

import com.extjs.selenium.ExtJsComponent;
import com.extjs.selenium.button.Button;
import org.apache.log4j.Logger;

/**
 * The Info/Error/Warning Message Box from ExtJs
 */
public class MessageBox extends ExtJsComponent {
    private static final Logger logger = Logger.getLogger(MessageBox.class);

    public static String BUTTON_OK = "OK";
    public static String BUTTON_CANCEL = "Cancel";
    public static String BUTTON_YES = "Yes";
    public static String BUTTON_NO = "No";

    private static Window messageBoxWindow = new Window(true).setCls("x-window-dlg").setInfoMessage("MessageBox");

    // === statics ===

    /**
     * instant get MessageBox text if is present
     * @return
     */
    public static String getMessage() {
        return getMessage(0);
    }


    public static String getMessage(boolean useCssSelectors) {
        return getMessage(0, useCssSelectors);
    }
    /**
     * Wait MessageBox to appear and get text.
     * Use this method when you realy expect some message box but don't know exact time when it appear
     * @param waitSeconds
     * @return
     */
    public static String getMessage(int waitSeconds, boolean useCssSelectors) {

        ExtJsComponent mbTextElement = new ExtJsComponent("ext-mb-text", messageBoxWindow);
        mbTextElement.setInfoMessage("MessageBox ext-mb-text");
        String msg;

        if(waitSeconds == 0){
            msg = mbTextElement.getHtmlText(useCssSelectors);
        } else {
            msg = mbTextElement.waitTextToRender(waitSeconds);
        }
        return msg;
    }

    /**
     * xPath only
     * @param waitSeconds
     * @return
     */
    public static String getMessage(int waitSeconds) {
        return getMessage(waitSeconds, false);
    }

    public static String getStaticPath() {
        ExtJsComponent  component = new ExtJsComponent("ext-mb-text", messageBoxWindow);
        component.setInfoMessage("MessageBox ext-mb-text");
        return component.getPath();
    }

    public boolean close() {
        return messageBoxWindow.close();
    }

    public static String press(String buttonText) {
        return press(buttonText, false);
    }

    public static String press(String buttonText, boolean useCssSelectors) {
        if(isIE() && !messageBoxWindow.isVisible()){
            return null;
        }

        String msg = getMessage(useCssSelectors);
        Button button = new Button(messageBoxWindow, buttonText);

        if (msg != null) {
            logger.info("Click on button " + buttonText + " in the window with message: " + msg);
            button.click(useCssSelectors);
        } else {
            logger.warn("There is no Message or Dialog");
        }
        return msg;
    }

    public static String pressOK() {
        return pressOK(false);
    }
    public static String pressOK(boolean useCssSelectors) {
        return press(BUTTON_OK, useCssSelectors);
    }
    public static String pressYes() {
        return pressYes(false);
    }

    public static String pressYes(boolean useCssSelectors) {
        return press(BUTTON_YES, useCssSelectors);
    }

    public static String pressNo(boolean useCssSelectors) {
        return press(BUTTON_NO, useCssSelectors);
    }

    public static String pressNo() {
        return pressNo(false);
    }
}
