Narrative:
In order to verify acq flow on quizzes site/tab
As a user
I want to launch the quizzes site/tab with acq path and verify persistent banner

Meta:
@TestingType Regression
GivenStories: stories/quiz/jm/QuizAdmin.story#{id:NewPromotionValues}

Scenario: Verify persistent banner on Acq flow for quizzes tab
Meta:
@TestingType Sanity
Given Go to Quiz tab
When land on quizzes Acquisition path
Then verify Persistent Banner