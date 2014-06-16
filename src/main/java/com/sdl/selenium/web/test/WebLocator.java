package com.sdl.selenium.web.test;

import org.apache.log4j.Logger;

public class WebLocator {
    private static final Logger LOGGER = Logger.getLogger(WebLocator.class);

    private PathBuilder pathBuilder;

    public WebLocator(PathBuilder pathBuilder) {
        this.pathBuilder = pathBuilder;
    }

    public static void main(String[] args) {
//        PathBuilder xxx = new PathBuilder().setClasses("cls").setText("TEXT");
//        WebLocator locator = new WebLocator(xxx);

        WebLocator locator = new WebLocator(By.id("ID"), By.text("clsg"));
        //WebLocator locator3 = new WebLocator(By.id("fdfd").name("dfdf").text("").build());

        locator.getPathBuilder().getText();
    }

    public PathBuilder getPathBuilder() {
        return pathBuilder;
    }

    public void setPathBuilder(PathBuilder pathBuilder) {
        this.pathBuilder = pathBuilder;
    }

    public WebLocator(By... bys) {
//        setBys(By.cls("test"));
//        pathBuilder.setBys(bys);

        pathBuilder = new PathBuilder(bys);
        /*pathBuilder = new PathBuilder(bys) {
            public String getItemPath(boolean disabled) {
                String selector = getBasePathSelector();
                return "//" + getTag() + (selector != null && "".equals(selector) ? "" : "[" + selector + "]");
            }
        };*/
    }

    protected WebLocator(WebLocator container, By... by) {
        this(by);
        pathBuilder.setContainer(container);
    }

}
