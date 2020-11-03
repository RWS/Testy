package com.sdl.selenium.utils.browsers;

import com.sdl.selenium.web.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractBrowserConfigReader extends PropertiesReader {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AbstractBrowserConfigReader.class);

    public AbstractBrowserConfigReader(String defaults, String resourcePath) {
        super(defaults, resourcePath);
    }

    public abstract WebDriver createDriver() throws IOException;

    public abstract WebDriver createDriver(URL remoteUrl, DesiredCapabilities capabilities) throws IOException;

    public abstract boolean isSilentDownload();

    public abstract DriverService getDriveService();

    public String getDownloadPath() {
        File file = new File(getDownloadDir());
        return file.getAbsolutePath();
    }

    public String getDownloadDir() {
        String downloadDir = getProperty("browser.download.dir");
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        log.info("pid:{}", pid);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MMM-dd");
        String date = sdf.format(new Date());

        downloadDir = downloadDir.replaceAll("\\{pid\\}", pid);
        downloadDir = downloadDir.replaceAll("\\{date\\}", date);
        return downloadDir;
    }

    public boolean isRemoteDriver() {
        String remoteProperty = System.getProperty("remoteDriver");
        return Boolean.parseBoolean(remoteProperty);
    }

    public boolean isRecordNetworkTraffic() {
        String recordNetworkTraffic = System.getProperty("browser.recordNetworkTraffic");
        return Boolean.parseBoolean(recordNetworkTraffic);
    }
}