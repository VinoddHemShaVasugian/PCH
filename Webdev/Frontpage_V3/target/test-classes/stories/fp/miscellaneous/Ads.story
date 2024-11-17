Narrative:
In order to verify ads
As a Tester
I want to login to Frontpage site and verify

Meta: 
@TestingType Regression

Scenario: Verify ads on Homepage
Given Login to the FP Application as recognized user
When Verify right rail ad '300','250'
Then Verify inline ad '728','90'
And Verify ad on Top stories '368','97'
And Verify Trending now ad '300','90'
And Verify ourpicks ad '187','255'
And Verify right rail multiple ad '300','600','300','250'

Scenario: Verify ads on Category pages
Given Login to the FP Application as recognized user
And Navigate to Entertainment page
When Verify right rail ad '300','250'
Then Verify inline ad '728','90'
And Verify ad on Top stories '368','97'
And Verify Trending now ad '300','90'
And Verify sponsored ads '766','204'
And Verify right rail multiple ad '300','600','300','250'

Scenario: Verify ads on Video page
Given Login to the FP Application as recognized user
When Verify the video landing page
And Verify right rail ad '300','250'
Then Verify inline tile ad '728','90'
And Verify right rail multiple ad '300','600','300','250'

Scenario: Verify ads on Weather page
Given Login to the FP Application as recognized user
And Navigate to Weather page
When Verify right rail ad '300','250'
Then Verify inline ad '728','90'
And Verify right rail multiple ad '300','600','300','250'

Scenario: Verify ads on Lottery page
Given Login to the FP Application as recognized user
And Navigate to Lottery page
When Verify right rail ad '300','250'
Then Verify inline ad '728','90'
And Verify right rail multiple ad '300','600','300','250'

Scenario: Verify ads on Sub category pages
Given Login to the FP Application as recognized user
And Navigate to News page
When Verify right rail ad '300','250'
Then Verify inline ad '728','90'
And Verify right rail multiple ad '300','600','300','250'
And Verify Trending now ad '300','90'
And Verify sponsored ads '796','170'

Scenario: Verify ads on Article page
Given Login to the FP Application as recognized user
When Click on any article from Top stories
And Verify right rail ad '300','250'
Then Verify right rail multiple ad '300','600','300','250'
And Verify bottom ad under article section '770','320'

Scenario: verify the Interstitial ads while navigating to different articles
Meta:
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
Then Verify the presence of interstitial ads