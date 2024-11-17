Narrative:
In order to verify the rewards update in the uninav
As a user 
I want to check rewards redeem and levelup playnow button 

@TestingType Sanity Regression
@TestCaseId RT-04307
Scenario: Create the full reg user and verify the rewards update
Given Register a user with the Optins
Then Click on redeem tokens icon

Scenario: verify the info pages
Given Register a user with the Optins
Then verify the infopages

Scenario: verify the uninav for guest user
@TestCaseId RT-04343
Given Goto Search and Win Site
Then Verify the uninav for guest user