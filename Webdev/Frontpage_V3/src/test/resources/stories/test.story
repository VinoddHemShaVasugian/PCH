Narrative:
In order to verify Segid tokens on website
As a Tester
I want to retrive the values from "Tokens / LinkPromotion" joomla article and validate on site.

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:TokensLinkPromotion}

Scenario: Verify segid tokens for incremental emails for Fully registered user
Meta: 
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When User redirect to the Frontpage application 'conditions'
Then Do a search and Verify SERP page
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'
And Login to the Frontpage Admin
And Do a search for an article 'Tokens / Link Promotion / SegId 111814-021815'
And Retrieve the description,notice,tokens,condition info from the article '2'
And Login to the FP Application as recent user
And User redirect to the Frontpage application 'conditions'
And Do a search and Verify SERP page
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'