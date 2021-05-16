Feature: Button to leave an unfinished game.
	Scenario: Player wants to leave the game prematurely.
    	Given the player is in a game "1"
        When they push the exit button and confirm "2"
        Then the game cancels the current playthrough and returns to the main menu "2"
    Scenario: Player pushes the exit button accidentaly.
    	Given the player pushing the button without noticing "2"
        When they push the button
        Then a confirmation screen pops up asking if they are sure about quitting. "1"