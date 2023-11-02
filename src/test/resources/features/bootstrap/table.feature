Feature: Table

  Scenario: Verify tables
    Given I open bootstrap app
    Then I verify if table has values:
      | John  | Carter | johncarter@mail.com  |
      | Peter | Parker | peterparker@mail.com |
      | John  | Moore  | johnmoore@mail.com   |
      | David | Miller | davidmiller@mail.com |
      | Nick  | White  | nickwhite@mail.com   |
      | Bob   | Smith  | bobsmith@mail.com    |

  Scenario: Verify tables
    Then I verify if table has object values:
      | first | last   | email                |
      | John  | Carter | johncarter@mail.com  |
      | Peter | Parker | peterparker@mail.com |
      | John  | Moore  | johnmoore@mail.com   |
      | David | Miller | davidmiller@mail.com |
      | Nick  | White  | nickwhite@mail.com   |
      | Bob   | Smith  | bobsmith@mail.com    |