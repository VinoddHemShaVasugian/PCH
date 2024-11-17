Narrative:
In order to verify Internal Ad market tiles
As a Tester
I want to click on ad tile and verify the page redirection and search activity

Meta:
@TestCaseId RT-04305
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:ContestKeys},stories/sw/jm/SearchAdmin.story#{id:TokensFirstSearch}

Lifecycle:
After:
Scope: STORY
Outcome: ANY
Given Login to the Search Admin
And Do a search for an article 'config-sitepagesearch7'
And Modify the field by its key name 'pch_admarketplace' with the given value 'false'
And User redirect to the Search application 'ClearCache'

Scenario: Verify PCH Internal Ad tiles search activity
GivenStories: stories/sw/jm/SearchAdmin.story#{id:EnablePCHInternalTiles}
Given Register a fully registered user through RF and login to the site
Then Redirect the user to 'search7' page
And Do a search by click on ad market place tiles and Verify SERP page
And Do a refresh and verify the first search and consecutive tokens
And Verify contest entry 'Contest Keys / Search / FirstSearch','Contest Key'
And Verify the daily search count as '1' and 'pch_tileclick_tracking' property contains the ad tile id
And Click on the same ad tile and verify no tokens are awarded
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','alternative description','1'

Scenario: Verify Ad MArket tiles are disappears when click on mulitple times
GivenStories: stories/sw/jm/SearchAdmin.story#{id:AdMarketPlaceClickLimit}
Given Register a fully registered user through RF and login to the site
Then Redirect the user to 'search7' page
And Do a search by click on ad market place tiles and Verify SERP page
And Click on the same ad tile for configured number of times based on property 'clickLimit'
And Verify the absence of the clicked tile

Scenario: Verify search activity log table for the Pch Internal Tile search activity
Given Register a fully registered user through RF
And Assign the user to the given 'SES' segment
When Login to the application with the EDID, TSRC, TSRC2, Segid as 'exp165160', 'search_swemail', '17s5265' and 'SWJanConT5'
Then Redirect the user to 'search7' page
And Do a search by click on ad market place tiles and Verify SERP page
And Verify the pch internal tile search activity details in the search activity log table





