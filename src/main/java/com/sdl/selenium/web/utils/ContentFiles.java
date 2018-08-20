package com.sdl.selenium.web.utils;

public class ContentFiles {
    private String currentFileContent;
    private String expectedFileContent;
    private boolean result;

    public ContentFiles(String currentFileContent, String expectedFileContent, boolean result) {
        this.currentFileContent = currentFileContent;
        this.expectedFileContent = expectedFileContent;
        this.result = result;
    }

    public String getCurrentFileContent() {
        return currentFileContent;
    }

    public void setCurrentFileContent(String currentFileContent) {
        this.currentFileContent = currentFileContent;
    }

    public String getExpectedFileContent() {
        return expectedFileContent;
    }

    public void setExpectedFileContent(String expectedFileContent) {
        this.expectedFileContent = expectedFileContent;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
