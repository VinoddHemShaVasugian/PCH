Feature: Verify different survey start event are posted to the correct log files
  In order to verify survey start events are being logged
  user needs to land on the surveys page and start taking surveys from different vendors

  @Desktop @Mobile
  Scenario: 1 Verify start event is recorded in log and the loi and cpi values are present in Lucid start event json
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    Then I verify 'Lucid' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |     0 |
      | MinimumGrossCPI |   0.1 |

  @Desktop @Mobile
  Scenario: 2 Verify start event is recorded in log and the the loi and cpi values are present in LucidApi start event json
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I select a survey 'VinLucidApiTokens'
    And I answer all basic profile questions
    Then I verify 'LucidApi' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |     0 |
      | MinimumGrossCPI |     0 |

  @Desktop @Mobile
  Scenario: 3 Verify start event is recorded in log and the loi and cpi values are present in Jebbitt start event json
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I select a survey 'jebbit'
    And I answer all basic profile questions
    Then I verify 'Jebbit' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |     0 |
      | MinimumGrossCPI |     0 |

  @Desktop @Mobile
  Scenario: 4 Verify start event is recorded in log and the loi and cpi values are present in Qmee start event json
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I select a survey 'VinQmeeTokens'
    And I answer all basic profile questions
    Then I verify 'Qmee' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |     1 |
      | MinimumGrossCPI |    50 |

  @Desktop @Mobile
  Scenario: 5 Verify start event is recorded in log and the loi and cpi values are present in PureSpectrum start event json
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I navigates to survey 'VinSurveyTabIW_PS'
    And I answer all basic profile questions
    Then I verify 'PURESPECTRUM' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    15 |
      | MinimumGrossCPI |     1 |

  @Desktop @Mobile
  Scenario: 6 Verify start event is recorded in log and the loi and cpi values are present in GRL start event json
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I navigates to survey 'grl'
    And I answer all basic profile questions
    Then I verify 'GRL' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    30 |
      | MinimumGrossCPI |     1 |

      
 @Desktop @Mobile
  Scenario: 7 Verify start event is recorded in log and the correct DEFAULT loi and cpi values are present in Lucid start event json
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I navigates to survey 'luciddefault'
    And I answer all basic profile questions
    Then I verify 'lucid' survey start event json contains the following elements
      | Element         | Value|
      | LengthInMinutes |   15 |
      | MinimumGrossCPI |   0.05|