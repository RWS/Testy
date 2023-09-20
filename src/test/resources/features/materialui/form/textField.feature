Feature: TextField

  Scenario: Start
    Given I open MaterialUI and add "react-text-field/" path

  Scenario: Verify textfield
    And I verify if textfield is present
    And in MaterialUI I set Name "Twenty"
    And in MaterialUI I verify if Name has value "Twenty"