package com.sdl.selenium.web.utils;

import com.sdl.selenium.utils.config.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fratiu
 * @since 5/3/2016.
 */
public class MultiThreadClipboardUtils {

    private static Map<String, String> clipboardContents = new HashMap<>();
    private static final String LOCK_FILE_NAME = "target/clipboardLock.lock";
    private static File lockFile = new File(LOCK_FILE_NAME);
    private static long lockTimeoutMillis = 5000;

    /***
     * Correlates the given String with the current WebDriver instance and stores it so it can be pasted somewhere else.
     * Note that at this point the value is not added to the system clipboard, but only stored in the application memory,
     * so it can only be pasted using the {@link #pasteString(WebLocator) pasteString} method.
     * <p>
     * This implementation is useful for avoiding mixing clipboard values from one WebDriver instance to another
     *
     * @param value String to be copied
     */
    public static void copyString(String value) {
        clipboardContents.put(((RemoteWebDriver) WebDriverConfig.getDriver()).getSessionId().toString(), value);
    }

    /***
     * Waits until the system clipboard is not being used by another WebDriver instance,
     * then populates the system clipboard with the value previously stored for the current WebDriver instance
     * and finally sends the CTRL + v command to the specified locator
     * <p>
     * {@link #copyString(String) copyString} method should have been called before
     *
     * @param locator WebLocator where the value from clipboard corresponding to the current WebDriver instance should be pasted
     */
    public static void pasteString(WebLocator locator) {
        waitForUnlockedClipboard();
        lockClipboard();
        String value = clipboardContents.get(((RemoteWebDriver) WebDriverConfig.getDriver()).getSessionId().toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value),
                new ClipboardOwner() {
                    @Override
                    public void lostOwnership(final java.awt.datatransfer.Clipboard clipboard, final Transferable contents) {
                        // do nothing
                    }
                });
        try {
            locator.sendKeys(Keys.CONTROL, "v");
        } catch (Throwable throwable) {
            // Making sure clipboard would not unexpectedly remain locked
            unlockClipboard();
        }
        unlockClipboard();
    }

    public static void waitForUnlockedClipboard(long lockTimeoutMillis) {
        long remainingMillis = lockTimeoutMillis;
        File lockFile = new File(LOCK_FILE_NAME);
        while (lockFile.exists() && remainingMillis > 0) {
            Utils.sleep(50);
            remainingMillis -= 50;
        }
        if (lockFile.exists()) {
            unlockClipboard();
            if (lockFile.exists()) {
                throw new RuntimeException("Clipboard was not unlocked after " + lockTimeoutMillis / 1000 + " second(s).");
            }
        }
    }

    public static void waitForUnlockedClipboard() {
        waitForUnlockedClipboard(lockTimeoutMillis);
    }

    private static void lockClipboard() {
        try {
            lockFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create clipboard lock");
        }
    }

    private static void unlockClipboard() {
        boolean lockRemoved = !lockFile.exists() || lockFile.delete();
        if (lockFile.exists()) {
            throw new RuntimeException("Failed to remove clipboard lock");
        }
    }

    public static void setLockTimeoutMillis(long lockTimeoutMillis) {
        MultiThreadClipboardUtils.lockTimeoutMillis = lockTimeoutMillis;
    }
}
