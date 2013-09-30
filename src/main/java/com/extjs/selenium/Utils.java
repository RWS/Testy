package com.extjs.selenium;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * TODO move this class to new pack near WebLocator (so can be used with no extjs pack)
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
     * @return
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
        } while (ExtJsComponent.driver.getPageSource().contains("Please Wait..."));
        return true;
    }

    /**
     * TODO find better solution
     * Try to make more specific method for grids for example (with parameter loadMsg)
     *
     * @param milliseconds
     * @return
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
        } while (ExtJsComponent.driver.getPageSource().contains("Loading..."));
        return true;
    }

    /**
     * remove the first " and "
     *
     * @param selector
     * @return
     */
    public static String fixPathSelector(String selector) {
        if (selector.startsWith(" and ")) {
            selector = selector.substring(5);
        }
        return selector;
    }

    public static String escapeQuotes(String toEscape) {
        if (toEscape.indexOf("\"") > -1 && toEscape.indexOf("'") > -1) {
            boolean quoteIsLast = false;
            if (toEscape.lastIndexOf("\"") == toEscape.length() - 1) {
                quoteIsLast = true;
            }
            String[] substrings = toEscape.split("\"");

            StringBuilder quoted = new StringBuilder("concat(");
            for (int i = 0; i < substrings.length; i++) {
                quoted.append("\"").append(substrings[i]).append("\"");
                quoted.append(((i == substrings.length - 1) ? (quoteIsLast ? ", '\"')" : ")") : ", '\"', "));
            }
            return quoted.toString();
        } else if (toEscape.indexOf("\"") == -1 && toEscape.indexOf("'") == -1 || toEscape.indexOf("\"") > -1) { // Escape string with just a quote into being single quoted: f"oo -> 'f"oo'
            return String.format("'%s'", toEscape);
        }

        // Otherwise return the quoted string
        return String.format("\"%s\"", toEscape);
    }

    /**
     * add 'css=' at the beginning of the string
     *
     * @param selector
     * @return
     */
    public static String fixCssSelector(String selector) {
        selector = selector.replaceAll("css=", "");
//        selector = "css=" + selector;
        if (selector.endsWith("*")) {
            selector = selector.substring(0, selector.length() - 1);
        }
        return selector;
    }

    public static String getValidFileName(String fileName) {
        String regex = "\\\\|:|/|\\*|\\?|\\<|\\>|\\|"; // matches special characters: ,(comma) (space)&><@?\/'"
        fileName = fileName.replaceAll(regex, "_");
        fileName = fileName.replaceAll(";", "_"); // replace semicolon only after replacing characters like &amp, &gt etc
        return fileName;
    }

    public static String getScreenShot(String fileName, String screensPath) {
        WebDriver driver = WebLocator.getDriver();
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        fileName = (dfm.format(new Date())) + "-" + fileName + ".jpg";
        fileName = getValidFileName(fileName);
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
            logger.error("Failed to capture screenshot: " + e);
        }
        return fileName;
    }

    public static boolean eq(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static boolean eqArray(String[] a, String[] b) {
        return Arrays.equals(a, b);
    }

    public static String getTextFromFile(String pathFile) {
        String strLine = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), "UTF8"));
            String tmp;
            while ((tmp = br.readLine()) != null) {
                strLine += tmp;
            }
            logger.debug(strLine.length());
            br.close();
        } catch (Exception e) {
            logger.debug("Error: " + e.getMessage());
        }
        return strLine;
    }

    public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);

        in.close();
        out.close();
    }

    //
    public static boolean unZip2(String filePath, String extractedFilePath) {
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        byte[] buffer = new byte[1024];

        try {

            //create output directory is not exists
            File folder = new File(extractedFilePath);
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(filePath));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                //String fileName = dfm.format(new DateField()) + ze.getName();
                File newFile = new File(extractedFilePath + File.separator + fileName);


                logger.info("file unzip : " + newFile.getAbsoluteFile());
                //System.out.println("file unzip : "+ newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            logger.info("Unzip Done!!!");

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return (file.delete());

    }

    public static boolean compareFiles(String filePath1, String filePath2) {
        boolean equal = true;
        BufferedReader br1 = null;
        BufferedReader br2 = null;
        try {

            br1 = new BufferedReader(new FileReader(filePath1));
            br2 = new BufferedReader(new FileReader(filePath2));

            String strLine1, strLine2;

            do {
                strLine1 = br1.readLine();
                strLine2 = br2.readLine();
                if (strLine1 == null && strLine2 == null) {
                    br1.close();
                    br2.close();
                    return true;
                } else if (strLine1 == null || strLine2 == null || !strLine1.equals(strLine2)) {
                    logger.debug("The files are not equal." + strLine1 + " != " + strLine2);
                    br1.close();
                    br2.close();
                    return false;
                }
            } while (true);
        } catch (IOException ex) {
            logger.debug("Exception occured" + ex);
        }
        return equal;
    }

    public static boolean compareFileSize(String filePath1, String filePath2) {
        boolean equal = false;
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        logger.info("file1.length = " + file1.length());
        logger.info("file2.length = " + file2.length());
        if (file1.length() == file2.length()) {
            equal = true;
        }
        return equal;
    }

    public static String obtainFileName(String fileName, String insertedText) {
        return obtainFileName(fileName, insertedText, null);
    }

    public static String obtainFileName(String fileName, String insertedText, String extension) {

        String[] pathSplit = fileName.split("\\\\");
        String[] splitted = pathSplit[pathSplit.length - 1].split("\\.");
        String result = "";
        for (int i = 0; i < (splitted.length - 1); i++) {
            result += splitted[i];

        }
        result += insertedText + "." + (extension == null ? splitted[splitted.length - 1] : extension);
        return result;
    }

    public static String getFileFromPath(String filePath) {
        File file = new File(filePath);
        return file.getName();
//        String[] pathSplit = filePath.split("\\\\");
//        String fileName = pathSplit[pathSplit.length-1];
//        return fileName;
    }

    public static void main(String args[]) {
        String e = "Don't Accept";
        logger.debug(e);
        e = escapeQuotes(e);
        logger.debug(e);

    }
}