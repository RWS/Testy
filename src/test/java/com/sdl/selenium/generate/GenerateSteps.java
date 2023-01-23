package com.sdl.selenium.generate;

import com.sdl.selenium.InputData;
import com.sdl.selenium.TestBase;
import com.sdl.selenium.web.utils.Utils;
import io.cucumber.java.en.Given;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class GenerateSteps extends TestBase {

    @SneakyThrows
    @Given("I generate feature {string} file in {string} package")
    public void iGenerateFeatureFileInPackage(String featureName, String packageName) {
        List<Path> features = getAllFilesFromResource("features");
        boolean createDirectory = true;
        boolean createFile = true;
        Path directoryPath = null;
        for (Path feature : features) {
            boolean directory = feature.toFile().isDirectory();
            String fileName = feature.toString();
            if (directory) {
                if (fileName.contains(packageName)) {
                    createDirectory = false;
                    directoryPath = feature;
                }
            } else {
                if (fileName.contains(featureName)) {
                    createFile = false;
                }
            }
        }
        if (createDirectory) {
            Path dir = Paths.get(String.valueOf(features.get(0)), packageName);
            directoryPath = Files.createDirectories(dir);
        }
        if (createFile) {
            String packageNameFormat = WordUtils.capitalize(packageName);
            Path featureFile = createFeatureFile(String.valueOf(directoryPath), featureName, packageNameFormat);
            Path path = Paths.get(InputData.TEST_PATH, "java", "com", "sdl", "selenium", packageName);
            if (!path.toFile().exists()) {
                Files.createDirectories(path);
                Path namePath = Paths.get(InputData.TEST_PATH, "java", "com", "sdl", "selenium", packageName, featureName);
                Files.createDirectories(namePath);
                String nameFormat = WordUtils.capitalizeFully(featureName, '-').replaceAll("-", "");
                String nameRunner = nameFormat + "Runner";
                Path runnerPath = Paths.get(String.valueOf(namePath), nameRunner + ".java");
                List<String> rows = List.of(
                        "package com.sdl.selenium." + packageName + "." + featureName + ";\n",
                        "import io.cucumber.junit.Cucumber;",
                        "import io.cucumber.junit.CucumberOptions;",
                        "import org.junit.runner.RunWith;\n",
                        "@RunWith(Cucumber.class)",
                        "@CucumberOptions(\n" +
                                "        plugin = {\"pretty\", \"json:target/" + nameFormat + ".json\"},\n" +
                                "        glue = {\"com.sdl.selenium\"},\n" +
                                "        features = \"classpath:features/" + packageName + "/" + featureFile.getFileName().toString() + "\"\n" +
                                ")",
                        "public class " + nameRunner + " {",
                        "}"
                );
                createFileAndAddContent(runnerPath, rows);
                String nameSteps = nameFormat + "Steps";
                Path stepsPath = Paths.get(String.valueOf(namePath), nameSteps + ".java");
                List<String> stepsRows = List.of(
                        "package com.sdl.selenium." + packageName + "." + featureName + ";\n",
                        "import com.sdl.selenium.TestBase;",
                        "import io.cucumber.java.en.Given;",
                        "import lombok.extern.slf4j.Slf4j;\n",
                        "@Slf4j",
                        "public class " + nameSteps + " extends TestBase {\n",
                        "   @Given(\"I open " + packageNameFormat + " app\")",
                        "   public void iOpen" + packageNameFormat + "App() {",
                        "       driver.get(\"\");",
                        "   }",
                        "}"
                );
                createFileAndAddContent(stepsPath, stepsRows);
            }
        }
        Utils.sleep(1);
    }

    @SneakyThrows
    private static void createFileAndAddContent(Path path, List<String> rows) {
        Path filePath = Files.createFile(path);
        Files.write(filePath, rows, StandardOpenOption.CREATE);
    }

    @SneakyThrows
    private static Path createFeatureFile(String directoryPath, String name, String packageName) {
        Path path = Paths.get(directoryPath, name + ".feature");
        String nameFormat = WordUtils.capitalizeFully(name, '-').replaceAll("-", "");
        Path filePath = Files.createFile(path);
        List<String> rows = List.of(
                "Feature: " + nameFormat + "\n",
                "  Scenario: Start",
                "    Given I open " + packageName + " app"
        );
        Files.write(filePath, rows, StandardOpenOption.CREATE);
        return filePath;
    }

    @SneakyThrows
    private List<Path> getAllFilesFromResource(String folder) {
        String path = InputData.RESOURCES_DIRECTORY_PATH + File.separator + folder;
        return Files.walk(Paths.get(path)).collect(Collectors.toList());
    }

    @Given("I generate step {string} in feature {string} file in {string} package")
    public void iGenerateStepInFeatureFileInPackage(String step, String featureName, String packageName) throws IOException {
        Path directoryPath = Paths.get(InputData.RESOURCES_DIRECTORY_PATH, "features", packageName);
        Path featureFile = Paths.get(String.valueOf(directoryPath), featureName + ".feature");
        List<String> rows = Files.readAllLines(featureFile);
        rows.add("");
        rows.add("  And " + step);
        Files.write(featureFile, rows, StandardOpenOption.TRUNCATE_EXISTING);

        Path viewDir = Paths.get(InputData.MAIN_PATH, "java", "com", "sdl", "selenium", packageName, featureName);
        if (!viewDir.toFile().exists()) {
            Files.createDirectories(viewDir);
        }

        String featureNameFormat = WordUtils.capitalizeFully(featureName);
        Path viewFile = Paths.get(String.valueOf(viewDir), featureNameFormat + ".java");
        List<String> viewRows = List.of(
                "package com.sdl.selenium." + packageName + "." + featureName + ";\n",
                "import com.sdl.selenium.web.WebLocator;",
                "import com.sdl.selenium.web.SearchType;",
                "import lombok.extern.slf4j.Slf4j;\n",
                "@Slf4j",
                "public class " + featureNameFormat + " extends WebLocator {\n",
                "   public " + featureNameFormat + "() {",
                "       setClassName(\"Button\");",
                "   }\n",
                "   public " + featureNameFormat + "(WebLocator container) {",
                "       this();",
                "       setContainer(container);",
                "   }\n",
                "   public " + featureNameFormat + "(WebLocator container, String text, SearchType... searchTypes) {",
                "       this(container);",
                "       if (searchTypes.length == 0) {",
                "           searchTypes = new SearchType[]{SearchType.EQUALS};",
                "       }",
                "       setText(text, searchTypes);",
                "   }",
                "}"
        );
        createFileAndAddContent(viewFile, viewRows);
        Path stepsFile = Paths.get(InputData.TEST_PATH, "java", "com", "sdl", "selenium", packageName, featureName, featureNameFormat + "Steps.java");
        List<String> stepsRows = Files.readAllLines(stepsFile);
        int index = lastIndexOf(stepsRows, s -> s.contains("import"));
        List<String> rows1 = List.of(
                "import com.sdl.selenium." + packageName + "." + featureName + "." + featureNameFormat + ";",
                "import io.cucumber.java.en.And;",
                "import static org.hamcrest.MatcherAssert.assertThat;",
                "import static org.hamcrest.core.Is.is;"
        );
        stepsRows.addAll(index + 1, rows1);
        List<String> rows2 = List.of(
                "",
                "   @And(\"" + step + "\")",
                "   public void " + WordUtils.capitalizeFully(step).replaceAll(" ", "") + "() {",
                "       Button button = new Button(null, \"\");",
                "       assertThat(button.isPresent(), is(true));",
                "   }"
        );
        stepsRows.addAll(stepsRows.size() - 1, rows2);
        Files.write(stepsFile, stepsRows, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static <E> int lastIndexOf(List<E> list, Predicate<E> predicate) {
        for (ListIterator<E> iter = list.listIterator(list.size()); iter.hasPrevious(); )
            if (predicate.test(iter.previous()))
                return iter.nextIndex();
        return -1;
    }
}
