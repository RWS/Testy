package com.sdl.selenium.utils.browsers;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

@Slf4j
public class FirefoxConfigReader extends AbstractBrowserConfigReader {

    private static final String DEFAULT_CONFIG = String.join("\n", "##Firefox defaults \n",
            "browser=firefox",
            "browser.version=",
            "browser.profile.name=default",
            "browser.profile.path=",
            "browser.binary.path=",
            "browser.driver.path=src\\\\test\\\\resources\\\\drivers\\\\geckodriver.exe",
            "browser.download.dir=src\\\\test\\\\resources\\\\download\\\\",
            "profile.preference.dom.max_script_run_time=500",
            "profile.preference.browser.download.folderList=2",
            "profile.preference.browser.download.manager.showWhenStarting=false",
            "profile.preference.browser.download.manager.closeWhenDone=true",
            "profile.preference.browser.download.manager.showAlertOnComplete=false",
            "profile.preference.browser.helperApps.neverAsk.openFile=text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "profile.preference.browser.helperApps.neverAsk.saveToDisk=text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "profile.preference.browser.helperApps.alwaysAsk.force=false",
            "profile.preference.browser.download.panel.shown=false",
            "profile.preference.security.warn_entering_secure=false",
            "profile.preference.security.warn_entering_secure.show_once=false",
            "profile.preference.security.warn_entering_weak=false",
            "profile.preference.security.warn_entering_weak.show_once=false",
            "profile.preference.security.warn_leaving_secure=false",
            "profile.preference.security.warn_leaving_secure.show_once=false",
            "profile.preference.security.warn_submit_insecure=false",
            "profile.preference.security.warn_submit_insecure.show_once=false",
            "profile.preference.security.warn_viewing_mixed=false",
            "profile.preference.security.warn_viewing_mixed.show_once=false",
            "profile.preference.dom.disable_beforeunload = true");

    public FirefoxConfigReader() {
        this(null);
    }

    public FirefoxConfigReader(String resourcePath) {
        super(DEFAULT_CONFIG, resourcePath);
    }

    @Override
    public WebDriver createDriver() throws IOException {
        FirefoxOptions options = getFirefoxOptions();
        return new FirefoxDriver(options);
    }

    @Override
    public WebDriver createDriver(URL remoteUrl) throws IOException {
        FirefoxOptions options = getFirefoxOptions();
        if (isRemoteDriver()) {
            RemoteWebDriver driver = new RemoteWebDriver(remoteUrl, options);
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        } else {
            return new FirefoxDriver(options);
        }
    }

    private FirefoxOptions getFirefoxOptions() throws IOException {
        String driverPath = getProperty("browser.driver.path");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.gecko.driver", driverPath);
        }
        FirefoxOptions options = new FirefoxOptions();
        options.setLogLevel(FirefoxDriverLogLevel.FATAL);
        FirefoxProfile profile = getProfile();
        if (profile == null) {
            String version = getProperty("browser.version");
            if (version != null) {
                options.setCapability("version", version);
            }
        } else {
            options.setProfile(profile);
        }
        return options;
    }

    private FirefoxProfile getProfile() throws IOException {
        FirefoxProfile profile = new FirefoxProfile();
        setProfilePreferences(profile);
        File file = new File(getDownloadPath());
        String downloadDir = file.getCanonicalPath();
        if ("".equals(downloadDir) && !"silent".equals(downloadDir)) {
            String profilePath = getProperty("browser.profile.path");
            if (profilePath != null && !"".equals(profilePath) && !"silent".equals(downloadDir)) {
                profile = new FirefoxProfile(new File(profilePath));
                setProfilePreferences(profile);
            }
        } else {
            profile.setPreference("browser.download.dir", downloadDir);
        }
        return profile;
    }

    @Override
    public boolean isSilentDownload() {
        return "silent".equals(getProperty("browser.download.dir")) || !"".equals(getProperty("browser.download.dir")) &&
                !"".equals(getProperty("profile.preference.browser.helperApps.neverAsk.openFile")) &&
                !"".equals(getProperty("profile.preference.browser.helperApps.neverAsk.saveToDisk")) &&
                !(Boolean.valueOf(getProperty("profile.preference.browser.helperApps.alwaysAsk.force"))) &&
                !(Boolean.valueOf(getProperty("profile.preference.browser.download.panel.shown"))) &&
                !(Boolean.valueOf(getProperty("profile.preference.browser.download.manager.showAlertOnComplete"))) &&
                (Boolean.valueOf(getProperty("profile.preference.browser.download.manager.closeWhenDone"))) &&
                !(Boolean.valueOf(getProperty("profile.preference.browser.download.manager.showWhenStarting"))) &&
                (Integer.valueOf(getProperty("profile.preference.browser.download.folderList")) == 2);
    }

    @Override
    public DriverService getDriveService() {
        return null;
    }

    private void setProfilePreferences(FirefoxProfile profile) {
        for (Map.Entry<Object, Object> entry : entrySet()) {
            String key = (String) entry.getKey();
            if (key.startsWith("profile.preference.")) {
                String preferenceKey = key.substring(19);
                String value = (String) entry.getValue();

                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    profile.setPreference(preferenceKey, Boolean.valueOf(value));
                } else {
                    try {
                        int intValue = Integer.parseInt(value);
                        profile.setPreference(preferenceKey, intValue);
                    } catch (NumberFormatException e) {
                        profile.setPreference(preferenceKey, value);
                    }
                }
            }
        }
        log.info("The properties was load with success: {}", toString());
    }
}
