package com.sdl.selenium;

import io.cucumber.junit.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class CucumberContextConfigurations {
}
