Feature: Grid

  Scenario: Verify grid
    Given I open extjs6 app and add "#cell-editing" path
    Then I verify if grid has values:
      | Adder's-Tongue      | Shade        | $9.58 | Apr 13, 2006 | true  |
      | Anemone             | Mostly Shady | $8.86 | Dec 26, 2006 | true  |
      | Bee Balm            | Shade        | $4.59 | May 03, 2006 | true  |
      | Bergamot            | Shade        | $7.16 | Apr 27, 2006 | true  |
      | Black-Eyed Susan    | Sunny        | $9.80 | Jun 18, 2006 | false |
      | Bloodroot           | Mostly Shady | $2.44 | Mar 15, 2006 | true  |
      | Blue Gentian        | Sun or Shade | $8.56 | May 02, 2006 | false |
      | Buttercup           | Shade        | $2.57 | Jun 10, 2006 | true  |
      | Butterfly Weed      | Sunny        | $2.78 | Jun 30, 2006 | false |
      | California Poppy    | Sunny        | $7.89 | Mar 27, 2006 | false |
      | Cardinal Flower     | Shade        | $3.02 | Feb 22, 2006 | true  |
      | Cinquefoil          | Shade        | $7.06 | May 25, 2006 | true  |
      | Columbine           | Mostly Shady | $9.37 | Mar 06, 2006 | true  |
      | Cowslip             | Mostly Shady | $9.90 | Mar 06, 2006 | true  |
      | Crowfoot            | Shade        | $9.34 | Apr 03, 2006 | true  |
      | Dutchman's-Breeches | Mostly Shady | $6.44 | Jan 20, 2006 | true  |
      | Gentian             | Sun or Shade | $7.81 | May 18, 2006 | false |
      | Ginger, Wild        | Mostly Shady | $9.03 | Apr 18, 2006 | true  |
      | Grecian Windflower  | Mostly Shady | $9.16 | Jul 10, 2006 | true  |
      | Greek Valerian      | Shade        | $4.36 | Jul 14, 2006 | true  |
      | Hepatica            | Mostly Shady | $4.45 | Jan 26, 2006 | true  |
      | Jack-In-The-Pulpit  | Mostly Shady | $3.23 | Feb 01, 2006 | true  |
      | Jacob's Ladder      | Shade        | $9.26 | Feb 21, 2006 | true  |
      | Liverleaf           | Mostly Shady | $3.99 | Jan 02, 2006 | true  |
      | Marsh Marigold      | Mostly Sunny | $6.81 | May 17, 2006 | false |
      | Mayapple            | Mostly Shady | $2.98 | Jun 05, 2006 | true  |
      | Phlox, Blue         | Sun or Shade | $5.59 | Feb 16, 2006 | false |
      | Phlox, Woodland     | Sun or Shade | $2.80 | Jan 22, 2006 | false |
      | Primrose            | Sunny        | $6.56 | Jan 30, 2006 | false |
      | Shooting Star       | Mostly Shady | $8.60 | May 13, 2006 | true  |
      | Snakeroot           | Shade        | $5.63 | Jul 11, 2006 | true  |
      | Spring-Beauty       | Mostly Shady | $6.59 | Feb 01, 2006 | true  |
      | Trillium            | Sun or Shade | $3.90 | Apr 29, 2006 | false |
      | Trout Lily          | Shade        | $6.94 | Mar 24, 2006 | true  |
      | Violet, Dog-Tooth   | Shade        | $9.04 | Feb 01, 2006 | true  |
      | Wake Robin          | Sun or Shade | $3.20 | Feb 21, 2006 | false |

#  Scenario: Verify grid
#    Then I verify if table has object values:
#      | first | last   | email                |
#      | Johnd | Carter | johncarter@mail.com  |
#      | Peter | Parker | peterparker@mail.com |
#      | John  | Moore  | johnmoore@mail.com   |
#      | David | Miller | davidmiller@mail.com |
#      | Nick  | White  | nickwhite@mail.com   |
#      | Bob   | Smith  | bobsmith@mail.com    |