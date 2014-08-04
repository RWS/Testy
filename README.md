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
When you want to create tests using Testy you need to create a maven project with this structure:

    AutomationProject
    |--src
    |   |--main
    |   |   |--java
    |   |   `--resources
    |   `--test
    |       |--java
    |       |    `--com
    |       |        `--mycompany
    |       |            `--app
    |       |               |--tests
    |       |               |    `--FirstTest.java
    |       |               |--TestBase.java
    |       |               `--InputData.java
    |       `--resources
    |           |--drivers
    |           |   `--chromedriver.exe
    |           |--log4j.properties
    |           |--webLocator.properties
    |           |--localhost-chrome.properties
    |           |--localhost-firefox.properties
    |           `--localhost.properties
    `--pom.xml



log4j.properties contains:

    ### set log levels - for more verbose logging change 'info' to 'debug' ###
    ### debug, info, warn, error, fatal, log ###
    log4j.rootLogger=debug, stdout

    ### direct log messages to stdout ###
    log4j.appender.stdout=org.apache.log4j.ConsoleAppender
    log4j.appender.stdout.Target=System.out
    log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
    log4j.appender.stdout.layout.ConversionPattern=%d{HH:mm:ss,SSS} %-5p: %c{1} - %m%n

    ### filter log messages by package ###
    log4j.logger=info
    log4j.logger.com=debug
    log4j.logger.org=info

webLocator.properties contains:

    weblocator.log.containers=false
    weblocator.log.useClassName=false
    weblocator.log.logXPathEnabled=false
    weblocator.highlight=true

    weblocator.defaults.renderMillis=3000
    weblocator.defaults.searchType=contains

localhost.properties contains:

    browser.config=localhost-firefox.properties
    #browser.config=localhost-chrome.properties
    #browser.config=localhost-explorer.properties

    server.url=http://localhost:8080

localhost-chrome.properties contains:

    browser=chrome
    browser.driver.path=\\drivers\\chromedriver.exe
    browser.download.dir=\\temp\\

localhost-firefox.properties contains:

    browser=firefox
    browser.version=
    # run firefox.exe -p or firefox.exe -profilemanager
    browser.profile.name=default
    browser.profile.path=
    browser.binary.path=
    browser.driver.path=
    dom.max_script_run_time=500
    browser.download.folderList=2
    browser.download.manager.showWhenStarting=false
    browser.download.manager.closeWhenDone=true
    browser.download.manager.showAlertOnComplete=false
    browser.download.dir=\\temp\\
    browser.helperApps.neverAsk.openFile=text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document
    browser.helperApps.neverAsk.saveToDisk=text/csv,application/csv,text/apex,application/apex,application/pdf,application/vnd.ms-excel,application/x-xpinstall,application/x-zip,application/x-zip-compressed,application/zip,application/octet-stream,application/msword,text/plain,application/octet,application/vnd.openxmlformats-officedocument.wordprocessingml.document
    browser.helperApps.alwaysAsk.force=false
    browser.download.panel.shown=false
    security.warn_entering_secure=false
    security.warn_entering_secure.show_once=false
    security.warn_entering_weak=false
    security.warn_entering_weak.show_once=false
    security.warn_leaving_secure=false
    security.warn_leaving_secure.show_once=false
    security.warn_submit_insecure=false
    security.warn_submit_insecure.show_once=false
    security.warn_viewing_mixed=false
    security.warn_viewing_mixed.show_once=false



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