#Author: vsankar@pch.com
Feature: Verify the badges awarding feature for Influencer badge.
  In order to award Influencer badge
  as a user I need to first sign in or create new registration
  and complete a survey for Influencer badge.

  @Desktop @Mobile
  Scenario: User complete a survey and verify the influencer badge awarded for guest user.
    When I land on the Surveys Page as a guest user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User complete a survey and verify the influencer badge awarded for Full reg user.
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User complete a survey and verify the influencer badge awarded for mini reg user.
    When I land on the Surveys Page as a recognized mini reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User complete a survey and verify the influencer badge awarded for silver reg user.
    When I land on the Surveys Page as a recognized silver user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User complete a survey and verify the influencer badge awarded for social reg user.
    When I land on the Surveys Page as a recognized social user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"
