package com.sdl.selenium.extjs3.grid;

import com.sdl.selenium.extjs3.form.ComboBox;
import com.sdl.selenium.extjs3.form.TextArea;
import com.sdl.selenium.extjs3.form.TextField;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class EditorGridPanel extends GridPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditorGridPanel.class);

    private int clicksToEdit = 2;

    public EditorGridPanel() {
        setClassName("EditorGridPanel");
    }

    public EditorGridPanel(WebLocator container) {
        this();
        setContainer(container);
    }

    public EditorGridPanel(String cls) {
        this();
        setClasses(cls);
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
     * @return active editor
     */
    public TextField getActiveEditor() {
        TextField editor;
        WebLocator container = new WebLocator("x-editor", this);
        WebLocator editableEl = new WebLocator(container).setElPath("//*[contains(@class, '-focus')]");
        String stringClass = editableEl.getAttributeClass();
        LOGGER.debug("active editor stringClass: " + stringClass);
        if (stringClass == null) {
            LOGGER.warn("active editor stringClass is null: " + editableEl); // TODO investigate this problem
            stringClass = "";
        }
        if (stringClass.contains("x-form-field-trigger-wrap")) {
            // TODO when is DateField
            LOGGER.debug("active editor is ComboBox");
            editor = new ComboBox();
            editor.setInfoMessage("active combo editor");
        } else if (stringClass.contains("x-form-textarea")) {
            LOGGER.debug("active editor is TextArea");
            editor = new TextArea();
        } else {
            LOGGER.debug("active editor is TextField");
            editor = new TextField();
        }
        editor.setContainer(this).setClasses("x-form-focus").setRender(Duration.ofSeconds(1)).setInfoMessage("active editor");
        return editor;
    }

    /**
     * Click/doubleClicked in specified cell to open the editor
     *
     * @param rowIndex rowIndex
     * @param colIndex colIndex
     * @return true or false
     */
    public boolean startEdit(int rowIndex, int colIndex) {
        GridCell cell = getCell(rowIndex, colIndex);
        return prepareEdit(cell);
    }

    public boolean startEdit(int rowIndex, int colIndex, long milliseconds) {
        GridCell cell = getCell(rowIndex, colIndex);
        return prepareEdit(cell, milliseconds);
    }

    public boolean startEdit(String searchElement, int colIndex) {
        GridCell cell = getCell(colIndex, new GridCell(searchElement, SearchType.EQUALS));
        return prepareEdit(cell);
    }

    private boolean prepareEdit(GridCell cell) {
        boolean selected;
        scrollTop();
        do {
            selected = cell.sendKeys(Keys.TAB) != null;
        } while (!selected && scrollPageDown());
        return startEdit(cell);
    }

    private boolean prepareEdit(GridCell cell, long milliseconds) {
        boolean selected;
        scrollTop();
        do {
            selected = cell.sendKeys(Keys.TAB) != null;
        } while (!selected && scrollPageDown());
        return startEdit(cell, milliseconds);
    }

    private boolean startEdit(final GridCell cell) {
        return startEdit(cell, 200);
    }

    private boolean startEdit(final GridCell cell, long milliseconds) {
        boolean clicked = false;
        if (ready(true)) {
            if (clicksToEdit == 1) {
                clicked = cell.select();
            } else {
                clicked = cell.doubleClickAt();
            }
            if (clicked) {
                Utils.sleep(milliseconds);
                LOGGER.debug("startEdit(" + cell + ")");
            }
        }
        return clicked;
    }


    public boolean isCellEditable(int rowIndex, int colIndex) {
        if (startEdit(rowIndex, colIndex)) {
            TextField editor = getActiveEditor();
            return editor.isPresent();
        }
        return false;
    }

    /**
     * set row value in active editor
     *
     * @param value value
     * @return true or false
     */
    public boolean setRowValue(String value) {
        LOGGER.debug("setRowValue(" + value + ") - in active editor");
        TextField editor = getActiveEditor();
        boolean edited = editor.setValue(value);
        if (edited) {
            editor.doBlur();
        }
        return edited;
    }

    /**
     * set row value in active editor
     *
     * @param value value
     * @return true or false
     */
    public boolean pasteRowValue(String value) {
        LOGGER.debug("pasteRowValue(" + value + ") - in active editor");
        TextField editor = getActiveEditor();
        boolean edited;
        if (editor instanceof ComboBox) {
            edited = editor.setValue(value);
        } else {
            edited = editor.pasteInValue(value);
        }
        if (edited) {
            editor.blur();
        }
        return edited;
    }

    public boolean appendRowValue(String value) {
        TextField editor = getActiveEditor();
        editor.sendKeys(Keys.END);
        editor.sendKeys(value);
        editor.blur();
        LOGGER.debug("appendRowValue(" + value + ") - in active editor");
        return true;
    }

    public boolean setRowValue(int rowIndex, int colIndex, String value) {
        boolean success = startEdit(rowIndex, colIndex) && setRowValue(value);
        LOGGER.debug("setRowValue(" + rowIndex + ", " + colIndex + "): " + value);
        return success;
    }

    public boolean setRowValueSafe(int rowIndex, int colIndex, String value) {
        return setRowValue(rowIndex, colIndex, value) || setRowValue(rowIndex, colIndex, value);
    }

    public boolean appendRowValue(int rowIndex, int colIndex, String value) {
        boolean success = startEdit(rowIndex, colIndex) && appendRowValue(value);
        LOGGER.debug("appendRowValue(" + rowIndex + ", " + colIndex + "): " + value);
        return success;
    }

    public boolean setRowValue(String searchElement, int colIndex, String value) {
        boolean success = startEdit(searchElement, colIndex) && setRowValue(value);
        LOGGER.debug("setRowValue(" + searchElement + ", " + colIndex + "): " + value);
        return success;
    }

    public boolean appendRowValue(String searchElement, int colIndex, String value) {
        boolean success = startEdit(searchElement, colIndex) && appendRowValue(value);
        LOGGER.debug("appendRowValue(" + searchElement + ", " + colIndex + "): " + value);
        return success;
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