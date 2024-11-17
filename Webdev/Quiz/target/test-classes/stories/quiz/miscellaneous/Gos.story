Narrative:
In order to verify the GOS details
As a user 
I want to launch the quiz site and complete any quiz

Meta:
@TestingType Regression

Scenario: Verify GOS details
Meta: 
@TestingType Sanity
Given Create '<userType>' user and login to quiz tab
When Navigate to Trending quiz page
Then Play quiz and Verify GOS page
And Retrive legacy GOS details
And Verify legacy GOS details

Examples:
|userType|
|guest|
|fully registered|
|mini registered|
|silver|