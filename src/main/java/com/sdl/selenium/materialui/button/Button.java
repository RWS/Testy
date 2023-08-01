package com.sdl.selenium.materialui.button;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Button extends com.sdl.selenium.web.button.Button {

   public Button() {
       setClassName("Button");
       setType("button");
   }

   public Button(WebLocator container) {
       this();
       setContainer(container);
   }

   public Button(WebLocator container, String text, SearchType... searchTypes) {
       this(container);
       if (searchTypes.length == 0) {
           searchTypes = new SearchType[]{SearchType.EQUALS};
       }
       setText(text, searchTypes);
   }

    public <T extends Button> T setIcon(final String icon, SearchType... searchTypes) {
        WebLocator svgIcon = new WebLocator().setAttribute("data-testid", icon, searchTypes);
        setChildNodes(svgIcon);
        return (T) this;
    }
}
