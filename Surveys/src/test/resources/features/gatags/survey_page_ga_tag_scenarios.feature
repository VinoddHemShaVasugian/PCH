#Author: bmacaluso@pch.com
Feature: Verify ga tags are firing as expected accross all devices
  In order to verify qa tags events are being fired
  user needs to land on the surveys page and start interacting

 

  @Desktop @GaTags @RT-05833 
  Scenario: Recognized full reg user on Survey page clicks the Redeem Tokens image verify GA tag for desktop
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    When I click the Redeem Tokens image
    And I pause execution for 2 seconds
    Then Verify All GA Tags
      | EventCategory          | EventAction | EventLabel |
      | uninav/desktop/desktop | click       | pchrewards |

  @Mobile @GaTags
  Scenario: Recognized full reg user on Survey page clicks the Redeem Tokens image verify GA tag for desktop
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    When I click the Redeem Tokens image
    And I pause execution for 2 seconds
    Then Verify All GA Tags
      | EventCategory | EventAction | EventLabel |
      | uninav/mobile | click       | pchrewards |

  @Desktop @GaTags
  Scenario: Recognized full reg user on Survey page clicks the Yes Im In button in handraiser section verify GA tag for desktop
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    When I click the Yes Im In button in survey handraiser section
    And I pause execution for 2 seconds
    Then Verify All GA Tags
      | EventCategory            | EventAction | EventLabel     |
      | lucid/survey_tab/desktop | click       | yes_handraiser |

  @Mobile @GaTags
  Scenario: Recognized full reg user on Survey page clicks the Yes Im In button in handraiser section verify GA tag for mobile
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    When I click the Yes Im In button in survey handraiser section
    And I pause execution for 2 seconds
    Then Verify All GA Tags
      | EventCategory           | EventAction | EventLabel     |
      | lucid/survey_tab/mobile | click       | yes_handraiser |
