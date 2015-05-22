package com.sdl.selenium.extjs3.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Info/Error/Warning Message Box from ExtJs
 */
public class MessageBox {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBox.class);

    private static MessageBoxWindow messageBoxWindow = new MessageBoxWindow();

    protected MessageBox(){}

    /**
     * instant get MessageBox text if is present
     *
     * @return MessageBox text if is present
     */
    public static String getMessage() {
        return messageBoxWindow.getMessage();
    }

    /**
     * Wait MessageBox to appear and get text.
     * Use this method when you realy expect some message box but don't know exact time when it appear
     *
     * @param waitSeconds seconds
     * @return MessageBox text if is present
     */
    public static String getMessage(int waitSeconds) {
        return messageBoxWindow.getMessage(waitSeconds);
    }

    public boolean close() {
        return messageBoxWindow.close();
    }

    public static String press(String buttonText) {
        return messageBoxWindow.press(buttonText);
    }

    public static String pressOK() {
        return messageBoxWindow.pressOK();
    }

    public static String pressYes() {
        return messageBoxWindow.pressYes();
    }

    public static String pressNo() {
        return messageBoxWindow.pressNo();
    }
}
