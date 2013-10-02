package com.extjs.selenium;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class UtilsTest {
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
        System.out.println("modified file name " + Utils.getValidFileName(originalFileName));
        System.out.println("valid file name = " + validFileName);
        Assert.assertEquals(Utils.getValidFileName(originalFileName), validFileName);
    }

    @Test(dataProvider = "validateTextWithQuotes")
    public void textWithQuotes(String original, String expected) {
        Assert.assertEquals(Utils.getEscapeQuotesText(original), expected);
    }
}

