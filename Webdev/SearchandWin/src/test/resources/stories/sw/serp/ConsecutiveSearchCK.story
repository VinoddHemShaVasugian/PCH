Narrative:
In order to verify contest entry 
As a Tester
I want to retrive the consecutive search contest keys in Joomla admin

Meta: 
@TestCaseId RT-04515
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:CSContestKeys}

Scenario: Verify consecutive search contest keys for Fully registered user
Given Register a fully registered user through RF and login to the site
When Do a search on homepage and Verify SERP page
Then Verify contest entry 'Contest Keys / Search / FirstSearch','Contest Key'
And Do a search and Verify SERP page
And Verify contest entry 'Contest Keys / Search / Second Search','Contest Key'
And Do a search and Verify SERP page
And Verify contest entry 'Contest Keys / Search / Third Search','Contest Key'

Scenario: Verify consecutive search contest keys for mini reg user
Given Register a mini registered user through RF and login to the site
When Do a search on homepage and Verify SERP page
Then User redirect to the Search application
And Do a search and Verify SERP page
And Do a search and Verify SERP page
And Do complete registration to become full reg user from Mini Reg user
And Verify contest entry 'Contest Keys / Search / FirstSearch','Contest Key'
And Verify contest entry 'Contest Keys / Search / Second Search','Contest Key'
And Verify contest entry 'Contest Keys / Search / Third Search','Contest Key'

Scenario: Verify consecutive search contest keys for silver user
Given Register a silver user through RF and login to the site
When Do a search on homepage and Verify SERP page
Then User redirect to the Search application
And Do a search and Verify SERP page
And Do a search and Verify SERP page
And Do complete registration to become fully registered user from Silver User
And Verify contest entry 'Contest Keys / Search / FirstSearch','Contest Key'
And Verify contest entry 'Contest Keys / Search / Second Search','Contest Key'
And Verify contest entry 'Contest Keys / Search / Third Search','Contest Key'

Scenario: Verify consecutive search contest keys for social user
Given Register a social user through RF and login to the site
When Do a search on homepage and Verify SERP page
Then User redirect to the Search application
And Do a search and Verify SERP page
And Do a search and Verify SERP page
And Complete registration to become full reg user from social reg user
And Verify contest entry 'Contest Keys / Search / FirstSearch','Contest Key'
And Verify contest entry 'Contest Keys / Search / Second Search','Contest Key'
And Verify contest entry 'Contest Keys / Search / Third Search','Contest Key'