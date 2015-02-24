package com.sdl.selenium.web.utils.browsers;

import com.sdl.selenium.web.utils.PropertiesReader;

public class FirefoxConfigReader extends PropertiesReader {

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
}
