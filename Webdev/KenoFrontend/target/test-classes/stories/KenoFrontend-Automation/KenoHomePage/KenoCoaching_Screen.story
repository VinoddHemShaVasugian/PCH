Narrative:
In order to verify coaching screen to first time users
As a tester
I want to test the Keno coaching screens as already registered user as well as for new user

GivenStories:stories/KenoFrontend-Automation/Admin/Preconditions.story#{id1:VerifyContestEntryDetails}

Scenario: Verification of Coaching screens with a new registration
Given Go to Keno Home Page
Then verify user sees following message as Coaching screen1: "Pick 10 Numbers or Choose Quick Pick!"
And user picks required numbers on the Keno gamecard
And user sees following message as Coaching screen2: "Submit Your Numbers!"
And user completes registration after submitting keno card
And user progresses to view keno ad followed by Choices screen
And user sees following message as Coaching screen3: "Play Any Instant Win Game To Unlock Your Next Keno Card!"
And user selects a SFL choice
And user with password plays SFL game
And User submits 2nd keno card

Scenario: Verify the contestkey confirmation in OAM testpage
Given User Logs into OAM page
And clicks on Search Menu
And clicks on Legacy Contest Entry Tab
When user enters user with password email from previous testcase
Then records with configured contest keys are found