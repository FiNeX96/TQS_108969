Feature: Using Bus Ticket Service


Scenario: User can buy a bus Ticket

    Given the user entered in the website
    When the user searches for trips
    And selects the first trip
    And selects the seat 26
    And the seat number 26 is not taken yet 
    And the user inputs his information
    And the user clicks on the buy button
    Then the user should receive a confirmation message


Scenario: User cannot buy a bus Ticket

    Given the user entered in the website
    When the user searches for trips
    And selects the first trip
    And selects the seat 26
    And the seat number 26 is already taken
    Then the buy button should say "Seat Taken"








