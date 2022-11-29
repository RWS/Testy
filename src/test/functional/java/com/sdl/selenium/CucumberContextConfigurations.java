package com.sdl.selenium;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
//@ContextConfiguration(locations = {"classpath:cucumber.xml"})
@SpringBootTest(classes = TestConfig.class)
public class CucumberContextConfigurations {
}
