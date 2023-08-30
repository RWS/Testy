Feature: Generate feature file

  Scenario: I generate feature file, runner and steps
    Given I generate feature "textarea" file in "materialui/form" package
    Given I generate step "in MaterialUI I verify if TextArea is present" in feature "textarea" file in "materialui/form" package