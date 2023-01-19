package com.sdl.selenium;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class CucumberContextConfigurations {
}
