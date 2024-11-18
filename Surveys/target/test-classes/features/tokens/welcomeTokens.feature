#Author: vsankar@pch.com
@Desktop @Mobile @Regression @RT-07378
Feature: Verify welcome token amount as per admin config
  In order to verify the welcome tokens amount
  user needs to register on survey tab and navigate to token activity page.

  Scenario Outline: 1 guest user register on Survey tab and verify welcome tokens
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And navigate to registration page
    And I create a full reg user
    And I click on My Account
    Then I verify the awarded token of 'welcomeTokenDefaultUserLevel' with description 'welcomeTokenDefaultUserLevelDesc' for survey activity '1'
    And I verify the awarded token of 'createPasswordTokens' with description 'createPasswordDescription' for survey activity '1'

    @Sanity
    Examples: 
      | userType |
      | guest    |