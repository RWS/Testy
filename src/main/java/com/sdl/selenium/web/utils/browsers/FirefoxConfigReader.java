package com.sdl.selenium.web.utils.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FirefoxConfigReader extends AbstractBrowserConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirefoxConfigReader.class);

    private static final String DEFAULT_CONFIG = "" +
            "\n browser=firefox" +
            "\n browser.version=" +
            "\n browser.profile.name=default" +
            "\n browser.profile.path=" +
            "\n browser.binary.path=" +
            "\n browser.driver.path=" +
            "\n browser.download.dir=src\\\\test\\\\resources\\\\download\\\\" +
            "\n profile.preference.dom.max_script_run_time=500" +
            "\n profile.preference.browser.download.folderList=2" +
            "\n profile.preference.browser.download.manager.showWhenStarting=false" +
            "\n profile.preference.browser.download.manager.closeWhenDone=true" +
            "\n profile.preference.browser.download.manager.showAlertOnComplete=false" +
            "\n profile.preference.browser.helperApps.neverAsk.openFile=text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document" +
            "\n profile.preference.browser.helperApps.neverAsk.saveToDisk=text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document" +
            "\n profile.preference.browser.helperApps.alwaysAsk.force=false" +
            "\n profile.preference.browser.download.panel.shown=false" +
            "\n profile.preference.security.warn_entering_secure=false" +
            "\n profile.preference.security.warn_entering_secure.show_once=false" +
            "\n profile.preference.security.warn_entering_weak=false" +
            "\n profile.preference.security.warn_entering_weak.show_once=false" +
            "\n profile.preference.security.warn_leaving_secure=false" +
            "\n profile.preference.security.warn_leaving_secure.show_once=false" +
            "\n profile.preference.security.warn_submit_insecure=false" +
            "\n profile.preference.security.warn_submit_insecure.show_once=false" +
            "\n profile.preference.security.warn_viewing_mixed=false" +
            "\n profile.preference.security.warn_viewing_mixed.show_once=false";

    public FirefoxConfigReader() {
        this(null);
    }

    public FirefoxConfigReader(String resourcePath) {
        super(resourcePath, DEFAULT_CONFIG);
    }

    @Override
    public WebDriver createDriver() throws IOException {
        WebDriver driver;
        String profileName = getProperty("browser.profile.name");
        FirefoxProfile myProfile;
        if (!"".equals(profileName) && profileName != null) {
            ProfilesIni allProfiles = new ProfilesIni();
            myProfile = allProfiles.getProfile(profileName);
        } else {
            myProfile = new FirefoxProfile();
        }
        if (myProfile != null) {
            LOGGER.info("profile not null");
            setProfilePreferences(myProfile);

            File file = new File(getProperty("browser.download.dir"));
            String downloadDir = file.getCanonicalPath();
            if (!"".equals(downloadDir)) {
                myProfile.setPreference("browser.download.dir", downloadDir);
            }
            driver = new FirefoxDriver(myProfile);
        } else {
            String profilePath = getProperty("browser.profile.path");
            if (profilePath != null && !profilePath.equals("")) {
                FirefoxProfile firefoxProfile = new FirefoxProfile(new File(profilePath));
                driver = new FirefoxDriver(firefoxProfile);
            } else {
                DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
                String version = getProperty("browser.version");
                if (version != null) {
                    firefoxCapabilities.setCapability("version", version);
                }
                String homeDir = getProperty("browser.binary.path");
                if (homeDir != null) {
                    firefoxCapabilities.setCapability("firefox_binary", homeDir + "firefox.exe");
                }
                driver = new FirefoxDriver(firefoxCapabilities);
            }
        }
        return driver;
    }

    @Override
    public boolean isSilentDownload() {
        return !"".equals(getProperty("browser.download.dir")) &&
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
    public String getDownloadPath() {
        File file = new File(getProperty("browser.download.dir"));
        return file.getAbsolutePath();
    }

    private void setProfilePreferences(FirefoxProfile myProfile) {
        for (Map.Entry<Object, Object> entry : entrySet()) {
            String key = (String) entry.getKey();
            if (key.startsWith("profile.preference.")) {
                String preferenceKey = key.substring(19);
                String value = (String) entry.getValue();

                if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                    myProfile.setPreference(preferenceKey, Boolean.valueOf(value));
                } else {
                    try {
                        int intValue = Integer.parseInt(value);
                        myProfile.setPreference(preferenceKey, intValue);
                    } catch (NumberFormatException e) {
                        myProfile.setPreference(preferenceKey, value);
                    }
                }
            }
        }
    }
}
