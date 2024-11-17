Narrative:
In order to verify the Mini reg user functionality
As a user 
I want to check Mini reg user lands on search&win site and perform activity

Meta:
@TestCaseId RT-04324
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:ContestKeys}

Scenario: Create the mini reg and verify the functionality
Given Register a mini registered user through RF and login to the site
When Lands on homepage verify complete registration button on the uninav
Then Verify the C1 VIP message
And Do complete registration to become full reg user from Mini Reg user
And User successfully landed on the search&win homepage
And Verify the C1 VIP message after complete registration

Scenario: Create the mini reg do first search then complete registration
Given Register a mini registered user through RF and login to the site
When Lands on homepage verify complete registration button on the uninav
Then Do a search and Verify SERP page
And Verify the presence of the complete reg light box
And Do complete reg from complete reg Lightbox
And Verify the user is full reg
And Verify contest entry 'contest keys / search / firstsearch','Contest Key'
