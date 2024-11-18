#Author: mmittal@pch.com
@Desktop @Mobile @Regression @RT-07687 @RT-07705 @RT-07707
Feature: Verify locking and unlocking of bonus game

  Scenario: Guest User does not see Play Now button in bonus game
    When I land on the Surveys Page as a guest user
    And I close the Take a Tour popup
    Then Bonus game is locked

  Scenario: Bonus game is unlocked after completing a survey
    When I land on the Surveys Page as a recognized full reg user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I close Stack the rewards window
    Then I am able to click on Play Now button
 
      | userType |
      | full reg |

      