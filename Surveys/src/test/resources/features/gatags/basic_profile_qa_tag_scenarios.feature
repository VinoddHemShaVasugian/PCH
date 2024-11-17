#Author: bmacaluso@pch.com
Feature: Build a new basic profile
  In order to build a basic profile
  as a user I need to answer all basic profile questions

  @Desktop @GaTags @RT-07379 
  Scenario Outline: On desktop device Build a basic profile for "<userType>" user verify correct count in DB and GA tag
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions
    Then Verify All GA Tags
      | EventCategory                  	| EventAction	     | EventLabel     |
      | surveytab/basicprofile/desktop 	| profilecomplete	 | survey/profile |
      | surveytab/desktop 							| impression			 | |

    Examples: 
      | userType |
      | full reg |
      | mini reg |
      | guest    |
      | silver   |
      | social   |

  @Mobile @GaTags @RT-07379
  Scenario Outline: On mobile device Build a basic profile for "<userType>" user verify correct count in DB and GA tag
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions
    Then Verify All GA Tags
      | EventCategory                  	| EventAction     | EventLabel     |
      | surveytab/basicprofile/mobile 	| profilecomplete | survey/profile |
      | surveytab/mobile 								| impression  		| 							 |

    Examples: 
      | userType |
      | full reg |
      | mini reg |
      | guest    |
      | silver   |
      | social   |
