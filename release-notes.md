RELEASE NOTES
-------------
**Release Notes for Testy 2.14.0-SNAPSHOT**
- update webdriver version 4.1.0
- improvement select(boolean doScroll, String... nodes) method in Tree
- improvement doRetryIfNotSame(V expected, Callable<V> call) method in RetryUtils
- migrate from slf4j-log4j12 to log4j-api
- make scrollable interface works with locked grid
- improvement Row(WebLocator grid, AbstractCell... cells) method for locked grid
- improvement MessageBox.java
- added getRow(boolean size, Cell... byCells) method for locked grid
- improvement locked grid
- improvement Row(WebLocator grid, AbstractCell... cells) method for locked grid
- add setFinalXPath(final String finalXPath)

**Release Notes for Testy 2.13.1**
- added setChildNodes(SearchType searchType, final WebLocator... childNodes) method
- improvement select(boolean doScroll, String... nodes) method in Tree
- update webdriver version 3.141.59
- TODO

**Release Notes for Testy 2.12.0**
- TODO

**Release Notes for Testy 2.11.0**
- added setHeaders(boolean strictPosition, final String... headers) method
- added selectAll() method in Grid
- improvement retryIfNotSame(...) accept integer parameter
- improvement getCellsText()
- chrome use --use-simple-cache-backend
- improvement Panel
- getParent correct in scrollInGrid(Row row) method
- added expand and collapse methods in ICombo interface
- added setIconCls () method in Tab
- improvement Tab
- added FieldContainer
- update webdriver version 3.14.0
- use lombok
- added getCellsText(String group, int... excludedColumns) method
- improvement setDate from DateField
- setSearch{*}Type methods overate with current value
- added Group class
- click method is with retry
- added RetryUtils class
- added doRemove in TagField
- added waitToRender(final long millis, boolean showXPathLog) method in IWebLocator
- improvement Scrollable
- fix for SearchType.CONTAINS_ALL_CHILD_NODES
- fix FieldSet
- setTemplateValue accept array
- ConditionManager is with Duration
- fix download with headless on linux
- update webdriver version 3.9.0

**Release Notes for Testy 2.10.0**
- update webdriver version 3.9.0
- update webdriver version 3.9.1
- improvement scrollPageDownInTree() method in Scrollable interface
- improvement getCellsText(int... excludedColumns) in Grid with scroll
- improvement collapse method on Panel 
- improvement ElementRemovedSuccessCondition, no show log if element wasn't present 
- update webdriver version 3.10.0
- update webdriver version 3.11.0

**Release Notes for Testy 2.9.0**
- update webdriver version 3.8.0
- update webdriver version 3.8.1

**Release Notes for Testy 2.8.0**
- update webdriver version 3.7.0
- improvement select method from Tree
- improvement ComboBox

**Release Notes for Testy 2.7.0**
- update webdriver version 3.6.0
- improvement FirefoxConfigReader with geckodriver.exe
- improvement ChromeConfigReader
- added isEnabled method
- improvement ComboBox class
- create Scrollable interface
- improvement getCellsText(int... excludedColumns) for Table and Grid

**Release Notes for Testy 2.6.2**
- update webdriver version 3.5.1
- improvement getCellsText method from Grid
- improvement Tree
- improvement TagField

**Release Notes for Testy 2.5.0**
- update webdriver version 3.4.0
- added public Tab(String title, SearchType... searchTypes) method in Tab
- added waitToRender(final long millis, boolean showXPathLog)
- added ToolTip in extjs6 package
- added setHeaders(final String... headers) in Grid
- delete temporary file automatically when driver is close
- add Cell and Row in extjs6 package

**Release Notes for Testy 2.4.0**
- update webdriver version 3.3.1
- add FieldSet for extjs 6 package
- add check(Cell... cells) and unCheck(Cell... cells) in Grid and Table
- check(Cell... cells) and unCheck(Cell... cells) doing scrollButton if row is not visible in Grid
- added method scrollToWebLocator(WebLocator element) in Utils
- added  method getError for TextField in extjs 6 package
- remove deprecated methods
- added select(Row row) and unSelect(Row row) in Grid
- added Cell(String columnText, SearchType... searchTypes) method in Cell

