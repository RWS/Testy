Feature: SegmentedButton

  Scenario: Start
    Given I open extjs6 app and add "#segmented-buttons" path

    And in ExtJs I verify if SegmentedButton is present

    And in ExtJs I verify if SegmentedButton has "Option One, Option Two, Option Three" values
    And in ExtJs I press on SegmentedButton with "Option Two" value
    And I stop
