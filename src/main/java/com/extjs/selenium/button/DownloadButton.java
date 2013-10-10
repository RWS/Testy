package com.extjs.selenium.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.SelectFiles;
import org.apache.log4j.Logger;

public class DownloadButton extends SelectFiles {

    private static final Logger logger = Logger.getLogger(DownloadButton.class);

    public DownloadButton() {
        setClassName("DownloadButton");
        defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE);
        setSearchTextType(SearchType.EQUALS);
    }

    public DownloadButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public DownloadButton(WebLocator container, String text) {
        this(container);
        setText(text);
    }

    /**
     * Wait for the element to be activated when there is deactivation mask on top of it
     *
     * @param seconds
     */
    public boolean waitToActivate(int seconds) {
        return isContainer("LiveGridPanel") ? true : super.waitToActivate(seconds);
    }

    private boolean isContainer(String className){
        WebLocator webLocator = getContainer();
        while(webLocator!= null){
            logger.debug(webLocator.getClassName());
            if(className.equals(webLocator.getClassName())){
                return true;
            }
            webLocator = webLocator.getContainer();
        }
        return false;
    }
}
