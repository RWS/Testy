package com.sdl.selenium.extjs6.form;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.extjs6.panel.Panel;
import com.sdl.selenium.web.WebLocator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CheckBoxIntegrationTest extends TestBase {

    private final Panel panel = new Panel(null, "Form Fields");
    private final CheckBox checkBox = new CheckBox(panel, "Checkbox:") {
        public boolean isChecked() {
            String ver = getVersion();
            if ("6.7.0".equals(ver) || "6.6.0".equals(ver) || "7.6.0".equals(ver) || "7.7.0".equals(ver)) {
                WebLocator ancestor = this.ancestor().setClasses("x-field");
                String aClass = ancestor.getAttributeClass();
                return aClass != null && aClass.contains("x-form-cb-checked");
            } else {
                WebLocator el = new WebLocator(this).setElPath("/../input");
                String select = el.getAttribute("aria-checked");
                return select != null && select.contains("true");
            }
        }
    };
    private final CheckBox boxLabel = new CheckBox("box label", panel) {
        public boolean isChecked() {
            String ver = getVersion();
            if ("6.7.0".equals(ver) || "6.6.0".equals(ver) || "7.6.0".equals(ver) || "7.7.0".equals(ver)) {
                WebLocator ancestor = this.ancestor().setClasses("x-field");
                String aClass = ancestor.getAttributeClass();
                return aClass != null && aClass.contains("x-form-cb-checked");
            } else {
                WebLocator el = new WebLocator(this).setElPath("/../input");
                String select = el.getAttribute("aria-checked");
                return select != null && select.contains("true");
            }
        }
    };

    @BeforeClass
    public void startTest() {
        checkBox.setVersion(version);
        boxLabel.setVersion(version);
        openEXTJSUrl("#form-fieldtypes", checkBox);
        checkBox.getValue();
    }

    @Test
    public void checkedTest() {
        boolean check = boxLabel.check(true);
        assertThat(check, is(true));
    }

    @Test(dependsOnMethods = "checkedTest")
    public void unCheckedTest() {
        assertThat(boxLabel.check(false), is(true));
    }

    @Test(dependsOnMethods = "unCheckedTest")
    public void checkedTest1() {
        boolean check = checkBox.check(true);
        assertThat(check, is(true));
    }

    @Test(dependsOnMethods = "checkedTest1")
    public void unChecked1Test() {
        assertThat(checkBox.check(false), is(true));
    }
}