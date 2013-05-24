package com.extjs.selenium.grid;

import com.extjs.selenium.Utils;
import com.extjs.selenium.form.ComboBox;
import com.extjs.selenium.form.TextArea;
import com.extjs.selenium.form.TextField;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;

public class EditorGridPanel extends GridPanel {
    private static final Logger logger = Logger.getLogger(EditorGridPanel.class);

    private int clicksToEdit = 2;

    public EditorGridPanel() {
        setClassName("EditorGridPanel");
        //logger.debug(getClassName() + "() constructor");
    }

    public EditorGridPanel(WebLocator container) {
        this();
        setContainer(container);
    }

    public EditorGridPanel(String cls) {
        this();
        setCls(cls);
    }

    public EditorGridPanel(String cls, String searchColumnId) {
        this(cls);
        setSearchColumnId(searchColumnId);
    }

    public EditorGridPanel(WebLocator container, String searchColumnId) {
        this(container);
        setSearchColumnId(searchColumnId);
    }

    public EditorGridPanel(WebLocator container, String searchColumnId, int clicksToEdit) {
        this(container, searchColumnId);
        this.clicksToEdit = clicksToEdit;
    }

    public EditorGridPanel(WebLocator container, int clicksToEdit) {
        this(container);
        this.clicksToEdit = clicksToEdit;
    }

    public int getClicksToEdit() {
        return clicksToEdit;
    }

    public void setClicksToEdit(final int clicksToEdit) {
        this.clicksToEdit = clicksToEdit;
    }

    /**
     * Use only after click/doubleClicked in that row or the editor is already opened
     *
     * @return
     */
    public TextField getActiveEditor() {
        TextField editor;
        WebLocator container = new WebLocator("x-editor", this);
        WebLocator editableEl = new WebLocator(container).setCls("-focus");
        String stringClass = editableEl.getAttributeClass();
        logger.debug("stringClass: " + stringClass);
        if (stringClass == null) {
            stringClass = "";
        }
        if (stringClass.contains("x-form-field-trigger-wrap")) {
            // TODO when is DateField
            logger.debug("getActiveEditor: grid active combo x-editor");
            editor = new ComboBox();
            editor.setInfoMessage("active combo editor");
        } else if (stringClass.contains("x-form-textarea")) {
            logger.debug("TextArea");
            editor = new TextArea();
        } else {
            logger.debug("TextField");
            editor = new TextField();
        }
        editor.setContainer(this).setCls("x-form-focus").setRenderSeconds(1).setInfoMessage("active textfield editor");
//        logger.debug("editor: " + editor.getPath());
        return editor;
    }

    /**
     * Click/doubleClicked in specified cell to open the editor
     *
     * @param rowIndex
     * @param colIndex
     * @return
     */
    public boolean startEdit(int rowIndex, int colIndex) {
        logger.debug("startEdit(" + rowIndex + ", " + colIndex + ")");
        GridCell cell = getGridCell(rowIndex, colIndex);
//        cell.sendKeys(Keys.TAB);
//        return startEdit(cell);
        return prepareEdit(cell);
    }

    public boolean startEdit(String searchElement, int colIndex) {
        logger.debug("startEdit(" + searchElement + ", " + colIndex + ")");
        GridCell cell = getGridCell(searchElement, colIndex);
//        cell.sendKeys(Keys.TAB);
//        return startEdit(cell);
        return prepareEdit(cell);
    }

    private boolean prepareEdit(GridCell cell) {
        boolean selected = false;
        scrollTop();
        do {
            try {
                cell.sendKeys(Keys.TAB);
                selected = true;
            } catch (ElementNotVisibleException e) {
                logger.error("sendKeys: ElementNotVisibleException");
            }
        } while (!selected && scrollPageDown());
        return startEdit(cell);
    }

    private boolean startEdit(final GridCell cell) {
        boolean clicked = false;
        if (ready(true)) {
            if (clicksToEdit == 1) {
                clicked = cell.select();
            } else {
                clicked = cell.doubleClickAt();
            }
            if (clicked) {
                Utils.sleep(200);
            }
        }
        return clicked;
    }


    public boolean isCellEditable(int rowIndex, int colIndex) {
        if (startEdit(rowIndex, colIndex)) {
            TextField editor = getActiveEditor();
            return editor.isElementPresent();
        }
        return false;
    }

    /**
     * set row value in active editor
     *
     * @param value
     * @return
     */
    public boolean setRowValue(String value) {
        logger.debug("setRowValue(" + value + ") - in active editor");
        TextField editor = getActiveEditor();
        boolean edited = editor.setValue(value);
        if (edited) {
            editor.blur();
        }
        return edited;
    }

    public boolean appendRowValue(String value) {
        logger.debug("setRowValue(" + value + ") - in active editor");
        TextField editor = getActiveEditor();
        editor.sendKeys(Keys.END);
        editor.sendKeys(value);
        boolean edited = true;
        if (edited) {
            editor.blur();
        }
        return edited;
    }

    public boolean setRowValue(int rowIndex, int colIndex, String value) {
        logger.debug("setRowValue(" + rowIndex + ", " + colIndex + "): " + value);
        if (startEdit(rowIndex, colIndex)) {
            return setRowValue(value);
        }
        return false;
    }

    public boolean appendRowValue(int rowIndex, int colIndex, String value) {
        logger.debug("setRowValue(" + rowIndex + ", " + colIndex + "): " + value);
        if (startEdit(rowIndex, colIndex)) {
            return appendRowValue(value);
        }
        return false;
    }


    public boolean setRowValue(String searchElement, int colIndex, String value) {
        logger.debug("setRowValue(" + searchElement + ", " + colIndex + "): " + value);
        if (startEdit(searchElement, colIndex)) {
            return setRowValue(value);
        }
        return false;
    }

    public boolean appendRowValue(String searchElement, int colIndex, String value) {
        logger.debug("setRowValue(" + searchElement + ", " + colIndex + "): " + value);
        if (startEdit(searchElement, colIndex)) {
            return appendRowValue(value);
        }
        return false;
    }

    private void setCursorPosition(TextField editor, int position) {
        while (position > 0) {
            editor.sendKeys(Keys.LEFT);
            position--;
        }
    }

    public boolean deleteCharacters(int rowIndex, int colIndex, int countCharacters, int cursorPosition) {
        startEdit(rowIndex, colIndex);
        TextField editor = getActiveEditor();
        setCursorPosition(editor, cursorPosition);
        while (countCharacters > 0) {
            editor.sendKeys(Keys.DELETE);
            countCharacters--;
        }
        return editor.blur();
    }

    public boolean backspaceCharacters(int rowIndex, int colIndex, int countCharacters, int cursorPosition) {
        startEdit(rowIndex, colIndex);
        TextField editor = getActiveEditor();
        setCursorPosition(editor, cursorPosition);
        while (countCharacters > 0) {
            editor.sendKeys(Keys.BACK_SPACE);
            countCharacters--;
        }
        return editor.blur();
    }
}