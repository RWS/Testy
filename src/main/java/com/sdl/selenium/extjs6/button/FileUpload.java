package com.sdl.selenium.extjs6.button;

import com.sdl.selenium.bootstrap.button.Upload;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUpload extends TextField implements Upload {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUpload.class);

    public FileUpload() {
        withClassName("FileUpload");
        withType("file");
    }

    public FileUpload(WebLocator container) {
        this();
        withContainer(container);
    }

    public FileUpload(WebLocator container, String label) {
        this(container);
        withLabel(label, SearchType.DEEP_CHILD_NODE_OR_SELF);
    }

    public FileUpload(WebLocator container, String label, boolean isInternationalized) {
        this(container);
        withLabel(label, isInternationalized, SearchType.DEEP_CHILD_NODE_OR_SELF);
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
    private boolean upload(WebLocator el, String filePath) {
        return executor.browse(el) && executor.upload(filePath);
    }
}
