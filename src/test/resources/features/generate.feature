Feature: Generate feature file

  Scenario: I generate feature file, runner and steps
    Given I generate feature "menu" file in "materialui/menu" package
    Given I generate step "in MaterialUI I verify if Menu is present" in feature "menu" file in "materialui/menu" package