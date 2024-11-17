Narrative:
In order to verify token queue records on DB
As a Tester
I want to modify the values from "Config-Prizemachine" joomla article and validate on site.

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:ModifyTokenBankAPI}

Lifecycle:
After:
Scope: STORY
Outcome: ANY
Given Login to the Frontpage Admin
And Do a search for an article 'Config-Prizemachine'
And Modify the field by its key name 'tokenBankApiBaseUri' with the given value 'http://tokenbankapi.{env}.pch.com/api'
And User redirect to the Frontpage application 'ClearCache'

Scenario: Verify token queue by making the an invalid api details
Given Register a fully registered user through RF and login to the site
When Do a search on homepage and Verify SERP page
Then Verify the token queue record details with status as '0'
And User redirect to the Frontpage application 'ProcessTokens'
And Verify the token queue record details with status as '1'
And Login to the Frontpage Admin
And Do a search for an article 'Config-Prizemachine'
And Modify the field by its key name 'tokenBankApiBaseUri' with the given value 'http://tokenbankapi.{env}.pch.com/api'
And User redirect to the Frontpage application 'ClearCache'
And User redirect to the Frontpage application 'ProcessTokens'
And Verify the absence of the token queue record
And User redirect to the Frontpage application
And Verify the tokens are awarded from the queue