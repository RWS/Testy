package com.sdl.selenium.extjs3.button;

import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class UploadButton extends Button implements Upload {

    public UploadButton() {
        withClassName("UploadButton");
    }

    public UploadButton(WebLocator container) {
        this();
        withContainer(container);
    }

    public UploadButton(WebLocator container, String text) {
        this(container);
        withText(text, SearchType.EQUALS);
    }

    public UploadButton(WebLocator container, String text, boolean isInternationalized) {
        this(container);
        withText(text, isInternationalized, SearchType.EQUALS);
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
    private boolean upload(WebLocator el, String... filePath) {
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
    private boolean upload(WebLocator el, String filePath) {
        return executor.browse(el) && executor.upload(filePath);
    }
}
