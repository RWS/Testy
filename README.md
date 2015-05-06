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
    @Test
    public void exampleTest() {
        TextField f = new TextField().setLabel("User Name:");
        f.setValue("user@example.com");
        WebLocator container = new WebLocator().setClasses("content-full-width");
        WebLink link = new WebLink(container, "Inscrie-te acum!");
        f.assertClick();
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
        <version>1.7.15</version>
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
        <version>1.7.16-SNAPSHOT</version>
    </dependency>
```

