package com.extjs.selenium;

import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.FileUtils;
import com.sdl.selenium.web.utils.Utils;
import com.sdl.selenium.web.utils.browsers.ChromeConfigReader;
import com.sdl.selenium.web.utils.browsers.FirefoxConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;


public class UtilsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilsTest.class);

    @DataProvider
    public static Object[][] validateFileName() {

        return new Object[][]{
                {"My: Documents", "My_ Documents"},
                {"My\\ Documents", "My_ Documents"},
                {"My/ Documents", "My_ Documents"},
                {"My* Documents", "My_ Documents"},
                {"My? Documents", "My_ Documents"},
                {"My< Documents", "My_ Documents"},
                {"My> Documents", "My_ Documents"},
                {"My| Documents", "My_ Documents"},
                {"My:\\/*?<>| Documents", "My________ Documents"},
                {"My Documents", "My Documents"}
        };
    }

    @DataProvider
    public static Object[][] validateTextWithQuotes() {

        return new Object[][]{
                {"Simple Text", "'Simple Text'"},
                {"Don't do it", "\"Don't do it\""},
                {"It was \"good\" ok!", "'It was \"good\" ok!'"},
                {"Don't do it \"now\" ok!", "concat(\"Don't do it \", '\"', \"now\", '\"', \" ok!\")"},
        };
    }

    //TODO Replace data provider with a more appropriate one

//    @Test( dataProviderClass = TrainerDataProvider.class, dataProvider = "translateDocumentFiles", alwaysRun = true)
//    public void split(String project, String trainingName, int column,  String pathFile, String downloadPathFile, String unzippedPathFile){
//        String newfile = Utils.obtainFileName(pathFile, "-trained.rum");
//        Assert.assertEquals("headlines-trained.rum.txt", newfile);
//    }
//
//    @Test( dataProviderClass = TrainerDataProvider.class, dataProvider = "translateDocumentFiles", alwaysRun = true)
//    public void getFileName(String project, String trainingName, int column,  String pathFile, String downloadPathFile, String unzippedPathFile){
//        String file = Utils.getFileFromPath(pathFile);
//        Assert.assertEquals("headlines.txt",file);
//    }

    @Test(dataProvider = "validateFileName")
    public void convertToValidFileName(String originalFileName, String validFileName) {
        assertEquals(FileUtils.getValidFileName(originalFileName), validFileName);
    }

    @Test(dataProvider = "validateTextWithQuotes")
    public void textWithQuotes(String original, String expected) {
        assertEquals(Utils.getEscapeQuotesText(original), expected);
    }

