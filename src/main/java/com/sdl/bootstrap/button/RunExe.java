package com.sdl.bootstrap.button;

import com.extjs.selenium.Utils;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.Date;

public class RunExe {

    private static RunExe instance = new RunExe();

    private RunExe() {
    }

    public static RunExe getInstance() {
        return instance;
    }

    public boolean download(String[] filePath) {
        return upload(filePath[0] + " " + filePath[1] + " " + downloadName());
    }

    private String downloadName() {
        return WebLocator.driver instanceof FirefoxDriver ? "Opening" : "Save As";
    }

    public boolean upload(String[] filePath) {
        return upload(filePath[0] + " " + filePath[1] + " " + uploadName());
    }

    public boolean upload(String filePath) {
        RunExeThread runExeThread = new RunExeThread(filePath);
        runExeThread.start();
        long startTime = new Date().getTime();
        while (Thread.State.RUNNABLE.equals(runExeThread.getState())) {
            Utils.sleep(200);
            long currentTime = new Date().getTime();
            if (currentTime - startTime >= 10000) {
                return false;
            }
        }
        return Thread.State.TIMED_WAITING.equals(runExeThread.getState());
    }

    private String uploadName() {
        return WebLocator.driver instanceof FirefoxDriver ? "File Upload" : "Open";
    }
}

class RunExeThread extends Thread {

    private String exePath;

    RunExeThread(String exePath) {
        this.exePath = exePath;
    }

    public void run() {
        try {
            Process process = Runtime.getRuntime().exec(exePath);
            while (0 != process.waitFor()) {
                Utils.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
