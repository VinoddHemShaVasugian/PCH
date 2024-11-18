#Author: vsankar@pch.com
@RT-08014 @RT-07477
Feature: Verify the user level upgrade, Survey complete count and badges awarding feature for Influencer Pro Plus badge.
  In order to award Influencer Pro Plus badge
  as a user I need to first sign in or create new registration
  and complete 3 surveys or complete basic profile and 2 surveys for Influencer Pro Plus badge

  @Desktop @Mobile
  Scenario Outline: 1 User completes 9 surveys and the basic profile to verify the influencer Pro Plus badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer Pro Plus" badge is locked
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
    And I navigates to survey 'Auto_Lucid_Tokens'
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
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify surveys completed count is "10"
    And I verify badge header text is "Recently Received"
    When I navigates to Badges page
    Then I verify "Influencer Pro Plus" badge is unlocked
    And I verify info text on "Influencer Pro Plus" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProLevelUpgradeTokenAmt' with description 'influencerProLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProPlusLevelUpgradeTokenAmt' with description 'influencerProPlusLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |
      | silver   |

  @Desktop @Mobile
  Scenario Outline: 2 User complete 10 surveys and verify the influencer pro plus badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer Pro Plus" badge is locked
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
    Then I verify surveys completed count is "10"
    And I verify badge header text is "Recently Received"
    When I navigates to Badges page
    Then I verify "Influencer Pro Plus" badge is unlocked
    And I verify info text on "Influencer Pro Plus" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProLevelUpgradeTokenAmt' with description 'influencerProLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProPlusLevelUpgradeTokenAmt' with description 'influencerProPlusLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |

  @Desktop @Mobile
  Scenario Outline: 3 User completes 10 surveys and verify the influencer pro plus badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer Pro Plus" badge is locked
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
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify surveys completed count is "10"
    And I verify badge header text is "Recently Received"
    When I navigates to Badges page
    Then I verify "Influencer Pro Plus" badge is unlocked
    And I verify info text on "Influencer Pro Plus" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProLevelUpgradeTokenAmt' with description 'influencerProLevelUpgradeTokenDesc' for survey activity '1'
    And I verify the awarded token of 'influencerProPlusLevelUpgradeTokenAmt' with description 'influencerProPlusLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | silver   |
