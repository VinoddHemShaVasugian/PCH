Narrative:
In order to verify Keno Play Slot Cards, Scratch cards and Live Drawing 
As a tester
I want to test the User Play Slot Cards, Scratch cards and wait for Live Drawing to know the match numbers 

Scenario: 1.Verify User played 1 cards and verify picked numbers with live drawing numbers 
Given Go to Keno Home Page
!-- And Navigate to Registration Page
!-- When Enter the Required details in the registration page
!-- Then Verify the successful registration
When Registered a fresh user via RS successfully
When play 1 cards
Then verify the status of the 1 cards


Scenario: 2.Verify the New User played 1 slot cards and verified the status of the 2 cards
Given Go to Keno Home Page
When play 1 cards without registartion
Then verify the status of the 1 cards



