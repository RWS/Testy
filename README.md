Testy
=====

Automated Acceptance Testing: Selenium and Selenium WebDriver test framework for web applications (best suited for ExtJS and complex UI)

mvn deploy:deploy-file -DgroupId=com.sdl.lt.dev -DartifactId=Testy -Dversion=1.3-SNAPSHOT -Dpackaging=jar -Dfile="target/Testy-1.3-SNAPSHOT.jar" -Durl=file:../mavenrepo/snapshots

If runging integration test from Intelij run src\test\resources\integration\testngIntegration.xml

CHANGES:
1.6-SNAPSHOT
1) Improvement setValue for TextField
2) Add method isSamePath()
3) Add logger.debug("currentElement already found one time: " + el); in method findElement()
4) Add setter and getter for currentElementPath
5) Verifica daca elemenetul exista cand a aparut exceptia StaleElementReferenceException si apoi face din nou click

1.5-SNAPSHOT

