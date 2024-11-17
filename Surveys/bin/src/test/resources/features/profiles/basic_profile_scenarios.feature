Feature: Build a new basic profile
  In order to build a basic profile
  as a user I need to answer all basic profile questions
 
 
@Desktop @GaTags 
Scenario: Build a basic profile for recognized full reg user verify correct count in DB and GA tag
Given I land on the Surveys Page as a recognized full reg user
And I close the Take a Tour popup
When I click first Take Survey Button
Then I verify The question completed count is '0 / 12'
When I answer all basic profile questions
And I verify question count 12 matches in Redis Database
Then Verify All GA Tags
|EventCategory									|EventAction					|EventLabel				|
|surveytab/basicprofile/desktop	|profilecomplete	   	|survey/profile		|



@Mobile @GaTags 
Scenario: Build a basic profile for recognized full reg user verify correct count in DB and GA tag
Given I land on the Surveys Page as a recognized full reg user
And I close the Take a Tour popup
When I click first Take Survey Button
Then I verify The question completed count is '0 / 12'
When I answer all basic profile questions
And I verify question count 12 matches in Redis Database
Then Verify All GA Tags
|EventCategory									|EventAction					|EventLabel				|
|surveytab/basicprofile/mobile	|profilecomplete	   	|survey/profile		|
