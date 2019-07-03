package com.sdl.selenium.web.form;

import com.sdl.selenium.web.Editable;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.SearchType;

public interface IField extends IWebLocator, Editable {

    <T extends IField> T setPlaceholder(final String value, SearchType... searchTypes);
}