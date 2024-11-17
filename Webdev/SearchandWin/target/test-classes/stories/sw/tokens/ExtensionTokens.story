Narrative:
In order to verify Extension tokens on website
As a Tester
I want to retrive the values from "Tokens / Extension" joomla article and validate on site.

Meta:
@TestCaseId RT-04387
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:ExtensionTokens},stories/sw/jm/SearchAdmin.story#{id:ConfigSearch}

Scenario: Verify extension tokens for Fully registered user
Given Register a fully registered user through RF and login to the site
When Verify the C1 VIP message after complete registration
Then Redirect the user to extension download page '/extensiondownload'
And Verify the db property 'searchandwin' as '1'
Then Redirect the user to '/search7' page
And Verify the extension tokens and the db property 'searchandwin' as '2'
And Verify the VIP activity
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'

Scenario: Verify extension tokens for Mini registered user
Given Register a mini registered user through RF and login to the site
When Redirect the user to extension download page 'extensiondownload'
And Verify the db property 'searchandwin' as '1'
Then Redirect the user to 'search7' page
And Verify the Complete Registration light box
And Verify the db property 'searchandwin' as '3'
And Complete the Mini reg user by click on Continue button from light box
And Verify the extension tokens and the db property 'searchandwin' as '2'
And Verify the VIP activity
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'

Scenario: Verify extension tokens for Social registered user
Given Register a social user through RF and login to the site
When Redirect the user to extension download page 'extensiondownload'
And Verify the db property 'searchandwin' as '1'
Then Redirect the user to 'search7' page
And Verify the Complete Registration light box
And Verify the db property 'searchandwin' as '3'
And Complete the Social reg user by click on Continue button from light box
And Verify the extension tokens and the db property 'searchandwin' as '2'
And Verify the VIP activity
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'

Scenario: Verify extension tokens for Silver registered user
Given Register a silver user through RF and login to the site
When Redirect the user to extension download page 'extensiondownload'
And Verify the db property 'searchandwin' as '1'
Then Redirect the user to 'search7' page
And Verify the Silver user Complete Registration light box
And Verify the db property 'searchandwin' as '3'
And Complete the Silver reg user by enter the password
And Verify the extension tokens and the db property 'searchandwin' as '2'
And Verify the VIP activity
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'

Scenario: Verify extension tokens for Mini registered user by closing the Complete registration light box
Given Register a mini registered user through RF and login to the site
When Redirect the user to extension download page '/extensiondownload'
And Verify the db property 'searchandwin' as '1'
Then Redirect the user to '/search7' page
And Verify the Complete Registration light box
And Verify the db property 'searchandwin' as '3'
And Close the Complete Registration light box
And Verify the db property 'searchandwin' as '4'
And Do complete registration to become full reg user from Mini Reg user
And Verify the extension tokens not get awarded