Feature: Verify locking and unlocking of bonus game

 
  @Desktop @Mobile
  Scenario: Guest User does not see Play Now button in bonus game
    When I land on the Surveys Page as a guest user
    And I close the Take a Tour popup
    Then I do not see Play Now Button
    
  @Desktop @Mobile
  Scenario: Bonus game is unlocked after completing a survey
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I pause execution for 20 seconds
    And I am able to click on Play Now button
    And I get redirected to the Bonus game page in the new tab