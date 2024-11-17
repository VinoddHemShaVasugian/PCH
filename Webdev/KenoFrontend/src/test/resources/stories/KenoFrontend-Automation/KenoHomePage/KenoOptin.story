Narrative:
In order to Verify Optin subscribtions
As a tester
I want to configure and opt-in on Keno website

GivenStories:stories/KenoFrontend-Automation/Admin/Preconditions.story#{id2:VerifyOptinDetails}
Scenario: Verify the user can view Opt-In lightbox
Given Go to Keno Home Page
And Navigate to Registration Page
When Enter the Required details in the registration page with optins unchecked
Then Verify the successful registration
And optin lightbox should be displayed
And a click on "X" button should close the lightbox

Scenario: Verify the Optin tokens in Token History
Given Go to Keno Home Page
And Navigate to Registration Page
When Enter the Required details in the registration page with optins unchecked
Then Verify the successful registration
And optin lightbox should be displayed
And user clicks on "Sign Up Now!" button
And "Thank You" message should display
And "Email Sign Up Bonus Tokens!" message shows as per configured tokens in token history page

Scenario: Verify the optin confirmation in OAM testpage
Given User Logs into OAM page
And clicks on Search Menu
And clicks on Subscriptions Events Tab
When user enters user with password email from previous testcase
Then record with Keno optin is found

Scenario: Verify Optin lightbox functionality for User without pasword
Given user without password is logged into keno
Then optin lightbox should be displayed
And user clicks on "Sign Up Now!" button
And "Thank You" message should display

Scenario: Verify the optin confirmation in OAM testpage
Given User Logs into OAM page
And clicks on Search Menu
And clicks on Subscriptions Events Tab
When user enters user without password email from previous testcase
Then record with Keno optin is found