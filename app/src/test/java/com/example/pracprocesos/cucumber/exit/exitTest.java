package com.example.pracprocesos.cucumber.exit;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class exitTest {
    @Given("^the player is in a game \"([^\"]*)\"$")
    public void the_player_is_in_a_game_something(String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Given("^the player pushing the button without noticing \"([^\"]*)\"$")
    public void the_player_pushing_the_button_without_noticing_something(String strArg1) throws Throwable {
        throw new PendingException();
    }

    @When("^they push the exit button and confirm$")
    public void they_push_the_exit_button_and_confirm() throws Throwable {
        throw new PendingException();
    }

    @When("^they push the button$")
    public void they_push_the_button() throws Throwable {
        throw new PendingException();
    }

    @Then("^the game cancels the current playthrough and returns to the main menu \"([^\"]*)\"$")
    public void the_game_cancels_the_current_playthrough_and_returns_to_the_main_menu_something(String strArg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^a confirmation screen pops up asking if they are sure about quitting. \"([^\"]*)\"$")
    public void a_confirmation_screen_pops_up_asking_if_they_are_sure_about_quitting_something(String strArg1) throws Throwable {
        throw new PendingException();
    }
}
