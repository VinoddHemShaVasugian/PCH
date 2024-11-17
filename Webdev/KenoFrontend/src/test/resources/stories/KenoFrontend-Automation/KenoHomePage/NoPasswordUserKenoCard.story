Narrative:
In order to Verify create password lightbox for user without password
As a tester
I want to create the user without password and submit the gamecard

GivenStories:stories/KenoFrontend-Automation/Admin/Preconditions.story#{id1:VerifyContestEntryDetails;id3:VerifyDailyTokenBonus}

Scenario: Verify the SFL and contest entry for user without password
Given user without password is logged into keno
When user picks required numbers on the Keno gamecard
And User submits first keno card
Then Create password lightbox should appear
And clicks on forfeit tokens link
And user progresses to view keno ad followed by Choices screen
And user selects a SFL choice
And user without password plays SFL game
And User submits 2nd keno card
And clicks on forfeit tokens link

Scenario: Verify the contestkey confirmation in OAM testpage
Given User Logs into OAM page
And clicks on Search Menu
And clicks on Legacy Contest Entry Tab
When user enters user without password email from previous testcase
Then records with configured contest keys are found

Scenario: Verify the Scratch Card and Keno card contest entry for user without password
Given user without password is logged into keno
When user picks required numbers on the Keno gamecard
And User submits first keno card
Then Create password lightbox should appear
And clicks on forfeit tokens link
And user progresses to view keno ad followed by Choices screen
And user selects a Scratch Card choice
And user without password plays Scratch Card game
And User submits 2nd keno card
And clicks on forfeit tokens link

Scenario: Verify the contestkey confirmation in OAM testpage
Given User Logs into OAM page
And clicks on Search Menu
And clicks on Legacy Contest Entry Tab
When user enters user without password email from previous testcase
Then records with configured contest keys are found

Scenario: Verify the Queued tokens upon creating password for user without password 
Given user without password is logged into keno
When user picks required numbers on the Keno gamecard
And User submits first keno card
Then Create password lightbox should appear
And clicks on forfeit tokens link
And user progresses to view keno ad followed by Choices screen
And user selects a Scratch Card choice
And user without password plays few Scratch Card games
And enters password on GOS
And user gets Path games queued tokens
And Registration tokens displays in Token History
