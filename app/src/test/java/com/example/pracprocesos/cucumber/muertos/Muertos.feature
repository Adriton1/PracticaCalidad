Feature: Cada turno se comprueba que si toda España ha muerto por el virus

  Scenario: El jugador juega un turno y la cifra de infectados y muertos se ha actualizado (y gana)
    Given The user is playing
    When The user plays the next turn and there are no people left alive
    Then The player wins