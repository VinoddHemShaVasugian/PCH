#Author: vsankar@pch.com
Feature: Verify the badges awarding feature for Influencer Pro badge.
  In order to award Influencer Pro badge
  as a user I need to first sign in or create new registration
  and complete 2 surveys for Influencer Pro badge.

  @Desktop @Mobile
  Scenario Outline: User complete 2 surveys and verify the influencer pro badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    #And I click first Take Survey Button
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro Badge! Now go for more!"

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |

  @Desktop @Mobile
  Scenario Outline: User completes 2 survey and verify the influencer pro badge awarded for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    #And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url by creating password for NoPassword user
    Then I verify badge header text is "Recently Received"
    And I verify badge title text is "Influencer Pro"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Influencer Pro Badge! Now go for more!"

    Examples: 
      | userType |
      | silver   |
      | social   |