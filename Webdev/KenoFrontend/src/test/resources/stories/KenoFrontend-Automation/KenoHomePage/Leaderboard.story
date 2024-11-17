Narrative:
This automation story is to verify Leaderboard functionality and covers below regression test cases:
RT-03908, RT-03909 and RT-03134

Scenario: Verify Mobile Tab and users data is appearing on leaderboard
Given Land on Keno Home Page as a fresh user with password using Reg Foundation
When User observes the leaderboard
Then user should see the Mobile Tab appearing in Leaderboard
And the user's rank, firstname last initial, status and Tokens are displayed on the leaderboard
And top token earner for the day is displayed on top of the leaderboard
And verify Yesterday's winners are displayed in leaderboard
And verify 'Token Leader Prize Details' link on PCH Rewards
And verify user details are displayed in my daily token total

Scenario: Verify Mobile Tab and users data is appearing on leaderboard for User without password
Given user without password is logged into keno
When User observes the leaderboard
Then verify information for user without password is appearing in leaderboard
And user should see the Mobile Tab appearing in Leaderboard
And the user's rank, firstname last initial, status and Tokens are displayed on the leaderboard
And top token earner for the day is displayed on top of the leaderboard
And verify Yesterday's winners are displayed in leaderboard

Scenario: Verify Desktop Tab and users data is appearing on leaderboard
Given Land on Keno Home Page as a fresh user with password using Reg Foundation
When User observes the leaderboard
Then verify user details are displayed in my daily token total
And user should see the Desktop/Tablet Tab appearing in Leaderboard
And the user's rank, firstname last initial, status and Tokens are displayed on the leaderboard
And top token earner for the day is displayed on top of the desktop leaderboard
And verify 'Token Leader Rewards Details' button in Token leaderboard

Scenario: Verify Leaderboard details are appearing for Unrecognized User
Given Go to Keno Home Page
When User observes the leaderboard
Then user should see the Desktop/Tablet Tab appearing in Leaderboard
And verify that My Daily Token Total is not present
And the user's rank, firstname last initial, status and Tokens are displayed on the leaderboard
And top token earner for the day is displayed on top of the desktop leaderboard
And verify 'Token Leader Rewards Details' button in Token leaderboard
