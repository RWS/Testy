package com.sdl.selenium.web.table;

import com.google.common.base.Strings;
import com.sdl.selenium.web.WebLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IHeaders extends IColumns {

    WebLocator getView();

    /**
     * Extracts headers from the component.
     *
     * @return List of headers as Strings, or an empty list if no headers exist
     */
    default List<String> getHeaders() {
        WebLocator body = new WebLocator(getView()).setClasses("x-grid-header-ct").setExcludeClasses("x-grid-header-ct-hidden").setResultIdx(1);
        WebLocator header = new WebLocator(body).setClasses("x-column-header");
        int size = header.size();
        List<String> headers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            header.setResultIdx(i);
            headers.add(header.getText());
        }
        return headers.stream().filter(i -> !Strings.isNullOrEmpty(i.trim())).toList();
    }

    /**
     * Extracts headers from the component.
     *
     * @return List of headers as Strings, or an empty list if no headers exist
     */
    default List<String> getHeadersFast() {
        WebLocator body = new WebLocator(getView()).setClasses("x-grid-header-ct").setExcludeClasses("x-grid-header-ct-hidden").setResultIdx(1);
        List<String> headers = new ArrayList<>();
        if (!Strings.isNullOrEmpty(body.getText())) {
            headers.addAll(Arrays.asList(body.getText().split("\\n")));
        }
        return headers;
    }
}