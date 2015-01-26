package com.sdl.selenium.web.utils.browsers;

import com.sdl.selenium.web.utils.PropertiesReader;

public class FirefoxConfigReader extends PropertiesReader {

    private static final String DEFAULT_CONFIG = ""+
            "\n browser=firefox" +
            "\n browser.version=" +
            "\n browser.profile.name=default" +
            "\n browser.profile.path=" +
            "\n browser.binary.path=" +
            "\n browser.driver.path=" +
            "\n dom.max_script_run_time=500" +
            "\n browser.download.folderList=2" +
            "\n browser.download.manager.showWhenStarting=false" +
            "\n browser.download.manager.closeWhenDone=true" +
            "\n browser.download.manager.showAlertOnComplete=false" +
            "\n browser.download.dir=src\\\\test\\\\resources\\\\download\\\\" +
            "\n browser.helperApps.neverAsk.openFile=text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document" +
            "\n browser.helperApps.neverAsk.saveToDisk=text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document" +
            "\n browser.helperApps.alwaysAsk.force=false" +
            "\n browser.download.panel.shown=false" +
            "\n security.warn_entering_secure=false" +
            "\n security.warn_entering_secure.show_once=false" +
            "\n security.warn_entering_weak=false" +
            "\n security.warn_entering_weak.show_once=false" +
            "\n security.warn_leaving_secure=false" +
            "\n security.warn_leaving_secure.show_once=false" +
            "\n security.warn_submit_insecure=false" +
            "\n security.warn_submit_insecure.show_once=false" +
            "\n security.warn_viewing_mixed=false" +
            "\n security.warn_viewing_mixed.show_once=false";

    public FirefoxConfigReader() {
        super(null, DEFAULT_CONFIG);
    }
    
    public FirefoxConfigReader(String resourcePath) {
        super(resourcePath, DEFAULT_CONFIG);
    }
}
