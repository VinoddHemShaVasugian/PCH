Narrative:
In order to verify recognized users flow on quizzes
As a user 
I want to launch the quiz site and verify recognized users redirection flow
 
Scenario: Verify recognized user redirection flow                 
Given Go to Quiz site
When create <userType> and login
Then verify user landing screen


Examples:
|userType             |Message|
|MiniReg              |       |
|Social               |       |
|User With Password   |       |
|User Without Password|       |
