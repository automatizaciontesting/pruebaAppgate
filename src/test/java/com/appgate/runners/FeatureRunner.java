package com.appgate.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;
import io.cucumber.junit.CucumberSerenityRunner;

@RunWith(CucumberSerenityRunner.class)
@CucumberOptions(features = "src/test/resources/features/ApiRest.feature",
        glue = "com.appgate.stepsdefinition",
        snippets = CAMELCASE,
        tags = "")
public class FeatureRunner {
}
