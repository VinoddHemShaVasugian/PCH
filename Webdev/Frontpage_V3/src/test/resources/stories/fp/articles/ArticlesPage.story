Narrative:
In order to verify the story/article details logs in database
As a user 
I want to Read the article from Frontpage site and verify the story details on story log table

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:StoryClaimTokens}

Scenario: verify the Article page by claiming the tokens
Meta:
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When Click on any article from Top stories
Then Verify tokens are claimed
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'

Scenario: Verify the story details on story_log table for Full Reg user
Given Register a fully registered user through RF and login to the site
When Click on any article from Top stories
Then Verify the Story log details for featured article and claimed status '1'
And User redirect to the Frontpage application
And Verify the Story log details for category page articles
And Verify the Story log details for sub category page articles

Scenario: Verify the story details on story_log table for Mini reg user type
Given Register a mini registered user through RF and login to the site
When Click on any article from Top stories
And Verify complete registration button on the uninav
Then Verify complete registration message on article page
And Verify the Story log details for featured article and claimed status '0'
And Do complete registration to become full reg user from Mini Reg user
And Verify user successfully landed on homepage
And Verify the Story log details for featured article and claimed status '1'

Scenario: Verify the story details on story_log table for Social user type
Given Register a social user through RF and login to the site
When Click on any article from Top stories
And Verify complete registration button on the uninav
Then Verify complete registration message on article page
And Verify the Story log details for featured article and claimed status '0'
And Complete registration to become full reg user from social reg user
And Verify user successfully landed on homepage
And Verify the Story log details for featured article and claimed status '1'

Scenario: Verify the story details on story_log table for Silver user type
Given Register a silver user through RF and login to the site
When Click on any article from Top stories
And Verify complete registration button on the uninav
Then Verify complete registration message on article page
And Verify the Story log details for featured article and claimed status '0'
And Do complete registration to become fully registered user from Silver User
And Verify user successfully landed on homepage
And Verify the Story log details for featured article and claimed status '1'