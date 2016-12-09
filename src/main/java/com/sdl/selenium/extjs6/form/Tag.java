package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

/**
 * @deprecated please use {@link TagField}
 */
@Deprecated
public class Tag extends TagField {
    private static final Logger LOGGER = Logger.getLogger(Tag.class);

    /**
     * @deprecated please use {@link TagField}
     */
    @Deprecated
    public Tag() {
        super();
    }

    /**
     * @deprecated please use {@link TagField}
     */
    @Deprecated
    public Tag(WebLocator container) {
        super(container);
    }

    /**
     * @deprecated please use {@link TagField}
     */
    @Deprecated
    public Tag(WebLocator container, String label) {
        super(container, label);
    }
}
