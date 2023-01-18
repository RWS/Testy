# TODOs

## QA Requirements

- [ ] When to use //table//td[2][text()='test'] vs //table//td[text()='test'][1]?
- [ ] Use OR statements in path Builder (eg. //button[contains(concat(' ', @class, ' '), ' btn-icon ') and @data-original-title='[review]Activate' or @title='[review]Activate']
  LOGGER.debug(MessageFormat.format("{0} is {0} years old, er, young", "e45"));
- [x] Generate cssSelector instead of xpath when possible
- [ ] private WebLocator btn = WebLocator.find(By.id("ix"), By.name("xxx"));
- [ ] combo.select("item"); combo.getValue().equals("item") into select(...)


## Examples and Documentation

- [ ] use images for examples (half image with html and half with java code that represent each element)
- [ ] Comparison of Automation frameworks (including Testy)

## Release Notes


## Improvements in suggestions

- [ ] show parents elements tree (outerHTML - innerHTML)

    ERROR: WebLocatorDriverExecutor - InvalidElementStateException in doClick: Selectaţi tot
    org.openqa.selenium.ElementNotVisibleException: element not visible
    ...
      at org.openqa.selenium.remote.RemoteWebElement.click(RemoteWebElement.java:84)
      at com.sdl.selenium.web.WebLocatorDriverExecutor.tryAgainDoClick(WebLocatorDriverExecutor.java:64)
      at com.sdl.selenium.web.WebLocatorDriverExecutor.doClick(WebLocatorDriverExecutor.java:45)
      at com.sdl.selenium.web.WebLocator.click(WebLocator.java:189)

