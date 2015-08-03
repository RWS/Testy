Testy
=====

Automated Acceptance Testing.
Selenium and Selenium WebDriver test framework for web applications.
This project is optimized for:
- dynamic html
- ExtJS
- Bootstrap
- complex UI
- simple web applications/sites

Java Usage Example
------------------

```java

    public class SubscribePage {
        private WebLocator header = new WebLocator().setClasses("header");
        private TextField emailField = new TextField().setLabel("Email");
        private WebLink subscribeLink = new WebLink(header, "Subscribe now");
     
        public void subscribe(String email) {
            emailField.setValue(email);
            subscribeLink.assertClick();
        }
    }
    
    public class SubscribeTest extends TestBase {
        SubscribePage subscribePage = new SubscribePage();
     
        @Test
        public void subscribeTest() {
            subscribePage.subscribe("me@testy.com");
        }
    }
```

Table and rows examples
-----------------------

```java

    public class SubscribersPage {
        private Table table = new Table();
        
        public boolean unsubscribe(String email) {
            // find row that contains specified email in second column
            TableRow row = table.getRow(
                   new TableCell(2, email)
            );
            // find remove button inside specified row
            Button removeButton = new Button(row, "Remove");
            return removeButton.click();
        }
    }
    
    public class RemoveSubscriberTest extends TestBase {
        SubscribersPage subscribersPage = new SubscribersPage();
     
        @Test
        public void unsubscribeTest() {
            boolean removed = subscribersPage.unsubscribe("me@testy.com");
            //... assert
        }
    }
```

Prerequisites
-------------
- Java
- Maven

Getting the maven plugin
------------------------

```xml
    <dependency>
        <groupId>com.sdl.lt</groupId>
        <artifactId>Testy</artifactId>
        <version>1.8.2</version>
    </dependency>
```

[Here is how these lines appear in a project pom.xml](https://github.com/nmatei/cucumber-testy-tutorial/blob/master/pom.xml)

And you need to instantiate the WebDriver with Testy as follows:

```java
    public static WebDriver driver;
    static {
        startSuite();
    }
    private static void startSuite() {
        try {
            driver = WebDriverConfig.getWebDriver(Browser.FIREFOX);
        } catch (Exception e) {
            LOGGER.error("Exception when start suite", e);
        }
    }
```

[Here is how these lines appear in a project](https://github.com/nmatei/cucumber-testy-tutorial/blob/master/src/test/java/org/fasttrackit/util/TestBase.java)

Example project
---------------

Here is a sample project with cucumber and Testy on Chrome browser:

[Full example](https://github.com/nmatei/cucumber-testy-tutorial)


Release Notes
-------------

**Release Notes for Testy 1.8.3-SNAPSHOT**


**Release Notes for Testy 1.8.2**
- add isDisplayed method in WebLocator
- fix properties.load from inputStream correctly
- add template for title
- add try and catch for WebDriverException at doClick and submit
- add getCssValue(String propertyName) method in WebLocator
- add profile.preference.dom.disable_beforeunload = true for Firefox
- add setTemplateTitle(WebLocator titleEl)
- setTitle(final String title, SearchType ...searchType) accept SearchType
- update webdriver version 2.47.0

[Detailed Release Notes](./release-notes.md) 

Getting SNAPSHOT versions of the plugin
---------------------------------------

```xml
    <repositories>
        <repository>
            <id>sonatype-nexus-snapshots</id>
            <name>sonatype-nexus-snapshots</name>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        </repository>
    </repositories>
    <dependency>
        <groupId>com.sdl.lt</groupId>
        <artifactId>Testy</artifactId>
        <version>1.8.3-SNAPSHOT</version>
    </dependency>
```

