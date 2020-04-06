package com.vam.auto;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/main/resources/test_scripts", plugin = {"html:target/report.html"}, tags= {"login"})
public class RunTest {

}
