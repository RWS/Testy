Feature: Generate feature file

  Scenario: I generate feature file, runner and steps
    Given I generate feature "textField" file in "materialui/form" package
    Given I generate step "I verify if textfield is present" in feature "textField" file in "materialui/form" package