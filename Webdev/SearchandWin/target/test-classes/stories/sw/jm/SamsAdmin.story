Narrative:
In order to verify the Sams banner
As a tester
I want to modify/retrieve the value from the SAMS joomla admin application

Meta:
@@TestingType Regression

Scenario: Unpublish sams banner in SAMS article
Meta: @id DisableSAMSBanner
Given Login to the SAMS Admin
And Unpublish the article 'Search Banner 2'
And User redirect to the Search application 'ClearCache'

Scenario: Publish sams banner in SAMS article
Meta: @id EnableSAMSBanner
Given Login to the SAMS Admin
And Publish the article 'Search Banner 2'
And User redirect to the Search application 'ClearCache'