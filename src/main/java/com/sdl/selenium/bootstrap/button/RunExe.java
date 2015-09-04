package com.sdl.selenium.bootstrap.button;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class RunExe {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunExe.class);
    
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

    public boolean upload(String... filePath) {
        if(filePath.length > 1){
            return doRun(filePath[0] + " \"" + filePath[1] + "\"");
        } else {
            URL resource = Thread.currentThread().getContextClassLoader().getResource("upload.exe");
            return resource != null && doRun(resource.getFile() + " \"" + filePath[0] + "\"");
        }
    }

    private boolean doRun(String filePath) {
        try {
            Process process = Runtime.getRuntime().exec(filePath);
            LOGGER.debug(filePath);
            if (0 == process.waitFor()) {
                return true;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}