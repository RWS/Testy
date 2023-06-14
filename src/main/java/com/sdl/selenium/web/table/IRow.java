package com.sdl.selenium.web.table;

import com.sdl.selenium.web.IText;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.Transform;

public interface IRow extends IWebLocator, IText, Transform, IColumns {

    ICell getCell(int columnIndex);

    int getCells();
}