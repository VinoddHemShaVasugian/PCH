#Author: vsankar@pch.com
@Desktop @Mobile @Regression @RT-07923 @RT-07924 @RT-07925 @RT-07926 @RT-07927 @RT-07174 @RT-07938 @RT-07907
Feature: Verify surveys mission module feature.
  In order to verify survey mission
  as a user I need to complete a required mission module and verify queue in admin.

  Scenario Outline: 1 Complete the Automation Mission module and verify mission status for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions as a "<userType>" user
    And I switch to the Surveys Page
    And I expand 'automationmission' module
    And I fetch the number of steps configured in 'automationmission' module
    And I 'Complete' the mission steps and verify the progress
    Then I verify the 'automationmission' module 'Complete' status
    When I pause execution for '300' seconds
    And I click on My Account
    And I verify the awarded token of 'automationMissionStepCompleteTokenAmt' with description 'For completing mission step1' for survey activity '1'
    And I verify the awarded token of 'automationMissionStepCompleteTokenAmt' with description 'For completing mission step2' for survey activity '1'
    And I verify the awarded token of 'automationMissionStepCompleteTokenAmt' with description 'For completing mission step3' for survey activity '1'
    And I verify the awarded token of 'automationMissionModuleCompleteTokenAmt' with description 'For completing automation mission module' for survey activity '1'
    And Verify contest entry 'automationMissionModuleCompleteContestkey'
    When I execute the cron to 'process-mission-completes'
    And I execute the cron to 'process-amz-gc-queue'
    And I login to offers admin
    And I navigates to Mission Amazon Gift Cards page
    Then I verify the GMT of gift card
    And I verify the transaction status of gift card 'EMAIL SENT'
    And I verify the amount of gift card '25'

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 2 Verify UI elements and descriptions on mission module for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions as a "<userType>" user
    And I switch to the Surveys Page
    Then I verify the mission module on homepage
    And I verify the number of mission modules as per admin config '4'
    And I verify an info icon on mission module
    Then I verify the UI elements in mission module collapsed state
    When I expand 'automationmission' module
    Then I verify the UI elements in mission module expanded state
    And I verify the description on incomplete mission module "automationMissionIncompleteDescriptionText"
    When I fetch the number of steps configured in 'automationmission' module
    And I 'Complete' the mission steps and verify the progress
    Then I verify the 'automationmission' module 'Complete' status
    And I verify the description on complete mission module "automationMissionCompleteDescriptionText"

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |
