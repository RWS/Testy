Feature: SplitButton

  Scenario: Start
    Given I open extjs6 app and add "#toolbar-menus" path

  Scenario: Verify SplitButton
    And in ExtJs I verify if SplitButton is present

  Scenario: Verify all values
    And in ExtJs I verify if SplitButton has following values:
      | I like Ext     |
      | Radio Options  |
      | >Aero Glass    |
      | >Vista Black   |
      | >Gray Theme    |
      | >Default Theme |
      | Choose a Date  |
      | Choose a Color |
      | Disabled Item  |
    And I stop


