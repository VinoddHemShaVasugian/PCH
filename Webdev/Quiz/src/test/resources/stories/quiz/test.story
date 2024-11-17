Narrative:
In order to verify registration on quiz tab
As a user 
I want to launch the quiz tab

Meta:
@TestingType Regression

Scenario: Verify optIn lightbox for organic user
Given Register a user without the Optins
When Verify user landed on quiz tab homepage
Then User redirect to the quiz tab
And Verify the presence of opt-in light box
And Signup for optIn and verify confirmation message
And Navigate to Token History Page
And Verify token transaction details based on admin configured article '5000','Email Sign up Bonus Tokens!','1'