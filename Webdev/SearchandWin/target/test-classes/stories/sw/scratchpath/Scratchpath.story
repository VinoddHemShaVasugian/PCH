Narrative:
In order to verify the Scratchpath
As a user
I want to logged into the application with valid user name and password

Meta:
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:Scratchpath Redirect},
stories/sw/jm/SearchAdmin.story#{id:Config_Infospace}

Scenario: Verify the user redirection to Scratchcard page for the segmented user
Meta: 
@TestCaseId RT-04274
@TestCaseId RT-04345
@TestCaseId RT-04385
@TestingType Sanity
Given Register a fully registered user through RF
And Assign the user to the segment based on the 'Segments Included' admin property
Then Login to the Search application with the created user
And Verify the application redirected to Scratchcard page
And Verify the presence of the Scratchcard light box

Scenario: Verify the user redirection to Scratchcard page for the blocked segment
Given Register a fully registered user through RF
And Assign the user to the segment based on the 'Segments Included' admin property
Then Login to the Search application with blocked segid from property 'Conditions'
And Verify the application redirected to homepage instead of scratchcard page

Scenario: Verify the Scratchcard games and algo results for the segmented user
Given Register a fully registered user through RF
When Assign the user to the segment based on the 'Segments Included' admin property
Then Login to the Search application with the created user
And Verify the application redirected to Scratchcard page
And Play and verify scratchpath games
And Verify the application redirected to homepage instead of scratchcard page
And Do a search and Verify SERP page
And Verify algo NFSP source and segment as per admin config 'nfspAccessIds','mx_d','mx_t','mx_m'