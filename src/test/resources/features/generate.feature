Feature: Generate feature file

  Scenario: I generate feature file, runner and steps
    Given I generate feature "alert" file in "materialui/alert" package
    Given I generate step "in MaterialUI I verify if Alert is present" in feature "alert" file in "materialui/alert" package