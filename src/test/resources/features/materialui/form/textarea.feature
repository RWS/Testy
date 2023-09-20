Feature: Textarea

  Scenario: Start
    Given I open MaterialUI and add "react-text-field/" path

  Scenario: Verify textfield
    And in MaterialUI I verify if TextArea is present
    And in MaterialUI I set Multiline "Message in textarea"
    And in MaterialUI I verify if Multiline has value "Message in textarea"
