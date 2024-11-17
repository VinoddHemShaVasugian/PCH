Narrative:
In order to verify acq flow on quizzes site/tab
As a user
I want to launch the quizzes site/tab with acq path and perform complete path flow

Meta:
@TestingType Regression
GivenStories: stories/quiz/jm/QuizAdmin.story#{id:NewPromotionValues}

Scenario: Verify Acq full path completion flow on quizzes Tab
Meta:
@TestingType Sanity
Given Go to Quiz tab
When land on quizzes Acquisition path
Then verify Persistent Banner
Then verify user complete fullPath and verify entries and tokens
And verify Claim Now appears on GOS
And verify clicking on Claim Now leads to proper Registration Page
And verify registration done properly
And verify if spectrum page appears then land on quizzes site
And verify Tokens and Verbiage after fullPath Registration
And verify ContestEntries after fullPath Registration