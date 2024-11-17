Narrative:
In order to verify Search result page
As a Tester
I want to do a search and verify the serp message, Dfp/E-comm ads and search results.

Meta: 
@TestCaseId RT-04504
@TestCaseId RT-04292
@TestCaseId RT-04298
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:SerpMessage}

Scenario: Verify serp page for guest user
Given Goto Search and Win Site
When Do a search and Verify serp message 'Desktop - First Search2'
And Do a search and Verify serp message 'Desktop - Later Search2'
Then Verify SERP page

Scenario: Verify serp page for Fully registered user
Meta: 
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When Do a search and Verify serp message 'Desktop - First Search1'
And Do a search and Verify serp message 'Desktop - Later Search1'
Then Verify SERP page

Scenario: Verify serp page for mini reg user
Given Register a mini registered user through RF and login to the site
When Do complete registration to become full reg user from Mini Reg user
And Do a search and Verify serp message 'Desktop - First Search1'
And Do a search and Verify serp message 'Desktop - Later Search1'
Then Verify SERP page

Scenario: Verify serp page for silver user
Given Register a silver user through RF and login to the site
When Do complete registration to become fully registered user from Silver User
And Do a search and Verify serp message 'Desktop - First Search1'
And Do a search and Verify serp message 'Desktop - Later Search1'
Then Verify SERP page

Scenario: Verify serp page for social user
Given Register a social user through RF and login to the site
When Complete registration to become full reg user from social reg user
And Do a search and Verify serp message 'Desktop - First Search1'
And Do a search and Verify serp message 'Desktop - Later Search1'
Then Verify SERP page

Scenario: Validating search results for encode characters
Given Goto Search and Win Site
When Do a search and Verify search results
Then Verify SERP page

Scenario: Verify search activity log table for the regular search activity
Meta: 
@TestingType Sanity
Given Register a fully registered user through RF
And Assign the user to the given 'SES' segment
When Login to the application with the EDID, TSRC, TSRC2, Segid as 'exp165160', 'search_swemail', '17s5265' and 'SWJanConT5'
And Do a search and Verify SERP page
Then Verify the regular search activity details in the search activity log table