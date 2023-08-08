Feature: Generate feature file

  Scenario: I generate feature file, runner and steps
    Given I generate feature "dialog" file in "materialui/dialog" package
    Given I generate step "I verify if Dialog is present" in feature "dialog" file in "materialui/dialog" package