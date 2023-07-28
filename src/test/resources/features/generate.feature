Feature: Generate feature file

  Scenario: I generate feature file, runner and steps
    Given I generate feature "list" file in "materialui/list" package
    Given I generate step "I verify if List is present" in feature "list" file in "materialui/list" package