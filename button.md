# Button Guide (All Types)

Single documentation file for all `Button` implementations in Testy.

## Available button types

- Generic web: `src/main/java/com/sdl/selenium/web/button/Button.java`
- Bootstrap: `src/main/java/com/sdl/selenium/bootstrap/button/Button.java`
- ExtJS3: `src/main/java/com/sdl/selenium/extjs3/button/Button.java`
- ExtJS6: `src/main/java/com/sdl/selenium/extjs6/button/Button.java`
- ExtReact: `src/main/java/com/sdl/selenium/extreact/button/Button.java`
- Material UI: `src/main/java/com/sdl/selenium/materialui/button/Button.java`

## Common usage pattern

All button classes follow the same core flow:

1. Create button locator (optionally scoped in a container).
2. Filter by text and/or icon.
3. Click or use framework-specific helpers.

```java
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.Button;

WebLocator toolbar = new WebLocator().setId("actions");
Button save = new Button(toolbar, "Save");
save.click();
```

## 1) Generic Web Button

Use for plain HTML buttons.

- Tag default: `button`
- Supports `setText(...)`, `setIconCls(...)`, `new Button(WebElement)`
- Default text search in `(container, text, searchTypes...)`: `SearchType.EQUALS` when missing

```java
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.button.Button;

WebLocator dialog = new WebLocator().setId("confirm-dialog");
Button cancel = new Button(dialog, "Cancel", SearchType.EQUALS);
cancel.click();
```

## 2) Bootstrap Button

Use for Bootstrap-like markup (`btn` classes).

- Base class: `btn`
- Tag default: `button`
- `isEnabled()` checks both disabled class and disabled attribute

```java
import com.sdl.selenium.bootstrap.button.Button;
import com.sdl.selenium.web.WebLocator;

WebLocator form = new WebLocator().setId("profile-form");
Button submit = new Button(form, "Submit");
if (submit.isEnabled()) {
    submit.click();
}
```

## 3) ExtJS3 Button

Use for classic ExtJS3 components.

- Base class: `x-btn`
- Tag default: `table`
- Useful methods: `toggle(...)`, `clickWithExtJS()`, `showMenu()`, `clickOnMenu(...)`, `waitToEnable(...)`

```java
import com.sdl.selenium.extjs3.button.Button;
import com.sdl.selenium.web.WebLocator;

WebLocator toolbar = new WebLocator().setId("main-toolbar");
Button actions = new Button(toolbar, "Actions");
actions.showMenu();
actions.clickOnMenu(new String[]{"Export", "PDF"});
```

## 4) ExtJS6 Button

Use for ExtJS6 components.

- Base class: `x-btn`
- Tag default: `a`
- Default text search includes deep child/self lookup
- Useful methods: `showMenu()`, `isEnabled()`

```java
import com.sdl.selenium.extjs6.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

WebLocator toolbar = new WebLocator().setId("main-toolbar");
Button menuButton = new Button(toolbar, "Actions", SearchType.EQUALS);
boolean opened = menuButton.showMenu();
```

## 5) ExtReact Button

Use for ExtReact components.

- Base class: `x-button`
- Tag default: `div`
- Supports Ext-style `showMenu()` and `isEnabled()` checks

```java
import com.sdl.selenium.extreact.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

WebLocator panel = new WebLocator().setId("editor-panel");
Button save = new Button(panel, "Save", SearchType.EQUALS);
save.click();
```

## 6) Material UI Button

Use for Material UI buttons.

- Sets type: `button`
- `setIconCls(...)` maps to child `svg` class search
- `clickOnMenu(...)` delegates menu selection to `Menu`

```java
import com.sdl.selenium.materialui.button.Button;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;

WebLocator card = new WebLocator().setId("actions-card");
Button more = new Button(card, "More", SearchType.EQUALS);
more.clickOnMenu("Delete", SearchType.EQUALS);
```

## Practical recommendations

- Major recommendation: prefer passing locator criteria through class constructors (`new Button(container, text, ...)`) for clearer and more stable tests.
- Use setters/getters mainly in exceptional cases (dynamic data, late binding, or when constructor signature does not cover the needed scenario).
- Prefer container-scoped locators for stability.
- Start with exact text (`SearchType.EQUALS`) and relax only when needed.
- For menu buttons in ExtJS/ExtReact, ensure button has a valid `id` before using `showMenu()`.
- Use framework-specific `Button` class instead of the generic one when UI is component-based.

