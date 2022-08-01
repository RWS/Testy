package com.sdl.selenium.web.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.sdl.selenium.utils.config.WebDriverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\\r\\n|\\r|\\n");

    public static String getValidFileName(String fileName) {
        String regex = "\\\\|:|/|\\*|\\?|\\<|\\>|\\|"; // matches special characters: ,(comma) (space)&><@?\/'"
        fileName = fileName.replaceAll(regex, "_");
        fileName = fileName.replaceAll(";", "_"); // replace semicolon only after replacing characters like &amp, &gt etc
        return fileName;
    }

    public static boolean waitFileIfIsEmpty(File file, long timeoutMillis) {
        boolean isNotEmpty;
        do {
            isNotEmpty = file.length() > 0;
            if (!isNotEmpty) {
                LOGGER.debug("File exist: '" + file.exists() + "' and content file is empty in: " + timeoutMillis);
                Utils.sleep(300);
            }
            timeoutMillis -= 300;
        } while (!isNotEmpty && timeoutMillis > 0);
        return isNotEmpty;
    }

    public static boolean waitFileToHaveSize(File file, long size, int maxRetries) {
        return RetryUtils.retry(maxRetries, () -> {
            boolean greaterThanExpected = file.length() >= size;
            if (!greaterThanExpected) {
                Utils.sleep(300);
            }
            return greaterThanExpected;
        });
    }

    public static String getTextFromFile(String pathFile) {
        return getTextFromFile(pathFile, Charsets.UTF_8);
    }

    public static String getTextFromFile(String pathFile, Charset cs) {
        String strLine = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), cs));
            String tmp;
            while ((tmp = br.readLine()) != null) {
                strLine += tmp;
            }
            LOGGER.debug("length {}", strLine.length());
            br.close();
        } catch (Exception e) {
            LOGGER.debug("Error: {}", e.getMessage());
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

    /**
     * @param zipFilePath      zipFilePath
     * @param outputFolderPath if null of empty will extract in same folder as zipFilePath
     * @return True | False
     */
    public static boolean unZip(String zipFilePath, String outputFolderPath) {
        byte[] buffer = new byte[1024];
        try {
            long startMs = System.currentTimeMillis();
            //create output directory if doesn't exists
            if (Strings.isNullOrEmpty(outputFolderPath)) {
                // unzip in same folder as zip file
                outputFolderPath = new File(zipFilePath).getParent();
            }
            File folder = new File(outputFolderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFilePath));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolderPath, fileName);
                if (!newFile.toPath().normalize().startsWith(outputFolderPath)) {
                    throw new RuntimeException("Bad zip entry");
                }

                LOGGER.info("file unzip : " + newFile.getAbsoluteFile());

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

            LOGGER.info("Unzip Done: " + zipFilePath);
            long endMs = System.currentTimeMillis();
            LOGGER.debug(String.format("unzip took %s ms", endMs - startMs));
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();

    }

    @Deprecated
    public static boolean compareFiles(String currentFilePath, String expectedFilePath) {
        return compare(currentFilePath, expectedFilePath).isResult();
    }

    public static ContentFiles compare(String currentFilePath, String expectedFilePath) {
        String currentFileContent = null;
        String expectedFileContent = null;
        try {
            File currentFile = new File(currentFilePath);
            File expectedFile = new File(expectedFilePath);
            FileUtils.waitFileIfIsEmpty(currentFile, 10000);
            FileUtils.waitFileIfIsEmpty(expectedFile, 10000);
            currentFileContent = formatToSystemLineSeparator(convertStreamToString(new FileInputStream(currentFile)));
            expectedFileContent = formatToSystemLineSeparator(convertStreamToString(new FileInputStream(expectedFile)));
            boolean equals = currentFileContent.equals(expectedFileContent);
            return new ContentFiles(currentFileContent, expectedFileContent, equals);
        } catch (IOException e) {
            e.printStackTrace();
            return new ContentFiles(currentFileContent, expectedFileContent, false);
        }
    }

    private static String formatToSystemLineSeparator(String input) {
        return NEW_LINE_PATTERN.matcher(input).replaceAll(Matcher.quoteReplacement(System.getProperty("line.separator")));
    }

    public static String convertStreamToString(InputStream is) {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                reader.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static boolean compareFileSize(String filePath1, String filePath2) {
        boolean equal = false;
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        LOGGER.info("file1.length = " + file1.length());
        LOGGER.info("file2.length = " + file2.length());
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

    public static String getFileNameFromPath(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    public static void cleanDownloadDir() {
        try {
            File directory = new File(WebDriverConfig.getDownloadPath());
            if (directory.exists()) {
                org.apache.commons.io.FileUtils.cleanDirectory(directory);
            }
            LOGGER.debug("Clean download directory with success.");
        } catch (IOException e) {
            LOGGER.debug("Clean Download Dir with error {}", e.getMessage());
        }
    }
}
