package com.sdl.selenium;


import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;

//@Slf4j
public class ScenarioDefinitions extends BaseStepDefinitions {

    private static final Logger LOGGER = LogManager.getLogger(ScenarioDefinitions.class);
    @Before
    public void before(Scenario scenario) {
        String scenarioId = getScenarioId(scenario);
        LOGGER.info("Adding scenario to storage: {} with id {}.", scenario, scenarioId);
        storage.set("SCENARIO_ID", scenarioId);
        LOGGER.info("Adding scenario to storage: {}.", scenario);
        storage.set("SCENARIO", scenario);
    }

    private String getScenarioId(Scenario scenario) {
        String featureUri = scenario.getUri().toString();
        String featureName = featureUri.substring(featureUri.lastIndexOf("/") + 1, featureUri.lastIndexOf("."));
        String scenarioId = (featureName + ";" + scenario.getName()).toLowerCase().replaceAll(" ", "-");
        return scenarioId;
    }

    @After
    public void after(Scenario scenario) {
        LOGGER.info("Removing scenario from storage: {}.", scenario);
        storage.set("SCENARIO_ID", "removed_after_completion");
        storage.set(scenario.getName(), scenario.getStatus().name());
    }
}
