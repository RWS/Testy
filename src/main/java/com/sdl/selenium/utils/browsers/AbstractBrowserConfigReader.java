package com.sdl.selenium.utils.browsers;

import com.sdl.selenium.web.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractBrowserConfigReader extends PropertiesReader {

    public AbstractBrowserConfigReader(String defaults, String resourcePath) {
        super(defaults, resourcePath);
    }

    public abstract WebDriver createDriver() throws IOException;

    public abstract boolean isSilentDownload();

    public String getDownloadPath() {
        File file = new File(getDownloadDir());
        return file.getAbsolutePath();
    }

    public String getDownloadDir() {
        String downloadDir = getProperty("browser.download.dir");
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MMM-dd");
        String date = sdf.format(new Date());

        downloadDir = downloadDir.replaceAll("\\{pid\\}", pid);
        downloadDir = downloadDir.replaceAll("\\{date\\}", date);
        return downloadDir;
    }
}
