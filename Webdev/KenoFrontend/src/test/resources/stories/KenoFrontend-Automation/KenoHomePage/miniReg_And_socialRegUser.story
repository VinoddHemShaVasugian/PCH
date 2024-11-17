Narrative:
In order to verify pre registration & post registration for social & mini user
As a tester
I want to test the User to load the Keno Page


Scenario: 1. Minireg User experience for pre registration
Given Go to Keno Home Page
When Navigate to SignIn Page
And mini reg user logs in
And user clicks on 'Complete Registration' button at top
Then user should see the Registration Form but without Email and Password fields on accounts site
And user fills up the reg form for mini user with optins prechecked
And should be able to register successfully
When user navigates to Token History
Then user should be awarded below tokens which is displayed in token history
|token_entry                | token_amount|
|User Registration  | +1,000  |
And the optins should be properly captured under OAM tools


Scenario: 2. Social user experience for pre Registration
Given A social reg user is on keno site
When user clicks on 'Complete Registration' button at top
Then user should see the Registration Form but with Email fields already filled on accounts site
And user fills up the reg form for social user with optins prechecked
And should be able to register successfully
When user navigates to Token History
Then user should be awarded below tokens which is displayed in token history
|token_entry                | token_amount|
|User Registration  | +1,000  |
And the optins should be properly captured under OAM tools


Scenario: 3. Minireg User experience for post registration
Given A minireg user is on keno site
When user picks required numbers
And notes the picked numbers
And clicks on Submit
Then user logs in if sign in page appears
And user should see the Registration Form but without Email and Password fields on accounts site
And user fills up the reg form for mini user with optins prechecked
And should be able to register successfully
And the gameplay is successful
When user navigates to Token History
Then user should be awarded below tokens which is displayed in token history
|token_entry                | token_amount|
|User Registration  | +1,000  |
And the optins should be properly captured under OAM tools


Scenario: 4. Social user experience for post Registration
Given A social reg user is on keno site
When user picks required numbers
And notes the picked numbers
And clicks on Submit
Then user logs in if sign in page appears
And user should see the Registration Form but with Email fields already filled on accounts site
And user fills up the reg form for social user with optins prechecked
And should be able to register successfully
And the gameplay is successful
When user navigates to Token History
Then user should be awarded below tokens which is displayed in token history
|token_entry                | token_amount|
|User Registration  | +1,000  |
And the optins should be properly captured under OAM tools
