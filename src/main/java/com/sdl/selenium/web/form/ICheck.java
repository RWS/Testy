package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Clickable;
import com.sdl.selenium.web.IWebLocator;

public interface ICheck extends Clickable, IWebLocator {

    @Deprecated
    boolean isSelected();

    boolean isChecked();

    default boolean check(boolean checked) {
        return checked == isChecked() || (click() && (checked == isChecked()));
    }

    default boolean doCheck(boolean checked) {
        return checked == isChecked() || (doClick() && (checked == isChecked()));
    }
}