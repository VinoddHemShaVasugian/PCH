Narrative:
In order to verify the Mini reg user functionality
As a user 
I want to Create a user from RF API and complete any quiz on quiz site.

Meta:
@TestingType Regression
GivenStories: stories/quiz/jm/QuizAdmin.story#{id:ContestKeys}

Scenario: Verify VIP message and complete registration functionality for Mini reg user type in tab
Given Register a mini registered user through RF and login to the site
When Verify complete registration button on the uninav
Then Verify the C1 VIP message
And Do complete registration to become full reg user from Mini Reg user
And Verify user successfully landed on homepage
And Verify the C1 VIP message after complete registration

Scenario: Verify complete registration functionality from light box for Mini reg user type in tab
Given Register a mini registered user through RF and login to the site
When Verify complete registration button on the uninav
Then Navigate to Trending quiz page
And Play quiz and Verify GOS page
And Verify the presence of the complete reg light box
And Do complete reg from complete reg Lightbox
And Verify the user is full reg
And Verify contest entry 'Contest Keys / QUIZ / Complete','Contest Key'