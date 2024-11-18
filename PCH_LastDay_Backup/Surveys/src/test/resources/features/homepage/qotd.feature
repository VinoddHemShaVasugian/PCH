#Author: vsankar@pch.com
@Desktop @Mobile @Regression @RT-08038
Feature: Verify QOTD feature.
  In order to verify QOTD flow
  as a new user I need to land on the surveys tab

  Scenario Outline: 1 Verify the Initial state of QOTD for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    Then I verify 'Initial' state of QOTD module

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 2 "<userType>" user landed on QOTD page and verify the UI elements.
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    When I click 'LetsGo' on QOTD module
    Then I verify the UI elements on QOTD page
    And I verify the OfficialRules link redirection
    And I verify the Sweepstake Facts link redirection
    And I verify the Privacy Policy link redirection

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |
      | silver   |

  Scenario Outline: 3 "<userType>" user completed QOTD and verify the status on homepage.
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    When I click 'LetsGo' on QOTD module
    And I complete QOTD module for "<userType>" user
    Then I verify the UI elements on SAVE confirmation popup
    When I accept the SAVE confirmation popup
    Then I verify the QAPI page
    When I switch to the Surveys Page
    Then I verify 'Incomplete' state of QOTD module
    And I click on My Account
    And I verify the awarded token of 'qotdModuleToken' with description 'qotdTokenAwardDescription' for survey activity '1'

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |

  Scenario Outline: 4 "<userType>" verify the EXIT functionality on QOTD page.
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    When I click 'LetsGo' on QOTD module
    And I click EXIT button
    Then I verify the UI elements on EXIT confirmation popup
    When I accept the EXIT confirmation popup
    Then I verify 'Initial' state of QOTD module

    Examples: 
      | userType |
      | full reg |
      | mini reg |
