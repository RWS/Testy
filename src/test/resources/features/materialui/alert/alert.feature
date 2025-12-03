Feature: Alert

  Scenario: Start
    Given I open MaterialUI and add "react-alert/" path

  Scenario: Verify alerts
    And in MaterialUI I verify if Alert with "This is an error Alert." message is present
    And in MaterialUI I verify if Alert with "This is a warning Alert." message is present
    And in MaterialUI I verify if Alert with "This is an info Alert." message is present
    And in MaterialUI I verify if Alert with "This is a success Alert." message is present

  Scenario: Final
    And I stop
