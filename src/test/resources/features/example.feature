Feature: Banking App Login

  Scenario: Successful login with valid credentials
    Given the user is on the banking app login page
    When the user enters valid credentials
    Then the user should be logged in successfully
