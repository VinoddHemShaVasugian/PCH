Narrative:
In order to verify the homepage section
As a user
I want to Login to Frontpage site

Meta:
@TestingType Regression

Scenario: Verify the Homepage categories
Meta:
@TestingType Sanity
Given Login to the FP Application as recognized user
When Verify the presence of the Our Picks section
Then Verify the presence of the Top stories section
And Verify the presence of the Categories section
And Verify the presence of the Trending Videos section
And Verify the presence of the Trending Now section

Scenario: Verify the our picks section
Given Login to the FP Application as recognized user
When Verify the presence of the Our Picks section
Then Verify the presence of 'weather' category in Our Picks section
And Verify the presence of 'horoscopes' category in Our Picks section
And Verify the presence of 'lottery' category in Our Picks section
And Verify ourpicks ad '187','255'

Scenario: verify the info pages
Meta:
@TestingType Sanity
Given Register a user with the Optins
Then Verify the infopages