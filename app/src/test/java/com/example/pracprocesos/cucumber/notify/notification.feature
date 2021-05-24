Feature: Notify when no more people are left in a province
	Scenario: A player completely infects a province
    	Given the player is in a province and no people are left
        When the player pushes the Infect button
        Then the game gives a notification indicating there are no more people