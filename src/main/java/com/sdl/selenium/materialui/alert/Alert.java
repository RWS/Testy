package com.sdl.selenium.materialui.alert;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Alert extends WebLocator {

   public Alert() {
       setClassName("Alert");
       setBaseCls("MuiAlert-root");
   }

   public Alert(String message, SearchType... searchTypes) {
       this();
       if (searchTypes.length == 0) {
           searchTypes = new SearchType[]{SearchType.EQUALS, SearchType.DEEP_CHILD_NODE_OR_SELF};
       }
       setText(message, searchTypes);
   }
}
