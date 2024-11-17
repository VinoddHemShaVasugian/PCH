Narrative:
In order to verify the apphealth on quizzes site/tab
As a user
I want to launch the quizzes site/tab

Meta:
@TestingType Regression

Scenario: Verify app health on quizzes tab
Meta:
@TestingType Sanity
Given Go to Quiz tab
When land on quizzes app health Page
Then verify quizzes apphealth
