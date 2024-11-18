#Author: vsankar@pch.com
#@Desktop @Mobile @Regression
#Feature: Verify awarded token amount as per admin config for Survey handraiser
  #In order to verify awarded tokens for Survey handraiser
  #user needs to click handraiser module on homepage and navigate to token activity page.
#
  #Scenario Outline: 1 "<userType>" user claim survey handraiser.
    #Given I land on the Surveys Page as a "<userType>" user
    #When I close the Take a Tour popup
    #And I click the Yes Im In button in survey handraiser section for "<userType>"
    #And I click on My Account
    #Then I verify the awarded token of 'handraiserToken' with description 'handraiserTokenDesc' for survey activity '1'
#
    #Examples: 
      #| userType |
      #| guest    |
      #| full reg |
      #| mini reg |
#
  #Scenario Outline: 2 "<userType>" user claim survey handraiser.
    #Given I land on the Surveys Page as a "<userType>" user
    #When I close the Take a Tour popup
    #And I click the Yes Im In button in survey handraiser section for "<userType>"
    #And I click on My Account
    #Then I verify the awarded token of 'handraiserToken' with description 'handraiserTokenDesc' for survey activity '1'
    #And I verify the awarded token of 'createPasswordTokens' with description 'createPasswordDescription' for survey activity '1'
#
    #Examples: 
      #| userType |
      #| silver   |
