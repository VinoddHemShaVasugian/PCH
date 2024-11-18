#Author: vmohanan@pch.com
@Desktop @Mobile @Regression
Feature: Title of your feature
  I want to use this template for my feature file

  Scenario Outline: 1 Verify the Token Titan inactive badge is displayed on the badges page for the "<userType>" usertype
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Token Titan" badge is locked
    And I navigates to survey 'Auto_SurveyToken_6L'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    And I navigates to Badges page
    Then I verify "Token Titan" badge is unlocked
    And I verify awarded token amount "titanTokenBadgeTokenAmt"
    And I navigates to survey 'Auto_SurveyToken_6L'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to Badges page
    Then I verify "Token Titan Gold" badge is unlocked
    And I verify awarded token amount "titanTokenGoldBadgeTokenAmt"
    And I verify info text on "Influencer Pro" badge

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |
