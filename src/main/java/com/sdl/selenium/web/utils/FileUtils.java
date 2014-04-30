package com.sdl.selenium.web.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    private static final Logger logger = Logger.getLogger(FileUtils.class);

    public static String getValidFileName(String fileName) {
        String regex = "\\\\|:|/|\\*|\\?|\\<|\\>|\\|"; // matches special characters: ,(comma) (space)&><@?\/'"
        fileName = fileName.replaceAll(regex, "_");
        fileName = fileName.replaceAll(";", "_"); // replace semicolon only after replacing characters like &amp, &gt etc
        return fileName;
    }


    public static boolean waitFileIfIsEmpty(File file) {
        boolean empty;
        int time = 0;
        do {
            logger.debug("Content file is empty in: " + time);
            time++;
            Utils.sleep(50);
            empty = file.length() > 0;
        } while (!empty && time < 100);
        return empty;
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

    /**
     * @param zipFilePath zipFilePath
     * @param outputFolderPath if null of empty will extract in same folder as zipFilePath
     * @return True | False
     */
    public static boolean unZip(String zipFilePath, String outputFolderPath) {
        byte[] buffer = new byte[1024];
        try {
            long startMs = System.currentTimeMillis();
            //create output directory if doesn't exists
            if (outputFolderPath == null || "".equals(outputFolderPath)) {
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
                File newFile = new File(outputFolderPath + File.separator + fileName);

                logger.info("file unzip : " + newFile.getAbsoluteFile());

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

            logger.info("Unzip Done: " + zipFilePath);
            long endMs = System.currentTimeMillis();
            logger.debug(String.format("unzip took %s ms", endMs - startMs));
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * @param filePath filePath
     * @param extractedFilePath extractedFilePath
     * @return  true | false
     * @deprecated use {@link #unZip(String, String)}
     */
    public static boolean unZip2(String filePath, String extractedFilePath) {
        return unZip(filePath, extractedFilePath);
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

    public static String getFileNameFromPath(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    public static void main(String args[]) {

    }
}