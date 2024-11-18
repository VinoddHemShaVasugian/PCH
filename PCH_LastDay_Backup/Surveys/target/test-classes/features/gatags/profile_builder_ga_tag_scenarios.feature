#Author: bmacaluso@pch.com
@Regression @GaTags
Feature: Build a new user profile
  In order to build a profile
  as a user I need to first sign in or create new registration
  and answer all presented questions

  @Desktop
  Scenario: User answers 1 profile builder question verify GA answer saved tag is fired
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click Profile Builder lets go button
    And The User Signs In with email 'fromreg' and password 'fromreg'
    And I select a profile category 'home'
    And I answer 1 profile builder questions
    And I pause execution for 2 seconds
    Then Verify All GA Tags
      | EventCategory                   | EventAction | EventLabel |
      | surveytab/updateprofile/desktop | save        |         20 |

  @Mobile
  Scenario: User answers 1 profile builder question verify GA answer saved tag is fired
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click Profile Builder lets go button
    And The User Signs In with email 'fromreg' and password 'fromreg'
    And I select a profile category 'home'
    And I answer 1 profile builder questions
    And I pause execution for 2 seconds
    Then Verify All GA Tags
      | EventCategory                  | EventAction | EventLabel |
      | surveytab/updateprofile/mobile | save        |         20 |