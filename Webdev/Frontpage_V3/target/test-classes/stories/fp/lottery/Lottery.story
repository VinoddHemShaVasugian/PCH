Narrative:
In order to  LotteryPage
As a  Tester
I want to  retrive lottery article tokens details from admin.

Meta: 
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:LotteryClaimTokens}

Scenario: Verify Lottery page and cliam tokens scenario.
Meta: 
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When Verify the lottery tokens claimed status of the user and the Progres bar
Then Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'

Scenario: Verify future drawing and different locations for lottery results
Given Login to the FP Application as recognized user
When Change location and verify lottery results
Then Verify the last draw date and next draw date
And Verify next jackpot detail

Scenario: Verify Lottery Past Results page
Given Register a fully registered user through RF and login to the site
When Goto Lottery Past Result page
Then Verify the Past Result page
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'

Scenario: Verify Lottery Pay Results page
Given Register a fully registered user through RF and login to the site
When Goto Lottery Payout page
Then Verify the Payout page
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'