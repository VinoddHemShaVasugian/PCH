Narrative:
In order to verify the Optin light box
As a user
I want to logged into the application with valid user name and password

Meta:
@TestingType Regression

Scenario: Verify the Optin light box
Meta:
@TestingType Sanity 
Given Register a user without the Optins
Then Verify the presence of the Optin light box
And Verify the dbproperty 'optinshowed' with value as '1'

Scenario: Verify the Optin light box when user has already been checked it when do registration
Meta:
@TestingType Sanity
Given Register a user with the Optins
Then Verify the absence of the Optin light box
And Verify the absence of the dbproperty 'optinshowed'

Scenario: Verify the Optin light box when the user redirects from other property site 
Given Go to Search & Win site
And Register a user with the Optins
When User redirect to the Frontpage application
Then Verify the presence of the Optin light box
And Verify the dbproperty 'optinshowed' with value as '1'