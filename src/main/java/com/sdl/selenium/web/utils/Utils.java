package com.sdl.selenium.web.utils;

import com.sdl.selenium.web.WebDriverConfig;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * TODO Sa fie o metoda de Utils care sa poti seta din WebLocator path-ul unde sa salveze imaginile in dependenta de WebDriver ori Selenium
 */
public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class);

    public static void sleep(long milliseconds) {
        try {
            if (milliseconds > 0) {
                Thread.sleep(milliseconds);
            }
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    public static void conditionalSleep(int maxSeconds, boolean condition) {
        int count = 0;
        while ((count < maxSeconds) && !condition) {
            sleep(1000);
            count++;
        }
    }

    /**
     * TODO find better solution
     *
     * @param milliseconds Try to make more specific method for grids for example (with parameter loadMsg)
     * @return True | False
     */
    public static boolean pleaseWait(int milliseconds) {
        int i = 0;
        do {
            Utils.sleep(milliseconds);
            i++;
            if (i == 60) {
                logger.warn("pleaseWait. Waited for 60x" + milliseconds + " milliseconds.");
                return false;
            }
        } while (WebDriverConfig.getDriver().getPageSource().contains("Please Wait..."));
        return true;
    }

    /**
     * TODO find better solution
     * Try to make more specific method for grids for example (with parameter loadMsg)
     *
     * @param milliseconds milliseconds
     * @return True | False
     */
    public static boolean loading(int milliseconds) {
        int i = 0;
        do {
            Utils.sleep(milliseconds);
            i++;
            if (i == 60) {
                logger.warn("loading. Waited for 60x" + milliseconds + " milliseconds.");
                return false;
            }
        } while (WebDriverConfig.getDriver().getPageSource().contains("Loading..."));
        return true;
    }

    /**
     * remove the first " and "
     *
     * @param selector " and"
     * @return String
     */
    public static String fixPathSelector(String selector) {
        if (selector.startsWith(" and ")) {
            selector = selector.substring(5);
        }
        return selector;
    }

    public static String getEscapeQuotesText(String text) {
        boolean hasDoubleQuote = text.contains("\"");
        boolean hasSingeQuote = text.contains("'");
        if (hasDoubleQuote && hasSingeQuote) {
            boolean quoteIsLast = false;
            if (text.lastIndexOf("\"") == text.length() - 1) {
                quoteIsLast = true;
            }
            String[] substrings = text.split("\"");

            StringBuilder quoted = new StringBuilder("concat(");
            for (int i = 0; i < substrings.length; i++) {
                quoted.append("\"").append(substrings[i]).append("\"");
                quoted.append(((i == substrings.length - 1) ? (quoteIsLast ? ", '\"')" : ")") : ", '\"', "));
            }
            return quoted.toString();
        } else if (hasDoubleQuote || !hasSingeQuote) {
            return String.format("'%s'", text);
        }
        return String.format("\"%s\"", text);
    }

    /**
     * add 'css=' at the beginning of the string
     *
     * @param selector "css="
     * @return String
     */
    public static String fixCssSelector(String selector) {
        selector = selector.replaceAll("css=", "");
//        selector = "css=" + selector;
        if (selector.endsWith("*")) {
            selector = selector.substring(0, selector.length() - 1);
        }
        return selector;
    }

    public static void copyToClipboard(final String text) {
        final StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,
                new ClipboardOwner() {
                    @Override
                    public void lostOwnership(final java.awt.datatransfer.Clipboard clipboard, final Transferable contents) {
                        // do nothing
                    }
                });
    }

    public static String getScreenShot(String fileName, String screensPath) {
        WebDriver driver = WebDriverConfig.getDriver();
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        fileName = (dfm.format(new Date())) + "-" + fileName + ".jpg";
        fileName = FileUtils.getValidFileName(fileName);
        String filePath = screensPath + fileName;
        try {
//            TestProperties properties = TestProperties.getInstance();
//            String screensPath = properties.getProjectDir()+ "\\reports\\screens\\";
            File screensDir = new File(screensPath);
            screensDir.mkdirs();
            logger.info("Screenshot: " + filePath);

            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenShot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            screenShot.setWritable(true);
            File file = new File(filePath);
            screenShot.renameTo(file);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: ", e);
        }
        return fileName;
    }

    public static boolean eq(Object a, Object b) {
        boolean equals = a == b || (a != null && a.equals(b));
        if (!equals) {
            logger.debug("'" + a + "' is not equals with '" + b + "'");
        }
        return equals;
    }

    public static boolean eqArray(String[] a, String[] b) {
        return Arrays.equals(a, b);
    }

    public static void main(String args[]){
    }
}
