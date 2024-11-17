Narrative: In order to verify Big fish Serp message on searching with bad terms
As a Tester
I want to do a search with bad terms(suspicious terms)  and verify the serp message 
Note: Bad terms are defined in admin


Meta: 
@TestingType Regression

Scenario: Verify serp page on searching with suspicious terms
Given Register a fully registered user through RF and login to the site
When Do search with suspicious Terms
Then Verify Bigfish ad on serp page
Then Verify Bigfish Serp message