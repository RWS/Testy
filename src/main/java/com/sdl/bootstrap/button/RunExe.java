package com.sdl.bootstrap.button;

import org.apache.log4j.Logger;

import java.io.IOException;

public class RunExe {
    private static final Logger logger = Logger.getLogger(RunExe.class);
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
        return doRun(filePath[0] + " \"" + filePath[1] + "\"");
    }

    private boolean doRun(String filePath) {
        try {
            Process process = Runtime.getRuntime().exec(filePath);
            logger.debug(filePath);
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
}