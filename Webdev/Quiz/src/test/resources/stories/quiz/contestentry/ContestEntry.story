Narrative:
In order to verify contest entry 
As a Tester
I want to retrive the contest entry for Quiz comletion from Joomla admin and complete any quiz.

Meta:
@TestingType Regression
GivenStories: stories/quiz/jm/QuizAdmin.story#{id:ContestKeys}

Lifecycle:
After:
Scope: STORY
Outcome: ANY
Given Login to the Quiz Admin
And Do a search for an article 'config-contest-entry-api-client'
And Modify the field by its key name 'contestEntryApiUri' with the given value 'http://cs.{env}.pch.com/EntryApi'
And User redirect to the quiz application 'ConfigurationClearCache'

Scenario: Verify quiz completion contest entry for Fully registered user in Quiz tab
Meta:
@TestingType Sanity
Given Register a user with the Optins
When Navigate to Trending quiz page
Then Play quiz and Verify GOS page
And Verify contest entry 'Contest Keys / QUIZ / Complete','Contest Key' for organic user

Scenario: Verify failed contest entries for Quiz tab
GivenStories: stories/quiz/jm/QuizAdmin.story#{id:ContestEntryAPI}
Given Register a fully registered user through RF and login to the site
When Navigate to Trending quiz page
Then Play quiz and Verify GOS page
And Verify the failed contest entry record details with status as '0'
And User redirect to the quiz application 'ProcessContestEntry'
And Verify the failed contest entry record details with status as '1'
And Login to the Quiz Admin
And Do a search for an article 'config-contest-entry-api-client'
And Modify the field by its key name 'contestEntryApiUri' with the given value 'http://cs.{env}.pch.com/EntryApi'
And User redirect to the quiz application 'ConfigurationClearCache'
And User redirect to the quiz application 'ProcessContestEntry'
And Verify the absence of the failed contest entry record
And User redirect to the quiz tab
And Verify contest entry 'Contest Keys / QUIZ / Complete','Contest Key'