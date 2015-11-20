package com.sdl.selenium.utils.browsers;

import com.sdl.selenium.web.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.lang.management.ManagementFactory;

public abstract class AbstractBrowserConfigReader extends PropertiesReader {

    public AbstractBrowserConfigReader(String defaults, String resourcePath) {
        super(defaults, resourcePath);
    }

    public abstract WebDriver createDriver() throws IOException;

    public abstract boolean isSilentDownload();

    public abstract String getDownloadPath();

    public String applyPid() {
        String tpl = getProperty("browser.download.dir");
        String[] strings = tpl.split("\\{");
        String pid = "";
        if (strings.length >= 2) {
            tpl = strings[0];
            pid = strings[1].substring(0, strings[1].indexOf("}"));
        }
        String pidValue = "";
        if ("pid".equals(pid)) {
            pidValue = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        }
        return tpl + pidValue;
    }
}
