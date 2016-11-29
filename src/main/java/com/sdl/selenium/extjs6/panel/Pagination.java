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
    private WebLocator itemTotals = new WebLocator(this).withBaseCls("x-toolbar-text").withText("Displaying items", SearchType.STARTS_WITH);

    private Button firstPageBtn = getButton("x-tbar-page-first");

    public Button getFirstPageBtn() {
        return firstPageBtn;
    }

    private Button previousPageBtn = getButton("x-tbar-page-prev");

    public Button getPreviousPageBtn() {
        return previousPageBtn;
    }

    private Button nextPageBtn = getButton("x-tbar-page-next");

    public Button getNextPageBtn() {
        return nextPageBtn;
    }

    private Button lastPageBtn = getButton("x-tbar-page-last");

    public Button getLastPageBtn() {
        return lastPageBtn;
    }

    public TextField getCurrentPage() {
        return currentPage;
    }

    public WebLocator getItemTotals() {
        return itemTotals;
    }

    private Button getButton(String cls) {
        WebLocator el = new WebLocator().withTag("span").withClasses(cls);
        return new Button(this).withChildNodes(el);
    }

    public boolean goToFirstPage() {
        return firstPageBtn.click();
    }

    public boolean goToPreviousPage() {
        return previousPageBtn.click();
    }

    public boolean goToNextPage() {
        return nextPageBtn.click();
    }

    public boolean goToLastPage() {
        return lastPageBtn.click();
    }

    public boolean refresh() {
        return getButton("x-tbar-loading").click();
    }
}
