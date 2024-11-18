#Author: vsankar@pch.com
@Desktop @Mobile @Regression @RT-08037 @RT-08033 @RT-08040
Feature: Verify QAPI feature.
  In order to verify QAPI flow
  as a new user I need to complete QOTD and redirect to QAPI page.

  Scenario Outline: 1 "<userType>" user completed QAPI flow and verify the tokens awarded and status on homepage.
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    When I click 'LetsGo' on QOTD module
    Then I verify the disabled status of SAVE button before answering the question
    When I complete QOTD module for "<userType>" user
    And I accept the SAVE confirmation popup
    Then I verify the QAPI page
    And I verify the UI elements on QAPI page
    And I verify the Privacy Policy link redirection on QAPI page
    When I fetch the total number of questions configured in QAPI module
    And I complete QAPI flow and verify the status of progress bar
    And I verify the total tokens awarded on QAPI complete page
    When I click Continue on QAPI complete page
    Then I verify 'Completed' state of QOTD module
    And I click on My Account
    And I verify the awarded token of 'qotdModuleToken' with description 'qotdTokenAwardDescription' for survey activity '1'
    And I verify the awarded token of 'qotdModuleToken' with description 'qapiTokenAwardDescription' for recent activity

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |

  Scenario Outline: 2 "<userType>" verify the EXIT functionality on QAPI flow.
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    When I click 'LetsGo' on QOTD module
    And I complete QOTD module for "<userType>" user
    And I accept the SAVE confirmation popup
    Then I verify the QAPI page
    When I click EXIT button
    Then I verify the UI elements on EXIT confirmation popup
    When I accept the EXIT confirmation popup
    And I click Continue on QAPI complete page
    Then I verify 'Incomplete' state of QOTD module
    When I click on My Account
    Then I verify the awarded token of 'qotdModuleToken' with description 'qotdTokenAwardDescription' for survey activity '1'

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |

  Scenario Outline: 3 Complete QOTD and QAPI for no password user, verify the status on homepage.
    Given I land on the Surveys Page as a 'silver' user
    And I close the Take a Tour popup
    When I click 'LetsGo' on QOTD module
    And I answer QOTD module
    Then I verify the UI elements on Create password popup
    When I complete create password on QOTD flow
    Then I verify the UI elements on Create password confirmation popup
    When I accept the create password confirmation popup
    Then I verify the QAPI page
    When I switch to the Surveys Page
    Then I verify 'Incomplete' state of QOTD module
    And I click on My Account
    And I verify the awarded token of 'qotdModuleToken' with description 'createPasswordQotdTokenDesc' for survey activity '1'
    And I verify the awarded token of 'createPasswordTokens' with description 'createPasswordDescription' for survey activity '1'
    When I switch to the Surveys Page
    And I click 'LetsGo' on QOTD module
    And I fetch the total number of questions configured in QAPI module
    And I complete QAPI flow and verify the status of progress bar
    And I verify the total tokens awarded on QAPI complete page
    When I click Continue on QAPI complete page
    Then I verify 'Completed' state of QOTD module
    And I click on My Account
    And I verify the awarded token of 'qotdModuleToken' with description 'qapiTokenAwardDescription' for recent activity

  Scenario Outline: 4 Complete QOTD and QAPI for Guest user, verify the status on homepage.
    Given I land on the Surveys Page as a 'guest' user
    And I close the Take a Tour popup
    When I click 'LetsGo' on QOTD module
    And I create a full reg user
    Then I verify the UI elements on SignUp confirmation popup
    When I click continue on SignUp confirmation popup
    And I answer QOTD module
    And I accept the SAVE confirmation popup
    Then I verify the QAPI page
    When I switch to the Surveys Page
    Then I verify 'Incomplete' state of QOTD module
    And I click on My Account
    And I verify the awarded token of 'qotdModuleToken' with description 'createPasswordQotdTokenDesc' for survey activity '1'
    And I verify the awarded token of 'createPasswordTokens' with description 'createPasswordDescription' for survey activity '1'
    When I switch to the Surveys Page
    And I click 'LetsGo' on QOTD module
    And I fetch the total number of questions configured in QAPI module
    And I complete QAPI flow and verify the status of progress bar
    And I verify the total tokens awarded on QAPI complete page
    When I click Continue on QAPI complete page
    Then I verify 'Completed' state of QOTD module
    And I click on My Account
    And I verify the awarded token of 'qotdModuleToken' with description 'qapiTokenAwardDescription' for recent activity
