Feature: Table

  Scenario: Verify tables
    Given I open bootstrap app
    Then I verify if table has values:
      | John  | Carter | {email} |
      | Peter | Parker | {email} |
      | John  | Moore  | {email} |
      | David | Miller | {email} |
      | Nick  | White  | {email} |
      | Bob   | Smith  | {email} |

  Scenario: Verify tables
    Then I verify if table has object values:
      | first | last   | email                |
      | Johnd | Carter | johncarter@mail.com  |
      | Peter | Parker | peterparker@mail.com |
      | John  | Moore  | johnmoore@mail.com   |
      | David | Miller | davidmiller@mail.com |
      | Nick  | White  | nickwhite@mail.com   |
      | Bob   | Smith  | bobsmith@mail.com    |