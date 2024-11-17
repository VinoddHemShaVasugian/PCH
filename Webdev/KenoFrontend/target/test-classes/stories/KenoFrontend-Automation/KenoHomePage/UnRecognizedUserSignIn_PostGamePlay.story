Narrative:
In order to verify Sign In Post Game Play
As a tester
I want to test the Keno card submission and sign in post game play

Scenario: 1.Verification of user sign in post game play
Given Go to Keno Home Page
When Submit the selected numbers as
01,03,04,05,06,07,08,80,85,91
Then It should be navigate to the registration page
And It should show "Sign In" button on the top right of the registration Page
And SignIn successfully with valid credential before the next set of keno game has started
|user_name                | password|
|demo18@pchmail.com  | pch1234  |
And verify correct numbers are submitted as
01,03,04,05,06,07,08,80,85,91