#Author: bmacaluso@pch.com
Feature: Build a new basic profile
  In order to build a basic profile
  as a user I need to answer all basic profile questions


  @Desktop @RT-07379 
  Scenario Outline: On desktop device build a basic profile for "<userType>" user verify correct questions and answers are recorded in DB
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions
    Then I verify basic profile questions and answers exist in Redis Database
    |Question |Answer|
	  |23				|23_02|
	  |34				|34_02|
	  |20				|20_04|

    Examples: 
      | userType |
      | full reg |
      | mini reg |
      | guest    |
      | silver   |
      | social   |
  
  @Mobile @RT-07379
  Scenario Outline: On mobile device build a basic profile for "<userType>" user verify correct questions and answers are recorded in DB
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions
    Then I verify basic profile questions and answers exist in Redis Database
    |Question |Answer|
	  |23				|23_02|
	  |34				|34_02|
	  |20				|20_04|

    Examples: 
      | userType |
      | full reg |
      | mini reg |
      | guest    |
      | silver   |
      | social   |
