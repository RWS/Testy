package com.sdl.selenium.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.text.WordUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
public class GenerateUtils {
    private final String name;
    private final String packageName;

    public GenerateUtils(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
    }

    public String getPackageNameFormat() {
        return WordUtils.capitalize(packageName);
    }

    public String getNameFormat() {
        String result;
        switch (name) {
            case "checkbox":
                result = "CheckBox";
                break;
            case "combobox":
                result = "ComboBox";
                break;
            case "button":
                result = WordUtils.capitalize(name);
                break;
            case "togglebutton":
                result = "ToggleButton";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    public String getPackageFormat() {
        String result;
        switch (name) {
            case "checkbox":
            case "combobox":
                result = "form";
                break;
            case "button":
            case "togglebutton":
                result = "button";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    @SneakyThrows
    public Path createPackageAndFeatureFile() {
        Path directoryPath = Paths.get(new File("src/test/resources").getAbsolutePath(), "features", packageName);
        Path featureFile = Paths.get(String.valueOf(directoryPath), name + ".feature");
        if (!featureFile.toFile().exists()) {
            if (!directoryPath.toFile().exists()) {
                Files.createDirectories(directoryPath);
            }
            createFeatureFile(featureFile);
        }
        return featureFile;
    }

    @SneakyThrows
    private Path createFeatureFile(Path path) {
        Path filePath = Files.createFile(path);
        List<String> rows = List.of(
                "Feature: " + getNameFormat() + "\n",
                "  Scenario: Start",
                "    Given I open " + getPackageNameFormat() + " app"
        );
        Files.write(filePath, rows, StandardOpenOption.CREATE);
        return filePath;
    }

    @SneakyThrows
    public Path createRunner(Path featureFile) {
        Path namePath = Paths.get(new File("src/test").getAbsolutePath(), "java", "com", "sdl", "selenium", packageName, getPackageFormat());
        if (!namePath.toFile().exists()) {
            Files.createDirectories(namePath);
        }
        String nameRunner = getNameFormat() + "Runner";
        Path runnerPath = Paths.get(String.valueOf(namePath), nameRunner + ".java");
        List<String> rows = List.of(
                "package com.sdl.selenium." + getPackageName() + "." + getPackageFormat() + ";\n",
                "import io.cucumber.junit.Cucumber;",
                "import io.cucumber.junit.CucumberOptions;",
                "import org.junit.runner.RunWith;\n",
                "@RunWith(Cucumber.class)",
                "@CucumberOptions(\n" +
                        "        stepNotifications = true,\n" +
                        "        plugin = {\"pretty\", \"json:target/" + getNameFormat() + ".json\"},\n" +
                        "        glue = {\"com.sdl.selenium\"},\n" +
                        "        features = \"classpath:features/" + getPackageName() + "/" + featureFile.getFileName().toString() + "\"\n" +
                        ")",
                "public class " + nameRunner + " {",
                "}"
        );
        createFileAndAddContent(runnerPath, rows);
        return namePath;
    }

    @SneakyThrows
    private static void createFileAndAddContent(Path path, List<String> rows) {
        Path filePath = Files.createFile(path);
        Files.write(filePath, rows, StandardOpenOption.CREATE);
    }

    public void createSteps(Path namePath) {
        String nameSteps = getNameFormat() + "Steps";
        Path stepsPath = Paths.get(String.valueOf(namePath), nameSteps + ".java");
        List<String> stepsRows = List.of(
                "package com.sdl.selenium." + packageName + "." + getPackageFormat() + ";\n",
                "import com.sdl.selenium.TestBase;",
                "import io.cucumber.java.en.Given;",
                "import lombok.extern.slf4j.Slf4j;\n",
                "@Slf4j",
                "public class " + nameSteps + " extends TestBase {\n",
                "   @Given(\"I open " + getPackageNameFormat() + " app\")",
                "   public void iOpen" + getPackageNameFormat() + "App() {",
                "       driver.get(\"\");",
                "   }",
                "}"
        );
        createFileAndAddContent(stepsPath, stepsRows);
    }

    public void addStepInFeatureFile(String step) throws IOException {
        Path directoryPath = Paths.get(new File("src/test/resources").getAbsolutePath(), "features", packageName);
        Path featureFile = Paths.get(String.valueOf(directoryPath), name + ".feature");
        List<String> rows = Files.readAllLines(featureFile);
        Optional<String> find = rows.stream().filter(i -> i.contains(step)).findFirst();
        if (find.isEmpty()) {
            rows.add("");
            rows.add("  And " + step);
            Files.write(featureFile, rows, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public void createViewClass() throws IOException {
        Path viewDir = Paths.get(new File("src/main").getAbsolutePath(), "java", "com", "sdl", "selenium", packageName, getPackageFormat());
        if (!viewDir.toFile().exists()) {
            Files.createDirectories(viewDir);
        }
        Path viewFile = Paths.get(String.valueOf(viewDir), getNameFormat() + ".java");
        if (!viewFile.toFile().exists()) {
            List<String> viewRows = List.of(
                    "package com.sdl.selenium." + packageName + "." + getPackageFormat() + ";\n",
                    "import com.sdl.selenium.web.WebLocator;",
                    "import com.sdl.selenium.web.SearchType;",
                    "import lombok.extern.slf4j.Slf4j;\n",
                    "@Slf4j",
                    "public class " + getNameFormat() + " extends WebLocator {\n",
                    "   public " + getNameFormat() + "() {",
                    "       setClassName(\"" + getNameFormat() + "\");",
                    "   }\n",
                    "   public " + getNameFormat() + "(WebLocator container) {",
                    "       this();",
                    "       setContainer(container);",
                    "   }\n",
                    "   public " + getNameFormat() + "(WebLocator container, String text, SearchType... searchTypes) {",
                    "       this(container);",
                    "       if (searchTypes.length == 0) {",
                    "           searchTypes = new SearchType[]{SearchType.EQUALS};",
                    "       }",
                    "       setText(text, searchTypes);",
                    "   }",
                    "}"
            );
            createFileAndAddContent(viewFile, viewRows);
        }
    }

   public void addMethodInStepsClass(String step) throws IOException {
        Path stepsFile = Paths.get(new File("src/test").getAbsolutePath(), "java", "com", "sdl", "selenium", packageName, getPackageFormat(), getNameFormat() + "Steps.java");
        List<String> stepsRows = Files.readAllLines(stepsFile);
        String nameMethod = WordUtils.capitalize(step).replaceAll(" ", "");
        Optional<String> findStep = stepsRows.stream().filter(i -> i.contains(nameMethod)).findFirst();
        if (findStep.isEmpty()) {
            int index = lastIndexOf(stepsRows, s -> s.contains("import"));
            List<String> rows1 = List.of(
                    "import com.sdl.selenium." + packageName + "." + getPackageFormat() + "." + getNameFormat() + ";",
                    "import io.cucumber.java.en.And;",
                    "import static org.hamcrest.MatcherAssert.assertThat;",
                    "import static org.hamcrest.core.Is.is;"
            );
            stepsRows.addAll(index + 1, rows1);
            List<String> rows2 = List.of(
                    "",
                    "   @And(\"" + step + "\")",
                    "   public void " + nameMethod + "() {",
                    "       " + getNameFormat() + " " + name + " = new " + getNameFormat() + "(null, \"\");",
                    "       assertThat(" + name + ".isPresent(), is(true));",
                    "   }"
            );
            stepsRows.addAll(stepsRows.size() - 1, rows2);
            Files.write(stepsFile, stepsRows, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public static <E> int lastIndexOf(List<E> list, Predicate<E> predicate) {
        for (ListIterator<E> iter = list.listIterator(list.size()); iter.hasPrevious(); )
            if (predicate.test(iter.previous()))
                return iter.nextIndex();
        return -1;
    }

    public void createUnitTestClass() throws IOException {
        Path unitDir = Paths.get(new File("src/test").getAbsolutePath(), "java", "com", "sdl", "unit", packageName, getPackageFormat());
        if (!unitDir.toFile().exists()) {
            Files.createDirectories(unitDir);
        }
        Path unitFile = Paths.get(String.valueOf(unitDir), getNameFormat() + "Test.java");
        if (!unitFile.toFile().exists()) {
            List<String> unitRows = List.of(
                    "package com.sdl.unit." + packageName + "." + getPackageFormat() + ";\n",
                    "import com.sdl.selenium." + packageName + "." + getPackageFormat() + "." + getNameFormat() + ";",
                    "import com.sdl.selenium.web.SearchType;",
                    "import com.sdl.selenium.web.WebLocator;",
                    "import org.testng.annotations.DataProvider;",
                    "import org.testng.annotations.Test;\n",
                    "import static org.hamcrest.MatcherAssert.assertThat;",
                    "import static org.hamcrest.core.IsEqual.equalTo;\n",
                    "public class " + getNameFormat() + "Test  {\n",
                    "   public static WebLocator container = new WebLocator(\"container\");\n",
                    "   @DataProvider",
                    "   public static Object[][] testConstructorPathDataProvider() {",
                    "       return new Object[][]{",
                    "           {new " + getNameFormat() + "(), \"//*\"},",
                    "           {new " + getNameFormat() + "().setClasses(\"cls\"), \"//*[contains(concat(' ', @class, ' '), ' cls ')]\"},",
                    "           {new " + getNameFormat() + "(container), \"//*[contains(concat(' ', @class, ' '), ' container ')]//*\"},",
                    "           {new " + getNameFormat() + "(container, \"Text\"), \"//*[contains(concat(' ', @class, ' '), ' container ')]//*[text()='Text']\"},",
                    "           {new " + getNameFormat() + "(container, \"Text\", SearchType.CONTAINS), \"//*[contains(concat(' ', @class, ' '), ' container ')]//*[contains(text(),'Text')]\"},",
                    "       };",
                    "   }\n",
                    "   @Test(dataProvider = \"testConstructorPathDataProvider\")",
                    "   public void getPathSelectorCorrectlyFromConstructors(" + getNameFormat() + " " + name + ", String expectedXpath) {",
                    "       assertThat(" + name + ".getXPath(), equalTo(expectedXpath));",
                    "   }",
                    "}"
            );
            createFileAndAddContent(unitFile, unitRows);
        }
    }

    public void addUnitTestInTestNGFile() throws IOException {
        Path testNGFile = Paths.get(new File("src/test/resources").getAbsolutePath(), "unit", "testng-unit.xml");
        List<String> testNGRows = Files.readAllLines(testNGFile);
        String item = "<class name=\"com.sdl.unit." + packageName + "." + getPackageFormat() + "." + getNameFormat() + "Test\"/>";
        Optional<String> itemFound = testNGRows.stream().filter(i -> i.contains(item)).findFirst();
        if (itemFound.isEmpty()) {
            int index = lastIndexOf(testNGRows, s -> s.contains("<class name="));
            testNGRows.add(index + 1, "            " + item);
            Files.write(testNGFile, testNGRows, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
