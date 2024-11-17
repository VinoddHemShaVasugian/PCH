Narrative:
In order to verify the Consecutive visit scenario
As a user
I want to logged into the application with valid user name and password

Meta:
@TestCaseId RT-04275
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:CVContestKeys},stories/sw/jm/SearchAdmin.story#{id:CVTokens}

Scenario: Verify the Consecutive visit for the user
Meta:
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When User redirect to the Search application 'ClearCache'
Then Do a search on homepage and Verify SERP page
And Verify the consecutive visit bar for the Day '1'
And Navigate to Token History Page
And Verify the token message and token amount on Token History page based on admin property 'Alternative Description', 'Tokens','1'
And Verify contest entry 'Contest Keys / Search / Consecutive Search 1','Contest Key'