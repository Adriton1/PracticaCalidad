package com.example.pracprocesos.cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@CucumberOptions(
        plugin = "pretty",
        features = {
                "src/test/java/com/example/pracprocesos/cucumber/muertos/Muertos.feature",
                "src/test/java/com/example/pracprocesos/cucumber/lecturaCiudades/LecturaCiudades.feature"
        },
        glue = "com.example.pracprocesos.cucumber"
)
@RunWith(Cucumber.class)
public class Runner {

}
