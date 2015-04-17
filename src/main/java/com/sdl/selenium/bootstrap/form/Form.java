package com.sdl.selenium.bootstrap.form;

import com.sdl.selenium.web.By;
import com.sdl.selenium.web.IWebLocator;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p><b><i>Used for finding element process (to generate xpath address)</i><b></p>
 * <p>Example:</p>
 * <pre>{@code
 * <form class="form-horizontal span3 experiment-tile">
 *      <legend>Form Title</legend>
 * </form>
 * }</pre>
 * <p>In Java write this:</p>
 * <pre>{@code
 * private Form form = new Form(By.title("Form Title"));
 * form.ready();
 * }</pre>
 */
public class Form extends WebLocator implements IWebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Form.class);

    public Form(By ...bys) {
        getPathBuilder().defaults(By.tag("form"), By.template("title", "count(.//legend[text()='%s']) > 0")).init(bys);
    }

    public Form(WebLocator container) {
        this(By.container(container));
    }

    public Form(WebLocator container, String title) {
        this(By.container(container), By.title(title));
    }
}