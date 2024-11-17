Narrative:
In order to verify token queue records on DB
As a Tester
I want to modify the values from "Config-Prizemachine" joomla article and validate on site.

Meta:
@TestCaseId RT-04295
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:ModifyTokenBankAPI}

Lifecycle:
After:
Scope: STORY
Outcome: ANY
Given Login to the Search Admin
And Do a search for an article 'Config-Prizemachine'
And Modify the field by its key name 'tokenBankApiBaseUri' with the given value 'http://tokenbankapi.{env}.pch.com/api'
And User redirect to the Search application 'ClearCache'

Scenario: Verify token queue by making the an invalid api details
Given Register a fully registered user through RF and login to the site
When Do a search on homepage and Verify SERP page
Then Verify the token queue record details with status as '0'
And User redirect to the Search application 'ProcessTokens'
And Verify the token queue record details with status as '1'
And Login to the Search Admin
And Do a search for an article 'Config-Prizemachine'
And Modify the field by its key name 'tokenBankApiBaseUri' with the given value 'http://tokenbankapi.{env}.pch.com/api'
And User redirect to the Search application 'ClearCache'
And User redirect to the Search application 'ProcessRetryTokens'
And Verify the absence of the token queue record
And User redirect to the Search application
And Verify the tokens are awarded from the queue