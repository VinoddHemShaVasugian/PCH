Narrative:
In order to verify registration on quiz tab
As a user 
I want to launch the quiz tab

Meta:
@TestingType Regression
GivenStories: stories/quiz/jm/QuizAdmin.story#{id:RegistrationTokens}

Scenario: Verify registration functionality for quiz tab
Meta: 
@TestingType Sanity
Given Register a user with the Optins
When Verify user landed on quiz tab homepage
Then Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'