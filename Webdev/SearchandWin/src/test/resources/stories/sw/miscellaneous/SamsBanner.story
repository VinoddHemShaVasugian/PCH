Narrative:
In order to verify the SAMS banner on homepage
As a user
I want to logged into the application and publish SAMS banner from admin article.

Meta:
@TestCaseId RT-04386
@TestingType Regression
GivenStories: stories/sw/jm/SamsAdmin.story#{id:EnableSAMSBanner}

Lifecycle:
After:
Scope: STORY
Outcome: ANY
Given Login to the SAMS Admin
And Publish the article 'Search Banner 2'
And User redirect to the Search application 'ClearCache'

Scenario: Verify the SAMS banner on homepage
Meta:
@TestingType Sanity
Given Register a fully registered user through RF
And Goto Search and Win Site
When Verify the presence of sams banner on homepage
Then Login to the Search application with the created user
And Verify the presence of sams banner on homepage

Scenario: Verify the fallback banner instead of SAMS banner on homepage
GivenStories: stories/sw/jm/SamsAdmin.story#{id:DisableSAMSBanner}
Given Register a fully registered user through RF
And Goto Search and Win Site
When Verify the absence of sams banner on homepage
Then Login to the Search application with the created user
And Verify the absence of sams banner on homepage