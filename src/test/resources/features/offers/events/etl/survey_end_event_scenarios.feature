#Author: bmacaluso@pch.com
Feature: Verify different survey end events are posted to the correct log files
  In order to verify survey end events are being logged
  user needs to land on the surveys page from different vendors

  @Desktop @Mobile
  Scenario Outline: "<userType>" user starts and completes a Lucid survey
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify survey end json is recorded in 'Lucid' EventsLogger
    And I verify 'Lucid' survey end event json contains the following elements
      | Element | Value    |
      | Status  | complete |

    Examples: 
      | userType |
      | full reg |

 

  @Desktop @Mobile
  Scenario Outline: "<userType>" user starts and incompletes a Lucid survey
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    Then I verify survey end json is recorded in 'Lucid' EventsLogger
    And I verify 'Lucid' survey end event json contains the following elements
      | Element | Value      |
      | Status  | incomplete |

    Examples: 
      | userType |
      | full reg |