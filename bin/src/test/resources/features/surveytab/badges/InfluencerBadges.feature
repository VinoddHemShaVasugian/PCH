#Author: vsankar@pch.com
Feature: Verify the badges awarding feature for Influencer, Influencer Pro and Influencer Pro Plus badges.
  In order to award Influencer, Influencer Pro and Influencer Pro Plus badges
  as a user I need to first sign in or create new registration
  and complete a survey for Influencer badge
  and complete 2 surveys for Influencer Pro badge
  and complete 3 surveys for Influencer Pro Plus badge

  @Desktop @Mobile
  Scenario: User complete a survey and verify the influencer badge awarded for user.
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I pause execution for 10 seconds
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User complete 2 surveys and verify the influencer pro badge awarded for user.
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I pause execution for 10 seconds
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"
    When I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I pause execution for 10 seconds
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User complete 3 surveys and verify the influencer pro plus badge awarded for user.
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I pause execution for 10 seconds
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Badge! Now go for more!"
    When I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I pause execution for 10 seconds
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro Badge! Now go for more!"
    When I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I pause execution for 10 seconds
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro+"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro+ Badge! Now go for more!"
