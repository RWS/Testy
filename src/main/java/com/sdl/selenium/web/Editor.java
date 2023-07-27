package com.sdl.selenium.web;

import com.sdl.selenium.extjs6.form.*;
import com.sdl.selenium.web.form.Field;
import com.sdl.selenium.web.utils.RetryUtils;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;

public interface Editor {
    Logger log = org.slf4j.LoggerFactory.getLogger(Editor.class);

    WebLocator getView();

    default <T extends Field> T getEditor(WebLocator cell) {
        return RetryUtils.retry(3, () -> {
            cell.click();
            return getEditor();
        });
    }

    @SuppressWarnings("unchecked")
    default <T extends Field> T getEditor() {
        return getDoEditor(getView());
    }

    default <T extends Field> T getDoEditor(WebLocator parent) {
        Field editor;
        WebLocator container = new WebLocator(parent).setClasses("x-editor");
        WebLocator input = new WebLocator(container).setTag("input");
        if (!input.isPresent()) {
            input = new WebLocator(container).setTag("textarea");
        }
        WebLocator finalInput = input;
        String type = RetryUtils.retry(2, () -> finalInput.getAttribute("data-componentid"));
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
        editor.setContainer(parent).setRender(Duration.ofSeconds(1)).setInfoMessage("active editor");
        if (!(editor instanceof CheckBox)) {
            editor.setClasses("x-form-focus");
        }
        return (T) editor;
    }

    default boolean edit(WebLocator cell, List<String> values) {
        String value = values.get(0);
        Field editor = getEditor(cell);
        boolean edited = false;
        if (editor instanceof TextField) {
            edited = RetryUtils.retry(2, () -> {
                editor.setValue(value);
                return editor.getValue().equals(value);
            });
        } else if (editor instanceof ComboBox) {
            ComboBox comboBox = (ComboBox) editor;
            edited = RetryUtils.retry(2, () -> {
                comboBox.select(value);
                return comboBox.getValue().equals(value);
            });
        } else if (editor instanceof TagField) {
            edited = ((TagField) editor).select(values.toArray(new String[0]));
        } else {
            log.error("Is not suported!");
        }
        return edited;
    }
}