Feature: Verify PchSurvey logo and links are available on other PCH properties
  In order to verify survey links and logo on PCH properties
  user needs to land on the surveys page and navigate to other properties

  @RT-06019 @Desktop
  Scenario Outline: 1 Verify the PCHSurveys logo on PCHSurveys page Desktop with different user types
    When I land on the Surveys Page as a existing user with email "pchengineer1fc728f1.04.03.23@pchmail.com" and gmt "34651555-2685-4f99-b079-0b6558752ae1"
    And I close the Take a Tour popup
    Then I verify PchSurveys logo is present on page

    Examples: 
      | email                     | gmt                                  |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec |

  @RT-06019 @Desktop
  Scenario Outline: 2 Verify the PCHSurveys logo on Surveys FAQ page Desktop with different user types
    When I land on the Surveys Page as a existing user with email "<email>" and gmt "<gmt>"
    And I close the Take a Tour popup
    And I click on "Survey FAQ's" icon
    Then I verify tabpage title is "FAQ Surveys [pipe] PCHSurveys"
    And I verify PchSurveys logo is present on page
    When I click on "PCHsurveys" icon
    Then I verify page title is "Surveys | PCHSurveys"

    Examples: 
      | email                     | gmt                                  |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec |

  @RT-06019 @Desktop
  Scenario Outline: 3 Verify the PCHSurveys logo on Surveys Loyalty Benefits page Desktop with different user types
    When I land on the Surveys Page as a existing user with email "<email>" and gmt "<gmt>"
    And I close the Take a Tour popup
    And I click on "Loyalty Benefits" icon
    Then I verify tabpage title is "Benefits Surveys [pipe] PCHSurveys"
    And I verify PchSurveys logo is present on page
    When I click on "PCHsurveys" icon
    Then I verify page title is "Surveys | PCHSurveys"

    Examples: 
      | email                     | gmt                                  |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec |

  # Due to long page load times this set of desktop scenarios can not be automated
  #@RT-06019 @Desktop
  #Scenario Outline: 4 Verify the PCHSurveys icon in available and functional in PCHCOM sites Desktop full reg user
  #When I land on the Surveys Page as a existing user with email "fullreguser@pchmail.com" and gmt "36fee6ac-0c25-4dd6-9b16-91c7935be29b"
  #And I close the Take a Tour popup
  #When I click on "<linktext>" icon
  #Then I verify page title is "<pagetitle>"
  #Then I verify menu icon "PCHsurveys" is present on page
  #When I click on "PCHsurveys" icon
  #Then I verify page title is "Surveys | PCHSurveys"
  #
  #Examples:
  #| linktext      | pagetitle                                                                 |
  #| PCH.com       | Free Online Sweepstakes & Contests [pipe] PCH.com                         |
  #| VIP           | VIP [pipe] PCH.com                                                        |
  #| Quizzes       | Quizzes [pipe] Free Online Quizzes [pipe] PCHquizzes                      |
  #| Slots         | Slots [pipe] PCH.com                                                      |
  #| Instant Win   | Instant Win Games [pipe] Play PCH Instant Win Games Today! [pipe] PCH.com |
  #| Scratch Offs  | SC Title [pipe] PCH.com                                                   |
  #| Sweepstakes   | PCH Sweepstakes [pipe] Win Free Money from PCH [pipe] PCH.com             |
  #| Winners       | Winners [pipe] PCH.com                                                    |
  #| The Good Life | GoodLife [pipe] PCH.com                                                   |
  #| Games         | Games                                                                     |
  @RT-06019 @Mobile
  Scenario Outline: 1 Verify the PCHSurveys logo on Surveys FAQ page Mobile
    When I land on the Surveys Page as a existing user with email "<email>" and gmt "<gmt>"
    And I close the Take a Tour popup
    And I click on "Survey FAQ's" icon
    Then I verify tabpage title is "FAQ Surveys [pipe] PCHSurveys"
    When I click and open Hamburger Menu
    And I click on "Surveys" icon
    Then I verify page title is "Surveys | PCHSurveys"

    Examples: 
      | email                     | gmt                                  |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec |

  @RT-06019 @Mobile
  Scenario Outline: 2 Verify the PCHSurveys logo on Surveys Loyalty Benefits page Mobile
    When I land on the Surveys Page as a existing user with email "<email>" and gmt "<gmt>"
    And I close the Take a Tour popup
    And I click on "Loyalty Benefits" icon
    Then I verify tabpage title is "Benefits Surveys [pipe] PCHSurveys"
    When I click and open Hamburger Menu
    When I click on "Surveys" icon
    Then I verify page title is "Surveys | PCHSurveys"

    Examples: 
      | email                     | gmt                                  |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec |

  @RT-06019 @Mobile
  Scenario Outline: 3 Verify the PCHSurveys icon in available and functional in PCHCOM sites Mobile
    When I land on the Surveys Page as a existing user with email "<email>" and gmt "<gmt>"
    And I close the Take a Tour popup
    When I click and open Hamburger Menu
    And I click on "<linktext>" icon
    Then I verify tabpage title is "<pagetitle>"
    When I click and open Hamburger Menu
    Then I verify menu icon "Surveys" is present on page
    When I click on "Surveys" icon
    Then I verify page title is "Surveys | PCHSurveys"

    Examples: 
      | email                     | gmt                                  | linktext      | pagetitle                                                                    |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | Home          | Free Online Sweepstakes & Contests [pipe] PCH.com                            |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | VIP           | VIP [pipe] PCH.com                                                           |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | Games         | Games                                                                        |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | Quizzes       | Quizzes [pipe] Free Online Quizzes [pipe] PCHquizzes                         |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | Slots         | Slots [pipe] PCH.com                                                         |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | Instant Win   | Instant Win Games [pipe] Play PCH Instant Win Games Today! [pipe] PCH.com    |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | Scratch Offs  | Online Scratch Off Games [pipe] Play PCH Scratch Games Today! [pipe] PCH.com |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | Sweepstakes   | PCH Sweepstakes [pipe] Win Free Money from PCH [pipe] PCH.com                |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | Winners       | Winners [pipe] PCH.com                                                       |
      | fullreguser@pchmail.com   | 36fee6ac-0c25-4dd6-9b16-91c7935be29b | The Good Life | GoodLife [pipe] PCH.com                                                      |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | Home          | Free Online Sweepstakes & Contests [pipe] PCH.com                            |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | VIP           | VIP [pipe] PCH.com                                                           |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | Games         | Games                                                                        |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | Quizzes       | Quizzes [pipe] Free Online Quizzes [pipe] PCHquizzes                         |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | Slots         | Slots [pipe] PCH.com                                                         |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | Instant Win   | Instant Win Games [pipe] Play PCH Instant Win Games Today! [pipe] PCH.com    |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | Scratch Offs  | Online Scratch Off Games [pipe] Play PCH Scratch Games Today! [pipe] PCH.com |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | Sweepstakes   | PCH Sweepstakes [pipe] Win Free Money from PCH [pipe] PCH.com                |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | Winners       | Winners [pipe] PCH.com                                                       |
      | minireguser@pchmail.com   | fede9f9c-4b43-4d28-98b7-a33b27c75dc2 | The Good Life | GoodLife [pipe] PCH.com                                                      |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | Home          | Free Online Sweepstakes & Contests [pipe] PCH.com                            |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | VIP           | VIP [pipe] PCH.com                                                           |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | Games         | Games                                                                        |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | Quizzes       | Quizzes [pipe] Free Online Quizzes [pipe] PCHquizzes                         |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | Slots         | Slots [pipe] PCH.com                                                         |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | Instant Win   | Instant Win Games [pipe] Play PCH Instant Win Games Today! [pipe] PCH.com    |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | Scratch Offs  | Online Scratch Off Games [pipe] Play PCH Scratch Games Today! [pipe] PCH.com |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | Sweepstakes   | PCH Sweepstakes [pipe] Win Free Money from PCH [pipe] PCH.com                |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | Winners       | Winners [pipe] PCH.com                                                       |
      | socialreguser@pchmail.com | dd8934b9-f1c0-4485-9d57-a397a8879eec | The Good Life | GoodLife [pipe] PCH.com                                                      |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | Home          | Free Online Sweepstakes & Contests [pipe] PCH.com                            |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | VIP           | VIP [pipe] PCH.com                                                           |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | Games         | Games                                                                        |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | Quizzes       | Quizzes [pipe] Free Online Quizzes [pipe] PCHquizzes                         |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | Slots         | Slots [pipe] PCH.com                                                         |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | Instant Win   | Instant Win Games [pipe] Play PCH Instant Win Games Today! [pipe] PCH.com    |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | Scratch Offs  | Online Scratch Off Games [pipe] Play PCH Scratch Games Today! [pipe] PCH.com |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | Sweepstakes   | PCH Sweepstakes [pipe] Win Free Money from PCH [pipe] PCH.com                |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | Winners       | Winners [pipe] PCH.com                                                       |
      | silverreguser@pchmail.com | 922258f4-f68e-4daa-bb55-7e329d7dde0a | The Good Life | GoodLife [pipe] PCH.com                                                      |
