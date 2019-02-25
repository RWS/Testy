package com.sdl.selenium.bootstrap.button;

import com.sdl.selenium.web.WebLocator;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <div>
 *  <div class="fileupload fileupload-new" data-provides="fileupload">
 *      <span class="btn btn-file"><span class="fileupload-new">Browse</span><spanclass="fileupload-exists">Change</span><input type="file"/></span>
 *      <span class="fileupload-preview"></span>
 *      <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none">×</a>
 *  </div>
 * </div>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * private UploadFile uploadButton = new UploadFile().setText("Browse");
 * uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx");
 * }</pre>
 */
public class UploadFile extends WebLocator implements Upload {

    public UploadFile() {
        setClassName("UploadFile");
        setBaseCls("fileupload");
        setTag("div");
    }

    /**
     * @param container parent
     */
    public UploadFile(WebLocator container) {
        this();
        setContainer(container);
    }

    public UploadFile(WebLocator container, String label) {
        this(container);
        setLabel(label);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.upload("C:\\text.txt");
     *
     * @param filePath e.g. ""C:\\text.txt"
     * @return true | false
     */
    @Override
    public boolean upload(String filePath) {
        WebLocator upload = new WebLocator(this).setTag("input").setType("file");
        return upload(upload, filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: {@code button.upload("Browse", "C:\\text.txt")};}
     *
     * @param text     button text
     * @param filePath e.g. "C:\\text.txt"
     * @return true | false
     */
    public boolean upload(String text, String filePath) {
        WebLocator upload = new WebLocator(this).setTag("input").setLabel(text).setLabelPosition("//following-sibling::").setLabelTag("span").setType("file");
        return upload(upload, filePath);
    }

    public boolean change(String filePath) {
        return change("Change", filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: {@code button.change("Change", "C:\\text.txt");}
     *
     * @param text e.g. "Change"
     * @param filePath e.g. "C:\\text.txt"
     * @return true | false
     */
    public boolean change(String text, String filePath) {
        WebLocator upload = new WebLocator(this).setTag("input").setLabel(text).setLabelPosition("//following-sibling::").setLabelTag("span").setType("file");
        return upload(upload, filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.newUpload("C:\\text.txt");
     *
     * @param filePath "C:\\text.txt"
     * @return true | false
     */
    public boolean newUpload(String filePath) {
        WebLocator uploadButton = new WebLocator(this).setTag("span").setClasses("fileupload-new").setElPathSuffix("icon-folder-open", "count(.//i[@class='icon-folder-open']) > 0");
        return upload(uploadButton, filePath);
    }

    /**
     * Upload file with AutoIT.
     * Use only this: button.reUpload("C:\\text.txt");
     *
     * @param filePath e.g. "C:\\text.txt"
     * @return true | false
     */
    public boolean reUpload(String filePath) {
        WebLocator uploadButton = new WebLocator(this).setTag("span").setClasses("fileupload-exists").setElPathSuffix("icon-refresh", "count(.//i[@class='icon-refresh']) > 0");
        return upload(uploadButton, filePath);
    }

    public boolean removeFile() {
        WebLocator removeButton = new WebLocator(this).setTag("a").setClasses("fileupload-exists").setElPathSuffix("icon-trash", "count(.//i[@class='icon-trash']) > 0");
        return removeButton.clickAt();
    }

    public String uploadedNameFile() {
        WebLocator upload = new WebLocator(this).setTag("span").setClasses("fileupload-preview");
        return upload.getText();
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
        return executor().browse(el) && executor().upload(filePath);
    }
}