package com.sdl.selenium.materialui.form;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.sdl.selenium")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber, json:target/jsonReports/Checkbox.json")
@SelectClasspathResource("features/materialui/form/checkbox.feature")
public class CheckBoxRunner {
}
