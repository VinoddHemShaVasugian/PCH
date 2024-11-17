Narrative:
In order to verify scratchpath game
As a Tester
I want to retrive scratchcard article details from admin and read/watch articles and video in Frontpage site.

Meta: 
@TestingType Regression

Lifecycle:
Before:
Scope: STORY
Given Register a fully registered user through RF and login to the site
When Verify the daily bonus game info window
Then Do a search and verify daily bonus game progress bar
And Read articles and verify daily bonus game progress bar
And Claim Horoscope tokens and verify daily bonus game progress bar
And Claim Lottery tokens and verify daily bonus game progress bar
And Claim Weather tokens and verify daily bonus game progress bar
And Verify the presence of play bonus game icon
And Verify the play bonus game property on database '1'

Scenario: Play daily bonus game and verify Scratchpath scenarios.
Meta: 
@TestingType Sanity
GivenStories: stories/fp/jm/FpAdmin.story#{id:Scratchcard}
Given Login to the FP Application as recent user
When Navigate to scratchcard page
Then Verify the application redirected to Scratchcard page
And Verify right rail ad '300','250'
And Verify inline ad '728','90'
And Verify right rail multiple ad '300','600','300','250'
And Play and verify scratchpath games
And Verify the application redirected to homepage instead of scratchcard page
And Verify the absence of play bonus game icon
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'
And Verify the play bonus game property on database '2'