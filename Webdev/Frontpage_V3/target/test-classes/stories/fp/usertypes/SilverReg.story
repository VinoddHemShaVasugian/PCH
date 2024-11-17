Narrative:
In order to verify the Silver reg user functionality
As a user 
I want to Create a user from RF API and navigate to Frontpage site

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:ContestKeys}

Scenario: Verify the complete registration functionality for Silver user type
Given Register a silver user through RF and login to the site
When Verify complete registration button on the uninav
Then Verify the N1 VIP message
And Do complete registration to become fully registered user from Silver User
And Verify user successfully landed on homepage
And Do a search and Verify SERP page
And Verify the L1 VIP message

Scenario: Do a search and verify the complete registration functionality for Silver user type
Given Register a silver user through RF and login to the site
When Verify complete registration button on the uninav
Then Verify the N1 VIP message
And Perform consecutive searches '3'
And User redirect to the Frontpage application
And Do complete registration to become fully registered user from Serp page
And Verify the user is full reg
And Verify contest entry 'Contest Keys / Frontpage / FirstSearch','Contest Key'