package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i></b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <form class="form-horizontal span3 experiment-tile">
 *      <legend>Form Title</legend>
 * </form>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * private Form form = new Form().withTitle("Form Title");
 * form.ready();
 * }</pre>
 */
public class Form extends WebLocator implements IWebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Form.class);

    public Form() {
        withClassName("Form");
        withTag("form");
        WebLocator e = new WebLocator().withTag("legend");
        withTemplateTitle(e);
    }

    public Form(WebLocator container) {
        this();
        withContainer(container);
    }

    public Form(String title) {
        this(null, title);
    }

    public Form(WebLocator container, String title) {
        this(container);
        withTitle(title, SearchType.EQUALS);
    }
}