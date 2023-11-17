Feature: List

  Scenario: Start
    Given I open MaterialUI and add "react-list/" path

  Scenario: Verify list
    And I verify if List is present

  Scenario: Verify all items of list
    And I verify if List has items "Inbox,Drafts"
    And I stop
