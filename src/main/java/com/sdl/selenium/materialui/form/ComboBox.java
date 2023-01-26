package com.sdl.selenium.materialui.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComboBox extends WebLocator {

   public ComboBox() {
       setClassName("ComboBox");
   }

   public ComboBox(WebLocator container) {
       this();
       setContainer(container);
   }

   public ComboBox(WebLocator container, String text, SearchType... searchTypes) {
       this(container);
       if (searchTypes.length == 0) {
           searchTypes = new SearchType[]{SearchType.EQUALS};
       }
       setText(text, searchTypes);
   }
}