**Release Notes for Testy 2.3.0**
- update webdriver version 3.0.1
- make deprecated methods with***()
- implement check and unCheck methods in Grid and Table
- add TagField
- implement setValue() in TagField
- add Tree
- add select(String value, boolean pagination) in ComboBox

**Release Notes for Testy 2.2.10**
- change set***() in with***()
    ex: WebLocator el = new WebLocator().setClasses("x-btn").setText("Save").setType("text");
        WebLocator el = new WebLocator().withClasses("x-btn").withText("Save").withType("text");
-  Add possibility to set in webLocator.properties: upload.exe.path=src\\test\\resources\\upload\\upload.exe
    ex: UploadFile uploadButton = new UploadFile().withText("Browse");
        uploadButton.upload(InputData.RESOURCES_DIRECTORY_PATH + "\\upload\\text.docx");
- added support for using system clipboard in several WebDriver instances in parallel without mixing copied values
- implemented all methods with assert
- added fileDetector to RemoteWebDrivers
- findElements support cssSelector
- added getRect()

**Release Notes for Testy 2.1.0**
- use attributes when generate toString, in case no other important string is present
- add support for css selector when setExcludeClasses
- update webdriver version 2.52.0
- add log at sendKeys method
- add possibility to overwritten value from webLocator.properties in browser.properties
- change getHtmlText() in getText()
  ex: WebLocator el = new WebLocator().setClasses("x-btn");
      el.getHtmlText(); - make deprecated;
      el.getText(); - use this;
- update webdriver version 2.53.0

**Release Notes for Testy 2.0.0**
- Change config default value for weblocator.log.suggestions=false
- Generate cssSelector instead of xpath when possible. Use config weblocator.generateCssSelector=false
- Add locator.setElCssSelector("#selector.element-css")
- Improve ElementNotVisibleException when use locator.setVisibility(true) - wait.until(visibilityOfElementLocated).
- Classes TableCell and TableRow made deprecated - use Cell & Row instead
- public String[] gridPanel.getRow have been removed 
- locator.isTextPresent made deprecated
- locator.getPath made deprecated
- add addSearchTextType(SearchType... searchTextType)
- add SearchType.CASE_INSENSITIVE and SearchType.CASE_SENSITIVE
- add SearchType.NO_TRIM
- made deprecated doClick, doClickAt, doSendKeys, getHtmlSource, getHtmlSource(el), doMouseOver and doHighlight from WebLocatorExecutor

**Release Notes for Testy 1.8.3**
- add weblocator.log.suggestions=true
- add setAttribute(String attribute, String value)
- add setPlaceholder(final String value) for all components which extend com.sdl.selenium.web.form.TextField
- update webdriver version 2.48.2
- improvement reuse download an upload code
- make public doClickAt, doClick, doMouseOver
- add doClear
- add getTagName, getLocation, getSize

**Release Notes for Testy 1.8.2**
- add isDisplayed method in WebLocator
- fix properties.load from inputStream correctly
- add template for title
- add try and catch for WebDriverException at doClick and submit
- add getCssValue(String propertyName) method in WebLocator
- add profile.preference.dom.disable_beforeunload = true for Firefox
- add setTemplateTitle(WebLocator titleEl)
- setTitle(final String title, SearchType ...searchTypes) accept SearchType
- update webdriver version 2.47.0

**Release Notes for Testy 1.8.1**
- add isDisplayed, isEnabled and submit method in WebLocatorDriverExecutor

**Release Notes for Testy 1.8.0**
- remove deprecated classes and methods
- remove gets deprecated methods from WebLocatorAbstractBuilder

**Release Notes for Testy 1.7.17**
- create XPathBuilder
- make gets deprecated methods from WebLocatorAbstractBuilder
- change when use in code deprecated methods
- add new property weblocator.min.chars.toType=0 in webLocator.properties
- add resultIdx

