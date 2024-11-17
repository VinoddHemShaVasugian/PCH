Narrative:
In order to verify Results Page
As a tester
I want to test the Keno results page as already registered user as well as for new user

Scenario: 1.Verification of UI for Result page
Given Go to Keno Home Page
And User login successfully with valid credentials
|user_name                | password|
|keno_auto_101@pchmail.com  | pch1234  |
When navigate to results page by clicking on Results link
Then It should display default latest drawing result on the page
And Pay Table should show correctly on click

Scenario: 2.Verify UI for Date Drop Down
Given Go to Keno Home Page
And User login successfully with valid credentials
|user_name                | password|
|keno_auto_101@pchmail.com  | pch1234  |
When navigate to results page by clicking on Results link
And user clicks on Date drop down
Then it should display the Calendar for today and past 13 days. Dates before 14 days must not be enabled

Scenario: 3.Verify functionality of selected past drawing date and time(TOKENS only)
Given Go to Keno Home Page
When Registered a fresh user successfully
And play 1 cards
And navigate to results page by clicking on Results link
Then user selects past date from date drop down
And selects drawing time from time drop down
And click on Go button
And it should show drawing numbers for that specific drawing along with it's relative Pay Table under Winning numbers from Token Drawing section


