package com.sdl.selenium.materialui.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.form.Field;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextField extends Field {

   public TextField() {
       setClassName("TextField");
       setBaseCls("MuiInputBase-input");
       setTag("input");
   }

   public TextField(WebLocator container) {
       this();
       setContainer(container);
   }

   public TextField(WebLocator container, String label, SearchType... searchTypes) {
       this(container);
       if (searchTypes.length == 0) {
           searchTypes = new SearchType[]{SearchType.EQUALS};
       }
       setLabel(label, searchTypes);
   }

    public String getError() {
        WebLocator error = new WebLocator(this).setTag("p").setRoot("/../../").setClasses("Mui-error");
        return error.getText();
    }
}
