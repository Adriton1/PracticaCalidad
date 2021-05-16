package com.example.pracprocesos.cucumber.exit;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class exitTest {
    private boolean enPartida,confirmacion;
    private String aux="";
    @Given("^the player is in a game \"([^\"]*)\"$")
    public void the_player_is_in_a_game_something(String strArg1) throws Throwable {
        enPartida=true;
        confirmacion=false;
        aux=strArg1;
    }

    @Given("^the player pushing the button without noticing \"([^\"]*)\"$")
    public void the_player_pushing_the_button_without_noticing_something(String strArg1) throws Throwable {
        enPartida=true;
        confirmacion=false;
        aux=strArg1;
    }

    @When("^they push the exit button and confirm \"([^\"]*)\"$")
    public void they_push_the_exit_button_and_confirm(String strArg1) throws Throwable {
        enPartida=true;
        confirmacion=true;
        aux=strArg1;
    }

    @When("^they push the button$")
    public void they_push_the_button() throws Throwable {
        enPartida=true;
        confirmacion=false;
    }

    @Then("^the game cancels the current playthrough and returns to the main menu \"([^\"]*)\"$")
    public void the_game_cancels_the_current_playthrough_and_returns_to_the_main_menu_something(String strArg1) throws Throwable {
        if(aux.equals(strArg1) && confirmacion==true){
            enPartida=false;
            System.out.println("El jugador ha salido correctamente de la partida");
        }
    }

    @Then("^a confirmation screen pops up asking if they are sure about quitting. \"([^\"]*)\"$")
    public void a_confirmation_screen_pops_up_asking_if_they_are_sure_about_quitting_something(String strArg1) throws Throwable {
        if(confirmacion==false){
            System.out.println("Se le muestra al jugador correctamente la pantalla de confirmación");
        }
    }
}
