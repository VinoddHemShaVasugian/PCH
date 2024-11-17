Narrative:
In order to Verify Mobile tab on Leaderboard
As a tester
I want to navigate to leaderboard and verify mobile tab for daily tokens

Scenario: Verify Mobile Tab and users data is appearing on leaderboard
Given Go to Keno Home Page
When Registered a fresh user via RS successfully
And User observes the leaderboard
Then verify user details are displayed in my daily token total
And user should see the Mobile Tab appearing in Leaderboard
And the user's rank, firstname last initial, status and Tokens are displayed on the leaderboard
And top token earner for the day is displayed on top of the leaderboard
And verify Yesterday's winners are displayed in leaderboard

Scenario: Verify Mobile Tab and users data is appearing on leaderboard for User without password
Given user without password is logged into keno
When User observes the leaderboard
Then verify information for user without password is appearing in leaderboard
And user should see the Mobile Tab appearing in Leaderboard
And the user's rank, firstname last initial, status and Tokens are displayed on the leaderboard
And top token earner for the day is displayed on top of the leaderboard
And verify Yesterday's winners are displayed in leaderboard
