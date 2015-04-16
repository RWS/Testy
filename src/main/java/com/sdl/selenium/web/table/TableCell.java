package com.sdl.selenium.web.table;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableCell extends Cell {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableCell.class);

    /*XPathBuilder pathBuilder = new XPathBuilder() {
        @Override
        protected String addPositionToPath(String itemPath) {
            if (hasPosition()) {
                int beginIndex = 2 + pathBuilder.getTag().length();
                itemPath = "//" + pathBuilder.getTag() + "[" + pathBuilder.getPosition() + "]" + itemPath.substring(beginIndex);
            }
            return itemPath;
        }
    };
*/
    public TableCell(By... bys) {
        getPathBuilder().defaultSearchTextType.add(SearchType.DEEP_CHILD_NODE_OR_SELF); //TODO Depinde ordinea!!!!
        getPathBuilder().defaults(By.tag("td")).init(bys);
        getPathBuilder().setTemplate("position", "//" + getPathBuilder().getTag() + "[" + getPathBuilder().getPosition() + "]");
        setRenderMillis(200);
    }

    public TableCell(WebLocator container, By... bys) {
        this(bys);
        getPathBuilder().setContainer(container);
    }

    public TableCell(WebLocator container, int columnIndex) {
        this(container, By.position(columnIndex));
    }

    public TableCell(int columnIndex, String columnText, SearchType... searchType) {
        this(By.position(columnIndex), By.text(columnText, searchType));

    }

    public TableCell(WebLocator container, int columnIndex, String columnText, SearchType... searchType) {
        this(container, By.position(columnIndex), By.text(columnText, searchType));
    }
}
