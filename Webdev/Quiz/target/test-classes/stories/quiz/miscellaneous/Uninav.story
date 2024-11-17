Narrative:
In order to verify uninav features on quiz tab
As a user 
I want to launch the quiz tab

Meta:
@TestingType Regression

Scenario: Verify uninav links on quiz tab for guest user
Meta: 
@TestingType Sanity
Given Go to Quiz tab
When Verify user landed on quiz tab homepage
Then Verify the presence of signin and register button
And Verify the registration page
And User redirect to the quiz tab
And Verify the signin page
And Signin with existing user details
And Verify user landed on quiz tab homepage

Scenario: Verify the redeem rewards on uninav for quiz tab
Given Login to the Quiz Application as recognized user
When Verify redeem tokens icon
Then Verify redeem tokens shelf

Scenario: verify the info pages links on uninav for quiz tab
Given Login to the Quiz Application as recognized user
When Verify user landed on quiz site homepage
Then verify the infopages

Scenario: Verify level up shelf on uninav for quiz tab
Given Login to the Quiz Application as recognized user
When Verify user landed on quiz site homepage
Then Click levelup shelf and verify Playnow button