//    @Test
    public void propertiesFireFox() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("browser", "firefox");
        map.put("browser.version", "");
        map.put("browser.profile.name", "default");
        map.put("browser.profile.path", "");
        map.put("browser.binary.path", "");
        map.put("browser.driver.path", "");
        map.put("profile.preference.dom.max_script_run_time", "500");
        map.put("profile.preference.browser.download.folderList", "2");
        map.put("profile.preference.browser.download.manager.showWhenStarting", "false");
        map.put("profile.preference.browser.download.manager.closeWhenDone", "true");
        map.put("profile.preference.browser.download.manager.showAlertOnComplete", "false");
        map.put("browser.download.dir", "src\\test\\resources\\download\\");
        map.put("profile.preference.browser.helperApps.neverAsk.openFile", "text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        map.put("profile.preference.browser.helperApps.neverAsk.saveToDisk", "text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        map.put("profile.preference.browser.helperApps.alwaysAsk.force", "false");
        map.put("profile.preference.browser.download.panel.shown", "false");
        map.put("profile.preference.security.warn_entering_secure", "false");
        map.put("profile.preference.security.warn_entering_secure.show_once", "false");
        map.put("profile.preference.security.warn_entering_weak", "false");
        map.put("profile.preference.security.warn_entering_weak.show_once", "false");
        map.put("profile.preference.security.warn_leaving_secure", "false");
        map.put("profile.preference.security.warn_leaving_secure.show_once", "false");
        map.put("profile.preference.security.warn_submit_insecure", "false");
        map.put("profile.preference.security.warn_submit_insecure.show_once", "false");
        map.put("profile.preference.security.warn_viewing_mixed", "false");
        map.put("profile.preference.security.warn_viewing_mixed.show_once", "false");
        map.put("profile.preference.webdriver.load.strategy", "unstable");

        FirefoxConfigReader firefoxConfigReader = new FirefoxConfigReader();

        for (Map.Entry<Object, Object> entry : firefoxConfigReader.entrySet()) {
            LOGGER.debug(entry.getKey() + " : " + entry.getValue());
            assertEquals(entry.getValue(), map.get(entry.getKey()));
        }

        map.put("browser.profile.name", "");

        FirefoxConfigReader firefoxConfigReaderMod = new FirefoxConfigReader("src\\test\\unit\\java\\com\\extjs\\selenium\\localhost-firefox-test.properties");
        for (Map.Entry<Object, Object> entry : firefoxConfigReaderMod.entrySet()) {
            LOGGER.debug(entry.getKey() + " : " + entry.getValue());
            assertEquals(entry.getValue(), map.get(entry.getKey()));
        }

        map.put("browser.profile.name", "default");
        map.put("profile.preference.browser.download.manager.closeWhenDone", "false");
        map.put("profile.preference.browser.download.manager.showAlertOnComplete", "true");
        map.put("browser.download.dir", "src\\test\\resources\\test\\");

        FirefoxConfigReader firefoxConfigReaderOverwrite = new FirefoxConfigReader("src\\test\\unit\\java\\com\\extjs\\selenium\\localhost-firefox-overwrite.properties");
        for (Map.Entry<Object, Object> entry : firefoxConfigReaderOverwrite.entrySet()) {
            LOGGER.debug(entry.getKey() + " : " + entry.getValue());
            assertEquals(entry.getValue(), map.get(entry.getKey()));
        }
    }

//    @Test
    public void propertiesChrome() {
        HashMap<String, String> mapChrome = new HashMap<String, String>();
        mapChrome.put("browser", "chrome");
        mapChrome.put("browser.driver.path", "src\\test\\resources\\drivers\\chromedriver.exe");
        mapChrome.put("browser.download.dir", "src\\test\\resources\\download\\");

        ChromeConfigReader chromeConfigReader = new ChromeConfigReader();

        for (Map.Entry<Object, Object> entry : chromeConfigReader.entrySet()) {
            LOGGER.debug(entry.getKey() + " : " + entry.getValue());
            assertEquals(entry.getValue(), mapChrome.get(entry.getKey()));
        }

        mapChrome.put("browser.download.dir", "src\\test\\resources\\test\\");

        ChromeConfigReader chromeConfigReader1 = new ChromeConfigReader("src\\test\\unit\\java\\com\\extjs\\selenium\\localhost-chrome-test.properties");
        for (Map.Entry<Object, Object> entry : chromeConfigReader1.entrySet()) {
            LOGGER.debug(entry.getKey() + " : " + entry.getValue());
            assertEquals(entry.getValue(), mapChrome.get(entry.getKey()));
        }

        mapChrome.put("browser.download.dir", "src\\test\\resources\\download\\");
        mapChrome.put("browser.driver.path", "src\\test\\resources\\test\\chromedriver.exe");

        ChromeConfigReader chromeConfigReader2 = new ChromeConfigReader("src\\test\\unit\\java\\com\\extjs\\selenium\\localhost-chrome-overwrite.properties");
        for (Map.Entry<Object, Object> entry : chromeConfigReader2.entrySet()) {
            LOGGER.debug(entry.getKey() + " : " + entry.getValue());
            assertEquals(entry.getValue(), mapChrome.get(entry.getKey()));
        }
    }

    @Test
    public void webLocatorChild() {
        WebLocator el1 = new WebLocator().setCls("1");
        WebLocator el2 = new WebLocator().setCls("2");
        WebLocator el = new WebLocator().setClasses("cls").setChildNodes(el1, el2);

        assertEquals(el.getPath(), "//*[contains(concat(' ', @class, ' '), ' cls ') and count(//*[@class='1'])>0 and count(//*[@class='2'])>0]");
    }
}

