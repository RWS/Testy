package com.sdl.selenium.web.table;

import com.sdl.selenium.web.ILocator;
import com.sdl.selenium.web.IText;

import java.util.List;

public interface IRow extends ILocator, IText {

    List<String> getCellsText(int... excludedColumns);
}
