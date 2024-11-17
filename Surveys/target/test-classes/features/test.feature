#Author: vsankar@pch.com
Feature: Verify the survey onsite messaging popup.
  In order to display the onsite message popup
  as a user I need to land on invalid/expired surveys
  and completes/incompletes a surveys to meets frequency gap.
  and meets the survey error scenarios.

  @Desktop @Mobile
  Scenario Outline: Verify the take survey feature on survey onsite message for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'INVALID_MID'
    And I click ANSWER SOME QUESTIONS button
    Then I verify page title is 'Profile'

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |
      | silver   |
      | social   |