Narrative:
In order to verify First search tokens on website
As a Tester
I want to retrive the values from "Tokens / First Search" joomla article and validate on site.

Meta:
@TestCaseId RT-04296
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:TokensFirstSearch}

Scenario: Verify First search tokens for Fully registered user
Meta:
@TestingType Sanity
Given Register a fully registered user through RF
And Assign segment by name 'Segments Included'
And Login to the SW Application as recent user
When Do a search and Verify SERP page
Then Navigate to Token History Page
And Verify token transaction details based on admin configured article 'Tokens','Alternative Description','1'