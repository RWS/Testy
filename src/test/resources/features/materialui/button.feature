Feature: Materialui

  Scenario: Start
    Given I open Materialui app and add "#contained-buttons" path

  Scenario: Verify button
    And I verify if button is present
    And I verify if "Disabled" button is disabled
    And I verify if "Primary" button is enabled
