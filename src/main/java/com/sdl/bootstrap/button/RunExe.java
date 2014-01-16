package com.sdl.bootstrap.button;

import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

public class RunExe {

    private static RunExe instance = new RunExe();

    private RunExe() {
    }

    public static RunExe getInstance() {
        return instance;
    }

    public boolean download(String[] filePath) {
        return download(filePath, downloadWindowName());
    }

    public boolean download(String filePath) {
        return doRun(filePath);
    }

    public boolean download(String[] filePath, String downloadWindowName) {
        return doRun(filePath[0] + " \"" + filePath[1] + "\" " + downloadWindowName);
    }

    private String downloadWindowName() {
        return WebLocator.driver instanceof FirefoxDriver ? "Opening" : "Save As";
    }

    public boolean upload(String[] filePath) {
        return doRun(filePath[0] + " \"" + filePath[1] + "\"");
    }

    private boolean doRun(String filePath) {
        try {
            Process process = Runtime.getRuntime().exec(filePath);
            if (0 == process.waitFor()) {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String uploadWindowName() {
        return WebLocator.driver instanceof FirefoxDriver ? "File Upload" : "Open";
    }
}