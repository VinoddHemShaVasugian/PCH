Narrative:
In order to verify the Sample functionality
As a user
I want to check the Winners in the Search and Win site

@TestingType Sanity Regression
Scenario: Verify the RecentWinners Section in the Search and Win Site
Given Goto Search and Win Site
When Click the 'Recent Winners'
Then Verify the Winners List
