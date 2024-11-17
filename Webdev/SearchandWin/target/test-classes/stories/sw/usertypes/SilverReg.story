Narrative:
In order to verify the Silver reg user functionality
As a user 
I want to check Silver reg user lands on search&win site and perform activity

Meta:
@TestCaseId RT-04326
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:ContestKeys}

Scenario: Create the Silver reg and verify the functionality
Given Register a silver user through RF and login to the site
When Lands on homepage verify complete registration button on the uninav
Then Verify the N1 VIP message
And Do complete registration to become fully registered user from Silver User
And User successfully landed on the search&win homepage
And Do a search and Verify SERP page
And Verify the L1 VIP message

Scenario: Create the Silver reg and verify the functionality
Given Register a silver user through RF and login to the site
When Lands on homepage verify complete registration button on the uninav
Then Verify the N1 VIP message
And Do a search and Verify SERP page
And Do complete registration to become fully registered user from Serp page
And Verify the user is full reg
And Verify contest entry 'contest keys / search / firstsearch','Contest Key'
