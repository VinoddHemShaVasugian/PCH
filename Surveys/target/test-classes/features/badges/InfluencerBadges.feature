#Author: vsankar@pch.com
Feature: Verify the badges awarding feature for Influencer badge.
  In order to award Influencer badge
  as a user I need to first sign in or create new registration
  and complete a survey for Influencer badge.

  @Desktop @Mobile
  Scenario Outline: User complete a survey and verify the influencer badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |

  @Desktop @Mobile
  Scenario Outline: User complete a survey and verify the influencer badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"

    Examples: 
      | userType |
      | silver   |
      | social   |
