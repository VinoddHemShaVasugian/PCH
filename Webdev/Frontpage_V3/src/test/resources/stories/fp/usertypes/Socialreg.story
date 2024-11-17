Narrative:
In order to verify the Social reg user functionality
As a user 
I want to Create a user from RF API and navigate to Frontpage site

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:ContestKeys}

Scenario: Verify the complete registration functionality for Social user type
Given Register a social user through RF and login to the site
When Verify complete registration button on the uninav
Then Complete registration to become full reg user from social reg user
And Verify user successfully landed on homepage
And Verify the C1 VIP message after complete registration

Scenario: Do a search and verify the complete registration functionality for Social user type
Given Register a social user through RF and login to the site
When Verify complete registration button on the uninav
Then Perform consecutive searches '3'
And User redirect to the Frontpage application
And Verify the presence of the complete reg light box for social user
And Do complete reg from complete reg Lightbox to become fullreg user
And Verify the user is full reg
And Verify contest entry 'Contest Keys / Frontpage / FirstSearch','Contest Key'