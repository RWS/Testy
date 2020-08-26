package com.sdl.selenium.utils.config;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.utils.PropertiesReader;

import java.util.Arrays;

public class WebLocatorConfigReader extends PropertiesReader {

    private static final String EXT_DEFAULT_CONFIG = String.join("\nExt.", "##Ext defaults \n",
            "checkbox.tag=*",
            "checkbox.tag.6.0.0=*",
            "checkbox.tag.6.6.0=input",
            "checkbox.tag.6.7.0=input",

            "checkbox.type.6.0.0=",
            "checkbox.type.6.6.0=checkbox",
            "checkbox.type.6.7.0=checkbox",

            "checkbox.baseCls=x-form-checkbox",
            "checkbox.baseCls.6.7.0=",

            "checkbox.boxLabel=/../",
            "checkbox.boxLabel.6.7.0=/..//",

            "radio.boxLabel=/../",
            "radio.boxLabel.6.7.0=/..//"
    );

    private static final String DEFAULT_CONFIG = String.join("\n", "##defaults \n",
            "weblocator.log.containers=true",
            "weblocator.log.useClassName=false",
            "weblocator.log.xPath=true",
            "weblocator.log.retryException=true",
            "weblocator.log.suggestions=false",
            "weblocator.highlight=false",
            "weblocator.generateCssSelector=false",
            "weblocator.defaults.renderMillis=3000",
            "#accepted values for searchType: " + Arrays.asList(SearchType.values()),
            "weblocator.defaults.searchType=CONTAINS",
            "weblocator.defaults.labelPosition=//following-sibling::*//",
            "weblocator.min.chars.toType=-1",
            "driver.autoClose=true",
            "driver.implicitlyWait=100",
            "extjs.version=6.7.0",
            "upload.exe.path=src\\\\test\\\\resources\\\\upload\\\\upload.exe",
            "weblocator.log.params.exclude=Password,Current Password,New Password");

    public WebLocatorConfigReader() {
        this(null);
    }

    public WebLocatorConfigReader(String resourcePath) {
        super(DEFAULT_CONFIG + EXT_DEFAULT_CONFIG, resourcePath);
    }
}
