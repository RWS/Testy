Feature: Dialog

  Scenario: Start
    Given I open MaterialUI and add "react-dialog/" path

  Scenario: Verify Dialog
#    And I verify if Dialog is present
    And in MaterialUI I see Dialog with "Use Google" title, "Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running." message and click on "Agree" button
