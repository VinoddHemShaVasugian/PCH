Narrative:
In order to verify NFSP in browser console 
As a Tester
I want to configure the expected NFSP (Source and Segments) in Joomla admin

Meta:
@TestCaseId RT-04306
@TestCaseId RT-04301
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:Config_SitePageSearch},
stories/sw/jm/SearchAdmin.story#{id:Config_Infospace},
stories/sw/jm/SearchAdmin.story#{id:Config_RiskManagement}

Scenario: Verify default NFSP
Meta: 
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When Do a search and Verify SERP page
Then Verify NFSP source and segment as per admin config 'nfspAccessIds','searchbox_hiddenInputs.nfsp'

Scenario: Verify the algo NFSP value for UnRecognised User 
Meta: 
@TestingType Sanity
Given Goto Search and Win Site
When Perform consecutive searches
Then Verify algo NFSP source and segment as per admin config 'nfspAccessIds','mx_d','mx_t','mx_m'

Scenario: Verify the algo NFSP value for Recognised User
Meta: 
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When Do a search and Verify SERP page
And Reset daily search count 'algo_only_searches_recognized'
Then Do a search and Verify SERP page
And Verify algo NFSP source and segment as per admin config 'nfspAccessIds','mx_d','mx_t','mx_m'

Scenario: Verify algo NFSP value for algo only segmented users
Given Register a fully registered user through RF
And Assign the segment based on admin property 'algo_only_segment'
When Do a search and Verify SERP page
Then Verify algo NFSP source and segment as per admin config 'nfspAccessIds','mx_d','mx_t','mx_m'
And User redirect to the Search application 'nfsp=s1gs5'
And Do a search and Verify SERP page
And Verify algo NFSP source and segment as per admin config 'nfspAccessIds','mx_d','mx_t','mx_m'

Scenario: Verify segmented NFSP value for segmented users
Given Register a fully registered user through RF
And Assign segment by code 'SST'
And Retrive segmented nfsp values 'SST'
And Login to the SW Application as recent user
When Do a search and Verify SERP page
Then Verify NFSP source and segment as per admin config 'nfspAccessIds','segmentednfsp0'
And Do a search and Verify SERP page
And Verify NFSP source and segment as per admin config 'nfspAccessIds','segmentednfsp1'
And User redirect to the Search application 'nfsp=s1gs5'
And Do a search and Verify SERP page
And Verify NFSP source and segment as per admin config 'nfspAccessIds','segmentednfsp1'

Scenario: Verify NFSP value by passing NFSP in URL
Given Register a fully registered user through RF and login to the site
When Do a search and Verify SERP page
Then Verify NFSP source and segment as per admin config 'nfspAccessIds','searchbox_hiddenInputs.nfsp'
And User redirect to the current URL 'nfsp=s1gs5'
And Verify NFSP source and segment as per admin config 'nfspAccessIds','s1gs5'