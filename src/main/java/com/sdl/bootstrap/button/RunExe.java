package com.sdl.bootstrap.button;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

public class RunExe {
    private static final Logger logger = Logger.getLogger(RunExe.class);
    private static RunExe instance = new RunExe();
    private static Long RUN_EXE_THREAD_INTERVAL = 500L;

    private RunExe() {
    }

    public static RunExe getInstance() {
        return instance;
    }

    public boolean download(String[] filePath) {
        return download(filePath, downloadWindowName());
    }

    public boolean download(String[] filePath, Long timeout) {
        return download(filePath, downloadWindowName(), timeout);
    }

    public boolean download(String filePath) {
        return doRun(filePath);
    }

    public boolean download(String filePath, Long timeout) {
        return doRun(filePath, timeout);
    }

    public boolean download(String[] filePath, String downloadWindowName) {
        return doRun(filePath[0] + " \"" + filePath[1] + "\" " + downloadWindowName);
    }

    public boolean download(String[] filePath, String downloadWindowName, Long timeout) {
        return doRun(filePath[0] + " \"" + filePath[1] + "\" " + downloadWindowName, timeout);
    }

    private String downloadWindowName() {
        return WebLocator.driver instanceof FirefoxDriver ? "Opening" : "Save As";
    }

    public boolean upload(String[] filePath) {
        return doRun(filePath[0] + " \"" + filePath[1] + "\"");
    }

    public boolean upload(String[] filePath, Long timeout) {
        return doRun(filePath[0] + " \"" + filePath[1] + "\"", timeout);
    }

    private boolean doRun(String filePath, Long timeout) {
        //create a thread in witch will run the exe file
        RunExeThread myRunThread = new RunExeThread(filePath);
        myRunThread.start();

        //waiting for the thread to execute complete the exe file

        while (myRunThread.getRunOk() == null) {
            Utils.sleep(RUN_EXE_THREAD_INTERVAL);
            timeout -= RUN_EXE_THREAD_INTERVAL;
            logger.debug("Waiting for " + filePath + " to finish executing.");
            if (timeout <= 0) {
                return false;
            }
        }
        return myRunThread.getRunOk();
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

    private String uploadWindowName() {
        return WebLocator.driver instanceof FirefoxDriver ? "File Upload" : "Open";
    }

    /**
     * The thread that will run an exe file
     */
    public class RunExeThread extends Thread {
        private String filePath;
        private Boolean runOk = null;

        public RunExeThread(String filePath) {
            this.filePath = filePath;
        }

        public void run() {
            runOk = doRun(filePath);
        }

        public Boolean getRunOk() {
            return runOk;
        }
    }
}