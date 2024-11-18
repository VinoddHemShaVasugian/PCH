#Author: vsankar@pch.com
@RT-07479 @RT-07478 @RT-07473 @Desktop @Mobile @Regression
Feature: Verify the user level upgrade, Survey complete count and badges awarding feature for Influencer Pro badge.
  In order to award Influencer Pro badge
  as a user I need to first sign in or create new registration
  and complete 2 surveys or complete basic profile and a survey for Influencer Pro badge.

  Scenario Outline: 1 User completes the basic profile and 4 surveys to verify the influencer Pro badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer Pro" badge is locked
    When I click on Back to Surveys link
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify surveys completed count is "5"
    And I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro Badge! Now go for more!"
    When I navigates to Badges page
    Then I verify "Influencer Pro" badge is unlocked
    And I verify info text on "Influencer Pro" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProLevelUpgradeTokenAmt' with description 'influencerProLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |
      | silver   |

  Scenario Outline: 2 User completes 5 surveys and verify the influencer pro badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer Pro" badge is locked
    When I click on Back to Surveys link
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify surveys completed count is "5"
    And I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro Badge! Now go for more!"
    When I navigates to Badges page
    Then I verify "Influencer Pro" badge is unlocked
    And I verify info text on "Influencer Pro" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProLevelUpgradeTokenAmt' with description 'influencerProLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |

  Scenario Outline: 3 User completes 5 survey and verify the influencer pro badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer Pro" badge is locked
    When I click on Back to Surveys link
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify surveys completed count is "5"
    And I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro Badge! Now go for more!"
    When I navigates to Badges page
    Then I verify "Influencer Pro" badge is unlocked
    And I verify info text on "Influencer Pro" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProLevelUpgradeTokenAmt' with description 'influencerProLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | silver   |
