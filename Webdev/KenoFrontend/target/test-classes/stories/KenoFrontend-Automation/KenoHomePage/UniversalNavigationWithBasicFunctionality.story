Narrative:
In order to Verify universal navigation
As a tester
I want to test the univ nav after sign in

Scenario: 1 Universal Nav bar for a Logged in User

Given Go to Keno Home Page
When Navigate to SignIn Page
And Enter the valid credentials in SignIn page
|user_name                | password|
|ds101@pchmail.com  | pch1234  |
Then Verify the successful login
And Universal Nav Bar is displayed on the site
And individual tabs should be clickable for navigating onto other sites
And user is able to scroll right and left onto the Uni Nav Bar