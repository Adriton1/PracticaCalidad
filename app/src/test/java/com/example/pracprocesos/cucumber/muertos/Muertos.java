package com.example.pracprocesos.cucumber.muertos;
import com.example.pracprocesos.Ciudad;
import com.example.pracprocesos.Virus;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import java.util.concurrent.ConcurrentHashMap;

@RunWith(Cucumber.class)
public class Muertos {

    @Given("^The user is playing$")
    public void the_user_is_playing() throws Throwable {
        System.out.println("You are in turn 32");
    }

    @When("^The user plays the next turn and there are no people left alive$")
    public void the_user_plays_the_next_turn_and_there_are_no_people_left_alive() throws Throwable {
        Virus virus = new Virus();
        ConcurrentHashMap<String, Ciudad> gameGraph = new ConcurrentHashMap<String, Ciudad>();
        for (int i=0; i!=21; i++) {
            Ciudad ciu = new Ciudad(String.valueOf(i),
                    "a",
                    10000,
                    1000000,
                    0,
                    0,
                    1000000,
                    0,
                    0,
                    null,
                    null,
                    null);
            gameGraph.put(String.valueOf(i), ciu);
        }
        virus.actualizar(gameGraph);
        boolean muertos = virus.isEveryoneDead(gameGraph);
        if (!muertos) {
            throw new Exception("Debería haber muerto toda Espanya");
        }
    }

    @Then("^The player wins$")
    public void the_player_wins() throws Throwable {
        System.out.println("Has ganado");
    }

}
