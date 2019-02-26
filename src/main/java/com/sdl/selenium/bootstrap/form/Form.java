package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.Locator;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

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
 * private Form form = new Form().setTitle("Form Title");
 * form.ready();
 * }</pre>
 */
public class Form extends Locator {

    public Form() {
        setClassName("Form");
        setTag("form");
        WebLocator e = new WebLocator().setTag("legend");
        setTemplateTitle(e);
    }

    public Form(Locator container) {
        this();
        setContainer(container);
    }

    public Form(String title) {
        this(null, title);
    }

    public Form(Locator container, String title) {
        this(container);
        setTitle(title, SearchType.EQUALS);
    }
}