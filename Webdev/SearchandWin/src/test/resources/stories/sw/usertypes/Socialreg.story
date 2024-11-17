Narrative:
In order to verify the Social reg user functionality
As a user 
I want to check Social reg user lands on search&win site and perform activity

Meta:
@TestCaseId RT-04325
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:ContestKeys}

Scenario: Create the Social reg and verify the functionality
Given Register a social user through RF and login to the site
When Lands on homepage verify complete registration button on the uninav
Then Complete registration to become full reg user from social reg user
And User successfully landed on the search&win homepage
And Verify the C1 VIP message after complete registration

Scenario: Create the social reg user do first search then complete registration
Given Register a social user through RF and login to the site
When Lands on homepage verify complete registration button on the uninav
Then Do a search and Verify SERP page
And Verify the presence of the complete reg light box for social user
And Do complete reg from complete reg Lightbox to become fullreg user
And Verify the user is full reg
And Verify contest entry 'contest keys / search / firstsearch','Contest Key'