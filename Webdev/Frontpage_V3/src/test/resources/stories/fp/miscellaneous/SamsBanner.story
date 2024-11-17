Narrative:
In order to verify the SAMS banner on homepage
As a user
I want to logged into the application and publish SAMS banner from admin article.

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/SamsAdmin.story#{id:EnableSAMSBanner}

Lifecycle:
After:
Scope: STORY
Outcome: ANY
Given Login to the SAMS Admin
And Publish the article 'Automation Banner'
And User redirect to the Frontpage application 'ClearCache'

Scenario: Verify the SAMS banner on homepage
Meta:
@TestingType Sanity
Given Register a fully registered user through RF
And Go to Frontpage site
When Verify the presence of sams banner on homepage
Then Login to the FP Application as recent user
And Verify the presence of sams banner on homepage

Scenario: Verify the fallback banner instead of SAMS banner on homepage
GivenStories: stories/fp/jm/SamsAdmin.story#{id:DisableSAMSBanner}
Given Register a fully registered user through RF
And Go to Frontpage site
When Verify the absence of sams banner on homepage
Then Login to the FP Application as recent user
And Verify the absence of sams banner on homepage