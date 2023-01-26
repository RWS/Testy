package com.sdl.selenium.generate;

import com.sdl.selenium.TestBase;
import com.sdl.selenium.utils.GenerateUtils;
import io.cucumber.java.en.Given;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class GenerateSteps extends TestBase {

    @SneakyThrows
    @Given("I generate feature {string} file in {string} package")
    public void iGenerateFeatureFileInPackage(String featureName, String packageName) {
        GenerateUtils generateUtils = new GenerateUtils(featureName, packageName);
        Path featureFile = generateUtils.createPackageAndFeatureFile();
        Path namePath = generateUtils.createRunner(featureFile);
        generateUtils.createSteps(namePath);
    }

    @Given("I generate step {string} in feature {string} file in {string} package")
    public void iGenerateStepInFeatureFileInPackage(String step, String featureName, String packageName) throws IOException {
        GenerateUtils generateUtils = new GenerateUtils(featureName, packageName);
        generateUtils.addStepInFeatureFile(step);
        generateUtils.createViewClass();
        generateUtils.addMethodInStepsClass(step);
        generateUtils.createUnitTestClass();
        generateUtils.addUnitTestInTestNGFile();
    }
}
