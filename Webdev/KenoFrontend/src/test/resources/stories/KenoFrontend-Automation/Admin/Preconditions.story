Narrative:
As a tester I want to verify configurations in CMS

Scenario: Verify the contestdetails in Contest Entry article
Meta: @id1 VerifyContestEntryDetails
Given Go to Keno Admin Page
When user logs in with credentials
|user_name|password|
|qaadmin|pch1234567|
And click on Articles
And search for Contest Entries category articles
And Search for Contest Entry title article
And read contestkeys configured for Desktop contest entry
And read contestmessage configured for Desktop contest entry

Scenario: Verify the optin details in Optin Lightbox article
Meta: @id2 VerifyOptinDetails
Given Go to Keno Admin Page
When user logs in with credentials
|user_name|password|
|qaadmin|pch1234567|
And click on Articles
And search for Optin Lightbox category articles
And Search for Optin Lightbox title article
And read Optin Tokens configured

Scenario: Verify Daily Token Bonus Module article
Meta: @id3 VerifyDailyTokenBonus
Given Go to Keno Admin Page
When user logs in with credentials
|user_name|password|
|qaadmin|pch1234567|
And click on Articles
And search for Daily Token Bonus category articles
And Search for Daily Token Bonus title article
And read Daily Token Bonus Tokens configured