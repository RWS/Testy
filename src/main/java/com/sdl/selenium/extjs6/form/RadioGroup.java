package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

public class RadioGroup extends WebLocator {

    private Radio radio = new Radio(this);

    public RadioGroup() {
        setClassName("RadioGroup");
        setBaseCls("x-form-checkboxgroup");
    }

    public RadioGroup(WebLocator container) {
        this();
        setContainer(container);
    }

    public RadioGroup(WebLocator container, String label, SearchType... searchTypes) {
        this(container);
//        List<SearchType> search = Arrays.asList(searchTypes);
//        Collections.addAll(search, SearchType.DEEP_CHILD_NODE_OR_SELF);
//        SearchType[] types = search.toArray(new SearchType[search.size()]);
        WebLocator labelEl = new WebLocator().setTag("label").setText(label, searchTypes);
        setChildNodes(labelEl);
    }

    public boolean selectByLabel(String label) {
        return selectByLabel(label, SearchType.EQUALS);
    }

    public boolean selectByLabel(String label, SearchType... searchType) {
        radio.setLabel(label, searchType);
        boolean selected = radio.isSelected() || radio.click();
        radio.setLabel(null);
        return selected;
    }

    public boolean isSelectedByLabel(String label, SearchType... searchType) {
        radio.setLabel(label, searchType);
        boolean selected = radio.isSelected();
        radio.setLabel(null);
        return selected;
    }

    public String getLabelName(String label) {
        WebLocator locator = new WebLocator(radio).setElPath("/following-sibling::label[contains(text(),'" + label + "')]");
        return locator.getText();
    }

//    public boolean isDisabled() {
//        WebLocator locator = new WebLocator().setElPath(radio.getXPath(true));
//        return locator.exists();
//    }
}