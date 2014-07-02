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

Sa presupunem ca doriti sa faceti teste automate pe o aplicatie web folosind framework-ul Testy.

Creiem un proiect java cu structura maven:

AutomationProject
    -src
        -main
            -java
            -resources
        -test
            -java
            -resources
    -pom.xml
