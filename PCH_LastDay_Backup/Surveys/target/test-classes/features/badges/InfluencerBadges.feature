#Author: vsankar@pch.com
@RT-07480 @RT-07473 @Desktop @Mobile @Regression
Feature: Verify the user level upgrade, Survey complete count and badges awarding feature for Influencer badge.
  In order to award Influencer badge
  as a user I need to first sign in or create new registration
  and complete a survey or complete basic profile for Influencer badge.

  Scenario Outline: 1 User completes the basic profile and 2 surveys. verify the influencer badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer" badge is locked
    When I click on Back to Surveys link
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify surveys completed count is "3"
    And I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"
    When I navigates to Badges page
    Then I verify "Influencer" badge is unlocked
    And I verify info text on "Influencer" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |
      | silver   |

  Scenario Outline: 2 User completes 3 surveys and verify the influencer badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer" badge is locked
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
    Then I verify surveys completed count is "3"
    And I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"
    When I navigates to Badges page
    Then I verify "Influencer" badge is unlocked
    And I verify info text on "Influencer" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |

  Scenario Outline: 3 User completes 3 surveys and verify the influencer badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to Badges page
    Then I verify "Influencer" badge is locked
    When I click on Back to Surveys link
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify surveys completed count is "3"
    And I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"
    When I navigates to Badges page
    Then I verify "Influencer" badge is unlocked
    And I verify info text on "Influencer" badge
    When I click on Back to Surveys link
    And I click on My Account
    Then I verify the awarded token of 'influencerLevelUpgradeTokenAmt' with description 'influencerLevelUpgradeTokenDesc' for survey activity '1'

    Examples: 
      | userType |
      | silver   |
