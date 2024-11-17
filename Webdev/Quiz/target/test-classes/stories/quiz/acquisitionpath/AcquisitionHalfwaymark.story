Narrative:
In order to verify acq flow on quizzes site/tab
As a user
I want to launch the quizzes site/tab with acq path and perform Halfway path flow

Meta:
@TestingType Regression
GivenStories: stories/quiz/jm/QuizAdmin.story#{id:NewPromotionValues}

Scenario: Verify Acq Halfway mark flow for quizzes tab
Meta:
@TestingType Sanity
Given Go to Quiz tab
When land on quizzes Acquisition path
Then verify Persistent Banner
And verify user complete halfwayPath and verify entries and tokens
And verify Claim Now appears on QuizPage after exactly halfwaypath complete
And verify clicking on Claim Now On quizPage leads to proper Registration Page
And verify registration done properly
And verify if spectrum page appears then land on quizzes site
And verify Tokens and Verbiage after halfwayPath Registration
And verify ContestEntries after halfwayPath Registration