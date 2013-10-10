package com.sdl.selenium.web;

import com.sdl.selenium.web.form.ITextField;

interface IButton {
    void click();
}

class WebLocatorImp extends WebLocatorAbstractBuilder  {

    public WebLocatorImp() {
    }

    public void click() {
        System.out.println("Click " + getClass());
    }

    public void type() {
        System.out.println("Type " + getClass());
    }
}

class TextField1 extends WebLocatorAbstractBuilder implements ITextField/*, ITextFieldPath*/ {

    private WebLocatorImp webLocatorImp = null;

    public TextField1(){
        webLocatorImp = new WebLocatorImp();
    }

    public void type() {
        webLocatorImp.type();
    }

    @Override
    public boolean setValue(String value) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*public String getCls() {
        return webLocatorImp.getCls();
    }

    public <T extends TextField1> T setCls(String cls) {
        webLocatorImp.setCls(cls);
        return (T) this;
    }*/
}

class TextFieldSimple extends TextField1 {

    public TextFieldSimple(){
        setClasses("TextFieldSimple");
    }

    @Override
    public boolean setValue(String value) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

class Button1 implements IButton {

    public Button1() {

    }

    @Override
    public void click() {
        new WebLocatorImp().click();
    }
}

class JavaPatern {

    public static void main(String[] args) {

        final TextField1 textField = new TextField1().setClasses("sasa");
        final TextFieldSimple textFieldSimple = new TextFieldSimple().setText("ds");
        final Button1 button = new Button1();
        final WebLocatorImp el = new WebLocatorImp();

        textField.type(); // loading unnecessary
        System.out.println(textField.getCls()); // loading unnecessary
        System.out.println(textFieldSimple.getCls()); // loading unnecessary
        System.out.println(textFieldSimple.getText()); // loading unnecessary
        button.click(); // loading necessary
        el.click(); // loading necessary
        el.type(); // loading necessary


    }
}