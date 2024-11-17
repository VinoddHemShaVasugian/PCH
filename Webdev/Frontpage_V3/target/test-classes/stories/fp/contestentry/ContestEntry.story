Narrative:
In order to verify contest entry 
As a Tester
I want to retrive the contest entry for registration and first search in Joomla admin

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:ContestKeys}

Lifecycle:
After:
Scope: STORY
Outcome: ANY
Given Login to the Frontpage Admin
And Do a search for an article 'config-contest-entry-api-client'
And Modify the field by its key name 'contestEntryApiUri' with the given value 'http://cs.{env}.pch.com/EntryApi'
And User redirect to the Frontpage application 'ClearCache'

Scenario: Verify Registration and First search contest entry for Fully registered user
Meta:
@TestingType Sanity
Given Register a user with the Optins
When Do a search and Verify SERP page
Then Verify contest entry 'Contest Keys / Frontpage / Registration','Contest Key' for organic user
And Verify contest entry 'Contest Keys / Frontpage / FirstSearch','Contest Key' for organic user

Scenario: Verify Registration and First search contest entry for mini reg user
Given Register a mini registered user through RF and login to the site
Then Do complete registration to become full reg user from Mini Reg user
And Do a search and Verify SERP page
And Verify contest entry 'Contest Keys / Frontpage / Registration','Contest Key'
And Verify contest entry 'Contest Keys / Frontpage / FirstSearch','Contest Key'

Scenario: Verify Registration and First search contest entry for silver user
Given Register a silver user through RF and login to the site
Then Do complete registration to become fully registered user from Silver User
And Do a search and Verify SERP page
And Verify contest entry 'Contest Keys / Frontpage / FirstSearch','Contest Key'

Scenario: Verify Registration and First search contest entry for social user
Given Register a social user through RF and login to the site
Then Complete registration to become full reg user from social reg user
And Do a search and Verify SERP page
And Verify contest entry 'Contest Keys / Frontpage / Registration','Contest Key'
And Verify contest entry 'Contest Keys / Frontpage / FirstSearch','Contest Key'

Scenario: Verify when user makes multiple searches with different contest entry
Given Register a user with the Optins
When Do a search and Verify SERP page
Then User redirect to the Frontpage application 'ck=BZ999'
And Do a search and Verify SERP page
And User redirect to the Frontpage application 'ck=PP123'
And Do a search and Verify SERP page
And Verify contest entry 'BZ999' for organic user
And Verify contest entry 'PP123' for organic user

Scenario: Verify failed contest entries
GivenStories: stories/fp/jm/FpAdmin.story#{id:ContestEntryAPI}
Given Register a fully registered user through RF and login to the site
When Do a search and Verify SERP page
Then Verify the failed contest entry record details with status as '0'
And User redirect to the Frontpage application 'ProcessContestEntry'
And Verify the failed contest entry record details with status as '1'
And Login to the Frontpage Admin
And Do a search for an article 'config-contest-entry-api-client'
And Modify the field by its key name 'contestEntryApiUri' with the given value 'http://cs.{env}.pch.com/EntryApi'
And User redirect to the Frontpage application 'ClearCache'
And User redirect to the Frontpage application 'ProcessContestEntry'
And Verify the absence of the failed contest entry record
And User redirect to the Frontpage application
And Verify contest entry 'Contest Keys / Frontpage / FirstSearch','Contest Key'