**Release Notes for Testy 1.7.16**
- remove deprecated classes and methods (before update to this version make sure you update to Testy 1.7.15 first to see what classes/methods need to migrate)
- rename getTableCell(int columnIndex, TableCell... byCells) in getCell(int columnIndex, TableCell... byCells) for table and GridPanel

**Release Notes for Testy 1.7.15**
- Organize classes packages (mark package com.sdl.bootstrap.* deprecated, and other classes and methods)

**Release Notes for Testy 1.7.8-SNAPSHOT**
- improvement WebDriverConfig.getWebDriver(...);

**Release Notes for Testy 1.7.7-SNAPSHOT**
- webdriver version 2.44.0 supported ff 33

**Release Notes for Testy 1.7.6-SNAPSHOT**
- webdriver version 2.43.0 supported ff 31, 32

**Release Notes for Testy 1.7.5-SNAPSHOT**
- webdriver version 2.42.2
- fix TableCell accept visibility
- add Tooltip class
- fix slow bug
- improvement upload.exe

**Release Notes for Testy 1.7.3-SNAPSHOT**
- revert void fireEventWithJS
- remove unused Utils.fixPathSelector
- improvement Row with tableCell.setTag("th")
- improvement TableCell accept array of SearchTypes

**Release Notes for Testy 1.7.2-SNAPSHOT**
- webdriver version 2.41
- move getDriver in WebDriverConfig
- remove deprecated class and methods
- assertDownload works with localhost-firefox.properties
- fix MultipleSelect path
- sendKeys return null if element doesn't ready;
- move assertDownload(String filePath) in download(String filePath)
- add Utils.waitFileIfIsEmpty(File file), Wait 5 seconds.
- move Utils from com.extjs.selenium.Utils to com.sdl.selenium.web.utils.Utils;
- create FileUtils
- convert to boolean fireEventWithJS
- add weblocator.log.logXPath=false and weblocator.defaults.searchType=contains
- has make protected
- add MultiSelect class

**Release Notes for Testy 1.7.1**
- improvement waitToRender() with WebDriverWait
- fixed when Button text or Label of TextField contains quotes.
- add constructor with container for all elements
- change setName("name") from contains(@name,'name') in @name='name'
- improvement Utils.getEscapeQuotesText()
- improvement getBasePathSelector()
- implemented type attribute for SimpleTextField.java
- rename Span in UneditableInput
- add WebLink class
- improvement com.sdl.selenium.extjs3.button.Button.click()
- rename attribute  excludeCls in excludeClasses list
- rename attribute deepness in elPathSuffix
- move specification Browser in WebDriverConfig
- remove methods type(String text) and typeKeys(String text) from WebLocator
- add interface ITextField
- move copyToClipboard in Utils
- mark setCls(final String cls) and getCls() Deprecate
- improvement maximize method from Panel
- add try in executeScript. findElement should not log "Element not found after" when element is not found.
- add tests to WebLocator for toString method : shouldShowClassInToStringWhenHasOneClass and shouldShowClassesInToStringWhenHasManyClass
- improvement getItemPathText with normalize-space
- add InputButton class
- change Boolean in boolean into WebLocatorAbstractBuilder
- rename setSearchTextType in setSearchTextTypes witch accept list
- improvement getItemPathText() for cases where the item has multiple enters
- add SearchType enum TRIM, CHILD_NODE, DEEP_CHILD_NODE
- update setText(final String text, final SearchType ...searchTypes) with SearchType options

**Release Notes for Testy 1.6**
- Improvement setValue for TextField
- Add method isSamePath()
- Add logger.debug("currentElement already found one time: " + el); in method findElement()
- Add setter and getter for currentElementPath
- Verifica daca elemenetul exista cand a aparut exceptia StaleElementReferenceException si apoi face din nou click
- Add more info about elements into intemToString
- remove upload(String[] filePath, String uploadWindowName)
- Add doSendKeys method
- Improvement update.exe
- use setter com.sdl.bootstrap.button.UploadFile
