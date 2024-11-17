Narrative:
In order to verify the content in Homepage
As a user 
I want to launch the quiz site

Meta:
@TestingType Regression

Scenario: Verify Quiz completion in Scroll view for Full reg user
Meta: 
@TestingType Sanity
Given Register a user with the Optins
When Navigate to Trending quiz page
Then Play quiz and Verify GOS page
And Retrive legacy GOS details
And Verify legacy GOS details
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','PCHQuiz Completion','1'