package com.sdl.selenium.web.button;

import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.WebLocator;

public class UploadFile extends WebLocator implements Upload {

    public UploadFile() {
        setClassName("UploadFile");
    }

    /**
     * @param container parent
     */
    public UploadFile(WebLocator container) {
        this();
        setContainer(container);
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