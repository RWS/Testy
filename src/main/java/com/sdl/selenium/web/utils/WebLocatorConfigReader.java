package com.sdl.selenium.web.utils;

public class WebLocatorConfigReader extends PropertiesReader {

    private static final String DEFAULT_CONFIG = ""+
            "\n weblocator.log.containers=true" +
            "\n weblocator.log.useClassName=false" +
            "\n weblocator.log.logXPathEnabled=true" +
            "\n weblocator.highlight=false" +
            "\n weblocator.defaults.renderMillis=3000" +
            "\n weblocator.defaults.searchType=contains" +
            "\n weblocator.defaults.labelPosition=//following-sibling::*//";

    public WebLocatorConfigReader() {
        super(null, DEFAULT_CONFIG);
    }

    public WebLocatorConfigReader(String resourcePath) {
        super(resourcePath, DEFAULT_CONFIG);
    }
}
