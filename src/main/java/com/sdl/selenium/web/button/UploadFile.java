package com.sdl.selenium.web.button;

import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFile extends WebLocator implements Upload {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFile.class);

    public UploadFile() {
        withClassName("UploadFile");
    }

    /**
     * @param container parent
     */
    public UploadFile(WebLocator container) {
        this();
        withContainer(container);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload(this, "C:\\upload.exe", "C:\\text.txt");
     * @deprecated The next version, replaced by {@link #upload(String)}.
     *
     * @param filePath e.g. "C:\\upload.exe", "C:\\text.txt"
     * @return true | false
     */
    @Deprecated
    public boolean upload(String... filePath) {
        return upload(this, filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload("C:\\text.txt");
     *
     * @param filePath e.g. "C:\\text.txt"
     * @return true | false
     */
    @Override
    public boolean upload(String filePath) {
        return upload(this, filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload(this, "C:\\upload.exe", "C:\\text.txt");
     * @deprecated The next version, replaced by {@link #upload(WebLocator, String)}.
     * @param el the item that you click to open upload window
     * @param filePath e.g. "C:\\upload.exe", "C:\\text.txt"
     * @return true | false
     */
    @Deprecated
    public boolean upload(WebLocator el, String... filePath) {
        return executor.browse(el) && executor.upload(filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload(this, "C:\\text.txt");
     *
     * @param el the item that you click to open upload window
     * @param filePath e.g. "C:\\text.txt"
     * @return true | false
     */
    public boolean upload(WebLocator el, String filePath) {
        return executor.browse(el) && executor.upload(filePath);
    }
}