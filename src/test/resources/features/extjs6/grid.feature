Feature: Grid

  Scenario: Verify grid object
    Given I open extjs6 app and add "#cell-editing" path
    Then I verify if grid has object values:
      | commonName          | light        | price | available    | indoor |
      | Adder's-Tongue      | Shade        | $9.58 | Apr 13, 2006 | true   |
      | Anemone             | Mostly Shady | $9.58 | Dec 26, 2006 | true   |
      | Bee Balm            | Shade        | $9.58 | May 03, 2006 | true   |
      | Bergamot            | Shade        | $9.58 | Apr 27, 2006 | true   |
      | Black-Eyed Susan    | Sunny        | $9.58 | Jun 18, 2006 | false  |
      | Bloodroot           | Mostly Shady | $9.58 | Mar 15, 2006 | true   |
      | Blue Gentian        | Sun or Shade | $9.58 | May 02, 2006 | false  |
      | Buttercup           | Shade        | $9.58 | Jun 10, 2006 | true   |
      | Butterfly Weed      | Sunny        | $9.58 | Jun 30, 2006 | false  |
      | California Poppy    | Sunny        | $9.58 | Mar 27, 2006 | false  |
      | Cardinal Flower     | Shade        | $9.58 | Feb 22, 2006 | true   |
      | Cinquefoil          | Shade        | $9.58 | May 25, 2006 | true   |
      | Columbine           | Mostly Shady | $9.58 | Mar 06, 2006 | true   |
      | Cowslip             | Mostly Shady | $9.58 | Mar 06, 2006 | true   |
      | Crowfoot            | Shade        | $9.58 | Apr 03, 2006 | true   |
      | Dutchman's-Breeches | Mostly Shady | $9.58 | Jan 20, 2006 | true   |
      | Gentian             | Sun or Shade | $9.58 | May 18, 2006 | false  |
      | Ginger, Wild        | Mostly Shady | $9.58 | Apr 18, 2006 | true   |
      | Grecian Windflower  | Mostly Shady | $9.58 | Jul 10, 2006 | true   |
      | Greek Valerian      | Shade        | $9.58 | Jul 14, 2006 | true   |
      | Hepatica            | Mostly Shady | $9.58 | Jan 26, 2006 | true   |
      | Jack-In-The-Pulpit  | Mostly Shady | $9.58 | Feb 01, 2006 | true   |
      | Jacob's Ladder      | Shade        | $9.58 | Feb 21, 2006 | true   |
      | Liverleaf           | Mostly Shady | $9.58 | Jan 02, 2006 | true   |
      | Marsh Marigold      | Mostly Sunny | $9.58 | May 17, 2006 | false  |
      | Mayapple            | Mostly Shady | $9.58 | Jun 05, 2006 | true   |
      | Phlox, Blue         | Sun or Shade | $9.58 | Feb 16, 2006 | false  |
      | Phlox, Woodland     | Sun or Shade | $9.58 | Jan 22, 2006 | false  |
      | Primrose            | Sunny        | $9.58 | Jan 30, 2006 | false  |
      | Shooting Star       | Mostly Shady | $9.58 | May 13, 2006 | true   |
      | Snakeroot           | Shade        | $9.58 | Jul 11, 2006 | true   |
      | Spring-Beauty       | Mostly Shady | $9.58 | Feb 01, 2006 | true   |
      | Trillium            | Sun or Shade | $9.58 | Apr 29, 2006 | false  |
      | Trout Lily          | Shade        | $9.58 | Mar 24, 2006 | true   |
      | Violet, Dog-Tooth   | Shade        | $9.58 | Feb 01, 2006 | true   |
      | Wake Robin          | Sun or Shade | $9.58 | Feb 21, 2006 | false  |

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

  Scenario: Verify grid parallel
    Given I open extjs6 app and add "#row-numberer" path
    Then I verify if grid has headers "Company, Price, Change, % Change, Last Updated"
    Then I verify parallel if grid has values:
      | 1   | Roodel       | $59.47 | 1.23  | 2.11   | 10/08/2025 |
      | 2   | Voomm        | $41.31 | 2.64  | 6.83   | 10/18/2025 |
      | 3   | Dabvine      | $29.94 | 3.55  | 13.45  | 10/11/2025 |
      | 4   | Twitterbeat  | $89.96 | -3.82 | -4.07  | 10/02/2025 |
      | 5   | Lajo         | $65.51 | 1.48  | 2.31   | 10/14/2025 |
      | 6   | Livetube     | $52.34 | 0.91  | 1.77   | 10/03/2025 |
      | 7   | Flipstorm    | $41.81 | -1.58 | -3.64  | 10/09/2025 |
      | 8   | Oloo         | $53.27 | 2.06  | 4.02   | 10/14/2025 |
      | 9   | Roombo       | $21.53 | -4.04 | -15.8  | 10/13/2025 |
      | 10  | Ntags        | $34.31 | 2.94  | 9.37   | 10/14/2025 |
      | 11  | Shuffletag   | $25.92 | 0.77  | 3.06   | 10/02/2025 |
      | 12  | Skivee       | $50.61 | -3.11 | -5.79  | 10/04/2025 |
      | 13  | Tanoodle     | $64.26 | -2.91 | -4.33  | 10/01/2025 |
      | 14  | Buzzster     | $37.16 | -1.09 | -2.85  | 10/14/2025 |
      | 15  | Topicblab    | $80.68 | -3.68 | -4.36  | 10/12/2025 |
      | 16  | Thoughtworks | $64.59 | -2.68 | -3.98  | 10/16/2025 |
      | 17  | Feedfire     | $21.51 | -3.72 | -14.74 | 10/12/2025 |
      | 18  | Thoughtstorm | $80.48 | -2.77 | -3.33  | 10/18/2025 |
      | 19  | Agivu        | $74.05 | 0.14  | 0.19   | 10/04/2025 |
      | 20  | Babbleblab   | $37.24 | -0.43 | -1.14  | 10/18/2025 |
      | 21  | Thoughtstorm | $71.75 | -0.83 | -1.14  | 10/02/2025 |
      | 22  | Skalith      | $52.57 | 3.79  | 7.77   | 10/17/2025 |
      | 23  | Vipe         | $67.77 | 1.18  | 1.77   | 10/01/2025 |
      | 24  | Bubblemix    | $61.24 | -3.11 | -4.83  | 10/15/2025 |
      | 25  | Kamba        | $37.20 | -2.96 | -7.37  | 10/10/2025 |
      | 26  | Zoombox      | $21.13 | -3.47 | -14.11 | 10/01/2025 |
      | 27  | Roomm        | $25.09 | -2.25 | -8.23  | 10/18/2025 |
      | 28  | Yacero       | $38.35 | 4.5   | 13.29  | 10/12/2025 |
      | 29  | Oyoloo       | $64.89 | -1.73 | -2.6   | 10/18/2025 |
      | 30  | Blogpad      | $64.20 | 0.14  | 0.22   | 10/01/2025 |
      | 31  | Lajo         | $84.82 | -2.05 | -2.36  | 10/16/2025 |
      | 32  | Zoombox      | $51.51 | 4.44  | 9.43   | 10/12/2025 |
      | 33  | Voolith      | $62.93 | 0.59  | 0.95   | 10/01/2025 |
      | 34  | Kwinu        | $48.11 | -2.66 | -5.24  | 10/07/2025 |
      | 35  | Livefish     | $21.23 | -0.72 | -3.28  | 10/11/2025 |
      | 36  | Kwinu        | $68.76 | 3.56  | 5.46   | 10/16/2025 |
      | 37  | Miboo        | $46.60 | 3.45  | 8      | 10/18/2025 |
      | 38  | Kwilith      | $58.14 | 0.14  | 0.24   | 10/07/2025 |
      | 39  | Photolist    | $56.49 | -4.73 | -7.73  | 10/03/2025 |
      | 40  | Miboo        | $77.71 | -3.93 | -4.81  | 10/09/2025 |
      | 41  | Browsedrive  | $49.90 | -1.72 | -3.33  | 10/02/2025 |
      | 42  | Riffpedia    | $45.90 | 0.11  | 0.24   | 10/12/2025 |
      | 43  | Oozz         | $87.35 | 4.48  | 5.41   | 10/06/2025 |
      | 44  | Shuffledrive | $88.31 | 2.06  | 2.39   | 10/11/2025 |
      | 45  | Yakitri      | $69.33 | 2.72  | 4.08   | 10/06/2025 |
      | 46  | Linkbuzz     | $70.51 | 0.07  | 0.1    | 10/18/2025 |
      | 47  | Wordpedia    | $26.92 | -4.43 | -14.13 | 10/13/2025 |
      | 48  | Yabox        | $76.81 | 2.59  | 3.49   | 10/10/2025 |
      | 49  | Dynabox      | $64.65 | -2.11 | -3.16  | 10/06/2025 |
      | 50  | Topicstorm   | $87.72 | 4.28  | 5.13   | 10/04/2025 |
      | 51  | Realpoint    | $82.67 | 2.54  | 3.17   | 10/10/2025 |
      | 52  | Vimbo        | $56.51 | -0.43 | -0.76  | 10/16/2025 |
      | 53  | Babbleset    | $24.72 | -1.85 | -6.96  | 10/11/2025 |
      | 54  | Myworks      | $59.48 | -1.99 | -3.24  | 10/09/2025 |
      | 55  | Kazio        | $75.84 | 4.58  | 6.43   | 10/06/2025 |
      | 56  | Linkbridge   | $60.95 | 2.28  | 3.89   | 10/15/2025 |
      | 57  | Quinu        | $55.00 | 2.7   | 5.16   | 10/12/2025 |
      | 58  | Wikivu       | $57.09 | -4.92 | -7.93  | 10/18/2025 |
      | 59  | Yata         | $29.53 | -2.92 | -9     | 10/17/2025 |
      | 60  | Feedfish     | $62.17 | 0.94  | 1.54   | 10/03/2025 |
      | 61  | Trudoo       | $56.56 | 0.4   | 0.71   | 10/15/2025 |
      | 62  | Kazio        | $22.92 | 0.17  | 0.75   | 10/06/2025 |
      | 63  | Quamba       | $26.54 | 2.38  | 9.85   | 10/14/2025 |
      | 64  | Eadel        | $80.18 | 2.63  | 3.39   | 10/16/2025 |
      | 65  | Wikibox      | $84.65 | 4.18  | 5.19   | 10/05/2025 |
      | 66  | Youopia      | $64.06 | 4.28  | 7.16   | 10/05/2025 |
      | 67  | Edgeblab     | $30.60 | -4.12 | -11.87 | 10/10/2025 |
      | 68  | JumpXS       | $27.65 | -4.44 | -13.84 | 10/14/2025 |
      | 69  | Skyvu        | $57.77 | -2.65 | -4.39  | 10/04/2025 |
      | 70  | Flipbug      | $80.04 | 0.31  | 0.39   | 10/13/2025 |
      | 71  | Wordtune     | $53.64 | -0.2  | -0.37  | 10/02/2025 |
      | 72  | Kamba        | $84.58 | 2.47  | 3.01   | 10/17/2025 |
      | 73  | Skyble       | $37.61 | 0.3   | 0.8    | 10/02/2025 |
      | 74  | Lajo         | $89.56 | 4.06  | 4.75   | 10/11/2025 |
      | 75  | Mynte        | $71.19 | -4.55 | -6.01  | 10/18/2025 |
      | 76  | Devbug       | $31.95 | 4.6   | 16.82  | 10/18/2025 |
      | 77  | Trudeo       | $57.00 | -3.41 | -5.64  | 10/12/2025 |
      | 78  | Twimm        | $35.03 | -4.22 | -10.75 | 10/06/2025 |
      | 79  | Edgeblab     | $30.44 | -0.87 | -2.78  | 10/17/2025 |
      | 80  | Yakidoo      | $43.55 | -2.11 | -4.62  | 10/11/2025 |
      | 81  | Jaxspan      | $42.44 | -4.68 | -9.93  | 10/10/2025 |
      | 82  | Realpoint    | $44.58 | -1.62 | -3.51  | 10/14/2025 |
      | 83  | Voonyx       | $71.83 | 0.21  | 0.29   | 10/05/2025 |
      | 84  | Gabtune      | $39.98 | 3.11  | 8.44   | 10/15/2025 |
      | 85  | Topicblab    | $79.32 | 0.16  | 0.2    | 10/03/2025 |
      | 86  | Realbridge   | $69.83 | 1.82  | 2.68   | 10/14/2025 |
      | 87  | Oyoba        | $47.04 | 1.63  | 3.59   | 10/14/2025 |
      | 88  | Tambee       | $29.26 | 0.14  | 0.48   | 10/07/2025 |
      | 89  | Gabtune      | $82.52 | 2.56  | 3.2    | 10/13/2025 |
      | 90  | Skiptube     | $20.15 | 1.45  | 7.75   | 10/17/2025 |
      | 91  | Skimia       | $56.50 | -1.5  | -2.59  | 10/08/2025 |
      | 92  | Jaxworks     | $87.72 | -0.9  | -1.02  | 10/18/2025 |
      | 93  | Quatz        | $29.71 | -0.48 | -1.59  | 10/02/2025 |
      | 94  | Gigashots    | $58.79 | 1.67  | 2.92   | 10/06/2025 |
      | 95  | Edgeblab     | $33.14 | -0.74 | -2.18  | 10/08/2025 |
      | 96  | Vipe         | $60.94 | -2.67 | -4.2   | 10/18/2025 |
      | 97  | Zoonder      | $36.20 | 1.86  | 5.42   | 10/06/2025 |
      | 98  | Zoovu        | $52.25 | 1.07  | 2.09   | 10/14/2025 |
      | 99  | Kamba        | $49.84 | 2.32  | 4.88   | 10/16/2025 |
      | 100 | Twimm        | $56.27 | -4.32 | -7.13  | 10/01/2025 |

  Scenario: Verify grid parallel big
    Given I open extjs6 app and add "#row-editing" path
    Then I verify parallel if grid has size 858

  Scenario: Stop
    And I stop