Narrative:
In order to verify the Lightbox functionality
As a user 
I want to launch quiz site.

Meta:
@TestingType Regression

Scenario: Verify registration light box for guest user
Meta: 
@TestingType Sanity
Given Go to Quiz tab
When Verify user landed on quiz tab homepage
Then Navigate to Trending quiz page
And Play quiz and Verify GOS page
And Verify the presence of the guest user registration light box

Scenario: Verify complete registration light box for Mini reg user type
Given Register a mini registered user through RF and login to the site
When Verify complete registration button on the uninav
Then Navigate to Trending quiz page
And Play quiz and Verify GOS page
And Verify the presence of the complete reg light box

Scenario: Verify complete registration light box for Silver user type
Given Register a silver user through RF and login to the site
When Verify complete registration button on the uninav
Then Navigate to Trending quiz page
And Play quiz and Verify GOS page
And Verify the presence of the complete reg light box for no password user

Scenario: Verify complete registration light box for social user type
Given Register a social user through RF and login to the site
When Verify complete registration button on the uninav
Then Navigate to Trending quiz page
And Play quiz and Verify GOS page
And Verify the presence of the complete reg light box

Scenario: Verify Abandoning Token light box
Meta: 
@TestingType Sanity
Given Create '<userType>' user and login to quiz tab
When Verify user landed on quiz tab homepage
Then Navigate to Trending quiz page
And Answer '17' question(s)
And Navigate to Token History Page
And Verify the presence of Abandoning Token light box

Examples:
|userType|
|guest|
|fully registered|
|mini registered|
|silver|