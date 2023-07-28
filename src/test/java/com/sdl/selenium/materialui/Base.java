package com.sdl.selenium.materialui;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.WebLocator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Base extends TestBase {

    private final WebLocator container = new WebLocator().setId("main-content");
}
