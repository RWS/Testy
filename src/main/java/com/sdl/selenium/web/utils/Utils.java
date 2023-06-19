package com.sdl.selenium.web.utils;

import com.google.common.base.Strings;
import com.sdl.selenium.utils.config.WebDriverConfig;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * TODO Sa fie o metoda de Utils care sa poti seta din WebLocator path-ul unde sa salveze imaginile in dependenta de WebDriver ori Selenium
 */
public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static void sleep(long milliseconds) {
        try {
            if (milliseconds > 0) {
                Thread.sleep(milliseconds);
            }
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException: {}", e);
        }
    }

    public static String getEscapeQuotesText(String text) {
        boolean hasDoubleQuote = text.contains("\"");
        boolean hasSingeQuote = text.contains("'");
        if (Strings.isNullOrEmpty(text)) {
            return text;
        } else if (hasDoubleQuote && hasSingeQuote) {
            boolean quoteIsLast = text.lastIndexOf("\"") == text.length() - 1;
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

    public static String getScreenShot(String fileName, String screensPath) {
        WebDriver driver = WebDriverConfig.getDriver();
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        fileName = (dfm.format(new Date())) + "-" + fileName + ".jpg";
        fileName = FileUtils.getValidFileName(fileName);
        String filePath = screensPath + fileName;
        try {
            File screensDir = new File(screensPath);
            screensDir.mkdirs();
            LOGGER.info("Screenshot: " + filePath);

            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenShot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            screenShot.setWritable(true);
            File file = new File(filePath);
            screenShot.renameTo(file);
        } catch (Exception e) {
            LOGGER.error("Failed to capture screenshot: ", e);
        }
        return fileName;
    }

    public static boolean eq(Object a, Object b) {
        boolean equals = a == b || (a != null && a.equals(b));
        if (!equals) {
            LOGGER.debug("'" + a + "' is not equals with '" + b + "'");
        }
        return equals;
    }

    public static boolean eqArray(String[] a, String[] b) {
        return Arrays.equals(a, b);
    }

    public static void deprecated() {
        LOGGER.warn("= = = = = @Deprecated = = = = =");
    }
}
