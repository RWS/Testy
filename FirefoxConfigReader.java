package com.sdl.selenium.utils.browsers;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class FirefoxConfigReader extends AbstractBrowserConfigReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirefoxConfigReader.class);

    private static final String DEFAULT_CONFIG =
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
            "\n profile.preference.security.warn_viewing_mixed.show_once=false" +
            "\n profile.preference.dom.disable_beforeunload = true";

    public FirefoxConfigReader() {
        this(null);
    }

    public FirefoxConfigReader(String resourcePath) {
        super(DEFAULT_CONFIG, resourcePath);
    }

    @Override
    public WebDriver createDriver() throws IOException {
        DesiredCapabilities firefoxCapabilities = getDesiredCapabilities();
        return new FirefoxDriver(firefoxCapabilities);
    }

    @Override
    public WebDriver createDriver(URL remoteUrl) throws IOException {
        DesiredCapabilities firefoxCapabilities = getDesiredCapabilities();
        if (isRemoteDriver()) {
            RemoteWebDriver driver = new RemoteWebDriver(remoteUrl, firefoxCapabilities);
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        } else {
            return new FirefoxDriver(firefoxCapabilities);
        }
    }

    private DesiredCapabilities getDesiredCapabilities() throws IOException {
        DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
        FirefoxProfile profile = getProfile();
        if (profile == null) {
            String version = getProperty("browser.version");
            if (version != null) {
                firefoxCapabilities.setCapability("version", version);
            }
            String homeDir = getProperty("browser.binary.path");
            if (homeDir != null) {
                firefoxCapabilities.setCapability("firefox_binary", homeDir + "firefox.exe");
            }
        } else {
            firefoxCapabilities.setCapability(FirefoxDriver.PROFILE, profile);
        }
        String driverPath = getProperty("webdriver.gecko.driver");
        if (!"".equals(driverPath)) {
            System.setProperty("webdriver.gecko.driver", driverPath);
        }
        return firefoxCapabilities;
    }

    private FirefoxProfile getProfile() throws IOException {
        String profileName = Platform.getCurrent().equals(Platform.LINUX) ? "" : getProperty("browser.profile.name"); //TODO find solution
        FirefoxProfile profile;
        if (profileName == null || "".equals(profileName)) {
            profile = new FirefoxProfile();
        } else {
            ProfilesIni allProfiles = new ProfilesIni();
            profile = allProfiles.getProfile(profileName);
        }
        setProfilePreferences(profile);
        File file = new File(getDownloadPath());
        String downloadDir = file.getCanonicalPath();
        if ("".equals(downloadDir)) {
            String profilePath = getProperty("browser.profile.path");
            if (profilePath != null && !"".equals(profilePath)) {
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
        LOGGER.info("The properties was load with success: {}", toString());
    }
}
