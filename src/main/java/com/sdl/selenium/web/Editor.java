package com.sdl.selenium.web;

import com.sdl.selenium.extjs6.form.*;
import com.sdl.selenium.extjs6.grid.Grid;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.utils.RetryUtils;
import org.slf4j.Logger;

import java.time.Duration;

public interface Editor {
    Logger log = org.slf4j.LoggerFactory.getLogger(Grid.class);

    WebLocator getView();

    default <T extends Field> T getEditor(WebLocator cell) {
        return RetryUtils.retry(3, () -> {
            cell.click();
            return getEditor();
        });
    }

    @SuppressWarnings("unchecked")
    default <T extends Field> T getEditor() {
        Field editor;
        WebLocator container = new WebLocator("x-editor", getView());
        WebLocator editableEl = new WebLocator(container).setTag("input");
        if (!editableEl.isPresent()) {
            editableEl = new WebLocator(container).setTag("textarea");
        }
        WebLocator finalEditableEl = editableEl;
        String type = RetryUtils.retry(2, () -> finalEditableEl.getAttribute("data-componentid"));
        if (type == null) {
            log.error("active editor type: 'null'");
            return null;
        } else {
            if (type.contains("combo")) {
                editor = new ComboBox();
            } else if (type.contains("textarea")) {
                editor = new TextArea();
            } else if (type.contains("datefield")) {
                editor = new DateField();
            } else if (type.contains("tag")) {
                editor = new TagField();
            } else if (type.contains("checkbox")) {
                editor = new CheckBox();
            } else if (type.contains("numberfield") || type.contains("textfield")) {
                editor = new TextField();
            } else {
                log.warn("active editor type: {}", type);
                return null;
            }
        }
        editor.setContainer(getView()).setRender(Duration.ofSeconds(1)).setInfoMessage("active editor");
        if (!(editor instanceof CheckBox)) {
            editor.setClasses("x-form-focus");
        }
        return (T) editor;
    }
}