# Testy

Automated Acceptance Testing.

Selenium and Selenium WebDriver test framework for web applications.
You don't have to worry anymore about complex XPATH or CSS selector because we are taking care for you about that.
Simply set each property on the element and we will generate the necessary selector.
You can combine more properties/attributes on the same selector very easy. 

This project is optimized for:

- [x] Dynamic html & Complex UI
- [x] Sencha ExtJS
- [x] Bootstrap
- [x] Simple web applications/sites

## Java Usage Example

```html

    <button>Save</button>
    <button>Cancel</button>
    
    <div class="close" title="Close">x</div>
    <span class="minimize" title="Minimize">_</span>
    
```

```java
    
    Button saveButton   = new Button().setText("Save");
    Button cancelButton = new Button().setText("Cancel");
    
    // more properties for selecting/testing specific element with wanted attributes
    WebLocator closeIcon = new WebLocator().setClasses("close").setTitle("Close");
    WebLocator minimIcon = new WebLocator().setClasses("minimize").setTitle("Minimize");

```

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

## Table and rows examples

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

## Prerequisites

- Java
- Maven

## Getting the maven plugin

```xml
    <dependency>
        <groupId>com.sdl.lt</groupId>
        <artifactId>Testy</artifactId>
        <version>1.8.3</version>
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

## Example project

Here is a sample project with cucumber and Testy on Chrome browser:

[Full example](https://github.com/nmatei/cucumber-testy-tutorial)


## Release Notes

**Release Notes for Testy 2.0.0-SNAPSHOT**
- add weblocator.log.suggestions=false default
- add elCssSelector
- if setVisibility on WebLocator, wait visibilityOfElementLocated

**Release Notes for Testy 1.8.3**
- add weblocator.log.suggestions=true
- add setAttribute(String attribute, String value)
- add setPlaceholder(final String value) for all components which extend com.sdl.selenium.web.form.TextField
- update webdriver version 2.48.2
- improvement reuse download an upload code
- make public doClickAt, doClick, doMouseOver
- add doClear
- add getTagName, getLocation, getSize

[Detailed Release Notes](./release-notes.md) 

### Getting SNAPSHOT versions of the plugin

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
        <version>2.0.0-SNAPSHOT</version>
    </dependency>
```

## Demo application links

- [Login](https://rawgit.com/sdl/Testy/master/src/test/functional/app-demo/login.html)
- [Bootstrap](https://rawgit.com/sdl/Testy/master/src/test/functional/app-demo/bootstrap/index.html)
- [Sencha ExtJs](https://rawgit.com/sdl/Testy/master/src/test/functional/app-demo/extjs/index.html)

