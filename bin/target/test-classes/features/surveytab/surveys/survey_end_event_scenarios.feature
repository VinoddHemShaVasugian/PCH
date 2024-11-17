Feature: Verify different survey end events are posted to the correct log files
  In order to verify survey end events are being logged
  user needs to land on the surveys page from different vendors

  @Desktop @Mobile
  Scenario: Recognized full reg user starts and completes a Lucid survey
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I select a survey 'lucid'
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify survey end json is recorded in 'Lucid' EventsLogger
    And I verify 'Lucid' survey end event json contains the following elements
      | Element | Value    |
      | Status  | complete |

  @Desktop @Mobile
  Scenario: Recognized full reg user starts and incompletes a Lucid survey
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I select a survey 'lucid'
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    Then I verify survey end json is recorded in 'Lucid' EventsLogger
    And I verify 'Lucid' survey end event json contains the following elements
      | Element | Value      |
      | Status  | incomplete |

      
      
      
       