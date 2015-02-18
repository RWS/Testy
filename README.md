Testy
=====

Automated Acceptance Testing.
Selenium and Selenium WebDriver test framework for web applications.
Optimized for:
- dynamic html
- ExtJS
- Bootstrap
- complex UI
- simple web applications/sites

Usage Example
-------------


```java
    @Test
    public void exampleTest() {
        TextField f = new TextField().setLabel("User Name:");
        logger.debug(f.getPath());
        f.setValue("user@example.com");

        WebLocator container = new WebLocator().setClasses("content-full-width");
        WebLink link = new WebLink(container, "Inscrie-te acum!");
        logger.debug(link.getPath());
        f.assertClick();
    }
```

Prerequisites
-------------
- Java
- Maven

Generate local Testy.jar
------------------------

from command line run:

    mvn package

jar file will be available in /target/

Run from Intellij IDEA
----------------------

When running integration test from Intellij IDEA run :

    src\test\resources\integration\testngIntegration.xml

How to use Testy?
-----------------

Example:
When you want to create tests using Testy you need to create a maven project structure:

[Here is a full example](https://github.com/nmatei/cucumber-testy-tutorial)


Problems
--------

Manual deploy maven in local .m2 folder
1) run :
        
        mvn package
        
2) copy next files into .m2\repository\com\sdl\lt\Testy\1.7.3-SNAPSHOT\

    target/Testy-1.7.3-SNAPSHOT.jar
    target/Testy-1.7.3-SNAPSHOT-javadoc.jar
    target/Testy-1.7.3-SNAPSHOT-sources.jar
    
3) copy pom.xml to .m2\repository\com\sdl\lt\Testy\1.7.3-SNAPSHOT\Testy-1.7.3-SNAPSHOT.pom

Links
-----

http://www.w3.org/TR/1999/REC-xpath-19991116/#node-sets
https://dvcs.w3.org/hg/webdriver/raw-file/tip/webdriver-spec.html
