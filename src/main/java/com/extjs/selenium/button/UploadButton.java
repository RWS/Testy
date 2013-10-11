package com.extjs.selenium.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.SelectFiles;
import org.apache.log4j.Logger;

public class UploadButton extends SelectFiles {

    private static final Logger logger = Logger.getLogger(UploadButton.class);

    public UploadButton() {
        setClassName("UploadButton");
        defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE);
    }

    public UploadButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public UploadButton(WebLocator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

    public boolean upload(String [] filePath){
        return upload(this, filePath);
    }
}
