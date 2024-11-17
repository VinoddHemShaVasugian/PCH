Narrative:
In order to Sub category menu pages
As a user
I want to logged into the application with valid user name and password

Meta:
@TestingType Regression

Scenario: Verify the Optin light box when user has already been checked it when do registration
Meta: 
@TestingType Sanity
Given Login to the FP Application as recognized user
When Get sub category menu url list
Then Verify submenu landing pages

Scenario: Verify sub category page content based on admin configuration
Given Register a fully registered user through RF and login to the site
When Get sub category menu url list
Then Verify articles, videos and Trending now section on sub category pages