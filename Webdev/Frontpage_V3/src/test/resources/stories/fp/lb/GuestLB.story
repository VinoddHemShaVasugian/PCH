Narrative:
In order to verify the signin and register lightbox 
As a user 
I want to check sign in and register lightbox should appear on homepage or article page not in serp page

Meta:
@TestingType Regression

Scenario: verify Sign in and register Lightbox for guest user

Given Go to Frontpage site as guest user
When Do a search and Verify SERP page
Then Do a search and Verify SERP page
Then Do a search and Verify SERP page
And Verify the presence of the Guest Optin light box