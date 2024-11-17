Narrative:
In order to Verify user's successful login
As a tester
I want to test the sign in and check user's profile bar

Scenario: I want to test the sign in and check user's profile bar
Given Go to Keno Home Page
When Navigate to SignIn Page
And Enter the valid credentials in SignIn page
|user_name                | password|
|keno_auto_101@pchmail.com  | pch234  |
Then Verify the successful login
And Welcome <first name> <last name> is displayed on the profile bar on the site