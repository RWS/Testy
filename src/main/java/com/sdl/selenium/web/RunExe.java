package com.sdl.selenium.web;

import com.sdl.selenium.utils.config.WebLocatorConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class RunExe {

    private static RunExe instance = new RunExe();

    private RunExe() {
    }

    public static RunExe getInstance() {
        return instance;
    }

    public boolean download(String filePath) {
        return doRun(filePath);
    }

    public boolean download(String downloadWindowName, String filePath) {
        return doRun(filePath + " " + downloadWindowName);
    }

    public boolean upload(String filePath) {
        return doRun(WebLocatorConfig.getUploadExePath() + " \"" + filePath + "\"");
    }

    private boolean doRun(String filePath) {
        try {
            Process process = Runtime.getRuntime().exec(filePath);
            log.debug(filePath);
            if (0 == process.waitFor()) {
                return true;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}