Narrative:
In order to verify the Silver user functionality
As a user 
I want to Create a user from RF API and complete any quiz on quiz site.

Meta:
@TestingType Regression
GivenStories: stories/quiz/jm/QuizAdmin.story#{id:ContestKeys}

Scenario: Verify VIP message and complete registration functionality for silver reg user type in tab
Given Register a silver user through RF and login to the site
When Verify complete registration button on the uninav
Then Verify the N1 VIP message
And Do complete registration to become fully registered user from Silver User
And Verify user successfully landed on homepage
And Verify the L1 VIP message

Scenario: Verify complete registration functionality from light box for Silver user type in tab
Given Register a silver user through RF and login to the site
When Verify complete registration button on the uninav
Then Navigate to Trending quiz page
And Play quiz and Verify GOS page
And Verify the presence of the complete reg light box for no password user
And Do complete registration to become fully registered user from light box
And Verify the user is full reg
And Verify contest entry 'Contest Keys / QUIZ / Complete','Contest Key'