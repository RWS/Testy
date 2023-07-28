Feature: Checkbox

  Scenario: Start
    Given I open MaterialUI app and add "react-checkbox/" path

  Scenario: Verify checkbox
    And I verify if checkbox is present
    And I verify if "Start" checkbox is uncheck
    And I check "Start" checkbox
    And I verify if "Start" checkbox is check
    And I verify if "End" checkbox is uncheck
