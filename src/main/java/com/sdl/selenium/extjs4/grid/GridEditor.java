package com.sdl.selenium.extjs4.grid;

import com.sdl.selenium.extjs4.form.*;
import com.sdl.selenium.extjs6.grid.Grid;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridEditor extends Grid {
    private static final Logger LOGGER = LoggerFactory.getLogger(GridEditor.class);

    private WebLocator gridEditor = new WebLocator().setBaseCls("x-grid-editor").setExcludeClasses("x-hide-offsets");


    public GridEditor() {
        setClassName("Grid");
        setBaseCls("x-tree-panel");
        setTag("*");
    }

    public GridEditor(WebLocator container) {
        this();
        setContainer(container);
    }

    public TextField getActiveEditor() {
        TextField editor;
        WebLocator container = new WebLocator().setBaseCls("x-grid-editor").setExcludeClasses("x-hide-offsets");
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
        editor.setContainer(this).setClasses("x-form-focus").setRenderMillis(1000).setInfoMessage("active editor");
        return editor;
    }

    public boolean setValue(WebLocator editor, int columnIndex, String value){
        Table table = new Table(gridEditor).setPosition(columnIndex);
        editor.setContainer(table);
        return doSetValue(editor, value);
    }

    public boolean setValue(WebLocator editor, String value) {
        editor.setContainer(gridEditor);
        return doSetValue(editor, value);
    }

    private boolean doSetValue(WebLocator editor, String value) {
        boolean success = false;
        if ("ComboBox".equals(editor.getClass().getSimpleName())) {
            ComboBox ed = (ComboBox) editor;
            success = ed.select(value);
        } else if ("TextField".equals(editor.getClass().getSimpleName())) {
            TextField ed = (TextField) editor;
            success = ed.setValue(value);
        } else if ("CheckBox".equals(editor.getClass().getSimpleName())) {
            CheckBox ed = (CheckBox) editor;
            if ("true".equals(value)) {
                success = ed.isSelected() || ed.click();
            } else if ("false".equals(value)) {
                success = !ed.isSelected() || ed.click();
            }
        }else if ("CustomComboBox".equals(editor.getClass().getSimpleName())) {
            CustomComboBox ed = (CustomComboBox) editor;
            success = ed.clickIcon();
        }
        return success;
    }
}
