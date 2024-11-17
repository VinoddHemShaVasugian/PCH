Narrative:
In order to verify Keno Registration
As a tester
I want to test the User Registration from the Keno Registration Page

GivenStories:stories/KenoFrontend-Automation/Admin/Preconditions.story#{id:VerifyDailyTokenBonus}

Scenario: Verify Post gameplay registration
Given Go to Keno Home Page
When Select the numbers by Quick Pick
And notes the picked numbers
And Submit the selected numbers
And Verify application navigates to registration page
And Enter the Required details in the registration page with optins unchecked
Then verify Daily Token Bonus module and Joomla admin have same tokens for visit
And Verify the successful registration
And user progresses to view keno ad followed by Choices screen
And numbers submitted are same on side rail
And Registration tokens displays in Token History

Scenario: Verify Pre gameplay registration
Given Go to Keno Home Page
And Navigate to Registration Page
When Enter the Required details in the registration page with optins unchecked
Then Verify the successful registration
!-- 
!-- Scenario: Verify User Navigates to SignIn page and perform successful login
!-- Given Go to Keno Home Page
!-- When Navigate to SignIn Page
!-- And Enter the valid credentials in SignIn page
!-- |user_name          | password|
!-- |ds101@pchmail.com  | pch1234  |
!-- Then Verify the successful login