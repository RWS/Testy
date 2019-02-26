package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.form.TextField;

public class FileUpload extends TextField implements Upload {

    public FileUpload() {
        setClassName("FileUpload");
        setType("file");
    }

    public FileUpload(Locator container) {
        this();
        setContainer(container);
    }

    public FileUpload(Locator container, String label) {
        this(container);
        setLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
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
    private boolean upload(Locator el, String filePath) {
        return executor().browse(el) && executor().upload(filePath);
    }
}