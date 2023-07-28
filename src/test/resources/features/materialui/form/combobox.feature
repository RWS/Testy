Feature: ComboBox

  Scenario: Start
    Given I open MaterialUI and add "react-select/" path

  Scenario: Verify combobox
    And I verify if combobox is present
    And I select "Twenty" in "Age" combobox
    And I verify if "Age" combobox have values: "Ten, Twenty, Thirty"
    And in MaterialUI I verify if Age has value "Ten"
