package com.sdl.selenium.extjs6.panel;

import com.sdl.selenium.extjs6.button.Button;
import com.sdl.selenium.extjs6.form.TextField;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pagination extends ToolBar {
    private static final Logger LOGGER = LoggerFactory.getLogger(Pagination.class);

    public Pagination() {
        withClassName("Pagination");
    }

    public Pagination(WebLocator container) {
        this();
        withContainer(container);
    }

    private WebLocator currentPageContainer = new WebLocator("x-tbar-page-number");
    private TextField currentPage = new TextField(currentPageContainer);


    private Button gotoFirstPageBtn = getButton("x-tbar-page-first");

    public boolean goToFirstPage() {
        return gotoFirstPageBtn.click();
    }

    public Button getGotoFirstPageBtn() {
        return gotoFirstPageBtn;
    }

    private Button gotoPreviousPageBtn = getButton("x-tbar-page-prev");

    public boolean goToPreviousPage() {
        return gotoPreviousPageBtn.click();
    }

    public Button getGotoPreviousPageBtn() {
        return gotoPreviousPageBtn;
    }

    private Button gotoNextPageBtn = getButton("x-tbar-page-next");

    public boolean goToNextPage() {
        return gotoNextPageBtn.click();
    }

    public Button getGotoNextPageBtn() {
        return gotoNextPageBtn;
    }

    private Button gotoLastPageBtn = getButton("x-tbar-page-last");

    public boolean goToLastPage() {
        return gotoLastPageBtn.click();
    }

    public Button getGotoLastPageBtn() {
        return gotoLastPageBtn;
    }

    public TextField getCurrentPage() {
        return currentPage;
    }

    public boolean refresh() {
        return getButton("x-tbar-loading").click();
    }

    public final String itemTotalsLabel = "Displaying items ";
    private WebLocator itemTotals = new WebLocator(this).withBaseCls("x-toolbar-text").withText(itemTotalsLabel, SearchType.STARTS_WITH);

    public WebLocator getItemTotals() {
        return itemTotals;
    }

    private Button getButton(String cls) {
        WebLocator el = new WebLocator().withTag("span").withClasses(cls);
        return new Button(this).withChildNodes(el);
    }

//    public static void main(String[] args) {
//        Pagination pagination = new Pagination();
//        LOGGER.info(pagination.getCurrentPage().getXPath());
//
//    }
}
