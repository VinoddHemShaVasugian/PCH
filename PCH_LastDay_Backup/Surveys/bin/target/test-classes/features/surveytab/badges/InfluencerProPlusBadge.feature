#Author: vsankar@pch.com
Feature: Verify the badges awarding feature for Influencer Pro Plus badge.
  In order to award Influencer Pro Plus badge
  as a user I need to first sign in or create new registration
  and complete 3 surveys for Influencer Pro Plus badge

  @Desktop @Mobile
  Scenario: User complete 3 surveys and verify the influencer pro plus badge awarded for full reg user.
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Truckmid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'lucidtests'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro+"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro+ Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User complete 3 surveys and verify the influencer pro plus badge awarded for mini reg user.
    When I land on the Surveys Page as a recognized mini reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Truckmid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'lucidtests'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro+"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro+ Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User completes 3 surveys and verify the influencer pro plus badge awarded for silver reg user.
    When I land on the Surveys Page as a recognized silver user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Truckmid'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'lucidtests'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro+"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro+ Badge! Now go for more!"

  @Desktop @Mobile
  Scenario: User completes 3 surveys and verify the influencer pro plus badge awarded for social reg user.
    When I land on the Surveys Page as a recognized social user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Truckmid'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'lucidtests'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro+"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro+ Badge! Now go for more!"
