Feature: Alert

  Scenario: Start
    Given I open MaterialUI and add "react-alert/" path

  Scenario: Verify alets
    And in MaterialUI I verify if Alert with "This is an error alert — check it out!" message is present
    And in MaterialUI I verify if Alert with "This is a warning alert — check it out!" message is present
    And in MaterialUI I verify if Alert with "This is an info alert — check it out!" message is present
    And in MaterialUI I verify if Alert with "This is a success alert — check it out!" message is present
