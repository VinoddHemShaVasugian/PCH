Narrative:
In order to verify the High Risk light box
As a user
I want to logged into the application with valid user name and password

Meta:
@TestCaseId RT-04299
@TestingType Regression
GivenStories: stories/sw/jm/SearchAdmin.story#{id:Config_RiskManagement}

Scenario: Verify the display of the Rapid search light box
Meta:
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
Then Do a search with a random keyword for to admin configured property value 'duplicate_search_max_rate'
And Validate the display of the Rapid search light box
And Do a search with a random keyword for to admin configured property value 'duplicate_search_max_rate'
And Validate the display of the Rapid search light box
And Do a search with a random keyword for to admin configured property value 'duplicate_search_max_rate'
And Validate the absence of the Rapid search light box

Scenario: Verify the display of the 26th max search light box
Meta:
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
And Do a search on Homepage with a random keyword
And Update the search count on backend to admin configured property 'max_chances_per_day'
Then Do a search on SERPpage with a random keyword
And Validate the display of the Max search light box

Scenario: Verify the display of the 40th warning light box
Given Register a fully registered user through RF and login to the site
And Do a search on Homepage with a random keyword
And Update the search count on backend to admin configured property 'search_warnings_first'
Then Do a search on SERPpage with a random keyword
And Validate the display of the Search Warning light box

Scenario: Verify the display of the 75th search warning light box
Given Register a fully registered user through RF and login to the site
And Do a search on Homepage with a random keyword
And Update the search count on backend to admin configured property 'search_warnings_later'
Then Do a search on SERPpage with a random keyword
And Validate the display of the Search Warning light box

Scenario: Verify the display of the 500th disable search light box
Meta:
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
And Do a search on Homepage with a random keyword
And Update the search count on backend to admin configured property 'max_searches_per_day'
Then Do a search on SERPpage with a random keyword
And Validate the display of the Search Disable light box
And Do a search on SERPpage with a random keyword
And Verify the dbproperty searchingenabled with value as 0
And Verify the dbproperty searchingenabled with expires value as 0