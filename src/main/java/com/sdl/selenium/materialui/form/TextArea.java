package com.sdl.selenium.materialui.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextArea extends TextField {

   public TextArea() {
       setClassName("TextArea");
       setTag("textarea");
       setBaseCls("MuiInputBase-inputMultiline");
       setAttribute("aria-hidden", "true", SearchType.NOT);
   }

   public TextArea(WebLocator container) {
       this();
       setContainer(container);
   }

   public TextArea(WebLocator container, String label, SearchType... searchTypes) {
       this(container);
       if (searchTypes.length == 0) {
           searchTypes = new SearchType[]{SearchType.EQUALS};
       }
       setLabel(label, searchTypes);
   }
}
