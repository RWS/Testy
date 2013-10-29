package com.extjs.selenium.window;

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

    public MessageBoxWindow() {
        super(true);
        setClassName("MessageBoxWindow");
        setInfoMessage("MessageBoxWindow");
        setClasses("x-window-dlg");
    }

    public MessageBoxWindow(String title) {
        this();
        setTitle(title);
    }
}
