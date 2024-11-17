Narrative:
In order to verify the weather page
As a user
I want to logged into the application with valid user name and password

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:WeatherClaimTokens}

Scenario: Verify the Weather Hover screen details with Main page details
Given Login to the FP Application as recognized user
When Verify weather hover menu
Then Edit zip code and verify weather page
And User redirect to the Frontpage application
And Validate the Hover details with the main page details

Scenario: Verify token cliamed and weather sections in weather page
Meta:
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When Verify the weather tokens claimed status of the user and the Progres bar
Then Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'
And Verify the Daily, Hourly, Ad and Climate Hisotry sections
And Verify the image of Header and Footer menu
And Verify default city for invalid zipcode

Scenario: Verify the Hourly Forecast section details with Ad refresh
Given Login to the FP Application as recognized user
When Verify the presence of Hourly Forecast section
Then Verify ads and weather content in expanded section
Then Verify ads and weather content in collapsed section

Scenario: Verify the Daily forecast section details with Ad refresh
Given Login to the FP Application as recognized user
When Verify the presence of daily forecast section
Then Verify the 5 day forecast section
And Verify the 7 day forecast section
And Verify the 3 day forecast section

Scenario: Verify the Weather Climate history
Given Login to the FP Application as recognized user
When Verify the presence of weather climate history section
Then Verify the default selected month
And Verify Ad refresh while modifing the month