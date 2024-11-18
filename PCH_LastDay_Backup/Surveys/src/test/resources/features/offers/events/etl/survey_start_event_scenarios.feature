#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression
Feature: Verify different survey start event are posted to the correct log files
  In order to verify survey start events are being logged
  user needs to land on the surveys page and start taking surveys from different vendors

  Scenario Outline: Verify start event is recorded in log and the correct values are present in LUCID start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    Then I verify 'Lucid' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType | SurveyId | PlacementId       | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              15 |             0.5 | notnull | empty  | lucid      | notnull  | Auto_Lucid_Tokens | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | link            |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct DEFAULT values are present in LUCID start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens_Default'
    And I answer all basic profile questions as a "<userType>" user
    Then I verify 'Lucid' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType | SurveyId | PlacementId               | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              15 |             0.1 | notnull | empty  | lucid      |        0 | Auto_Lucid_Tokens_Default | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | link            |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct values are present in LUCIDAPI start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_VinLucidApiTokens'
    And I answer all basic profile questions as a "<userType>" user
    Then I verify 'LucidApi' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType | SurveyId | PlacementId            | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              20 |             0.5 | notnull | empty  | lucid      | notnull  | Auto_VinLucidApiTokens | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | api             |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct DEFAULT values are present in LUCIDAPI start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    When I navigates to survey 'Auto_lucidApiDefault'
    And I answer all basic profile questions as a "<userType>" user
    Then I verify 'LucidApi' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType | SurveyId | PlacementId          | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              15 |             0.1 | notnull | empty  | lucid      | null     | Auto_lucidApiDefault | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | api             |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct values are present in PURESPECTRUM API start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PSplacement'
    And I close the Take a Tour popup
    And I answer all basic profile questions as a "<userType>" user
    And I pause execution for 1 seconds
    Then I verify 'PURESPECTRUM' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType         | SurveyId | PlacementId      | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              20 |             0.5 | notnull | empty  | purespectrumfusion |        0 | Auto_PSplacement | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | hybrid          |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct DEFAULT values are present in PURESPECTRUM API start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_psdefault'
    And I close the Take a Tour popup
    And I answer all basic profile questions as a "<userType>" user
    And I pause execution for 1 seconds
    Then I verify 'PURESPECTRUM' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType         | SurveyId | PlacementId    | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              15 |             0.1 | notnull | empty  | purespectrumfusion |        0 | Auto_psdefault | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | hybrid          |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct values are present in QMEE start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_VinQmeeTokens'
    And I answer all basic profile questions as a "<userType>" user
    And I pause execution for 1 seconds
    Then I verify 'Qmee' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType | SurveyId | PlacementId        | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              20 |             0.5 | notnull | empty  | qmee       | empty    | Auto_VinQmeeTokens | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | link            |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct DEFAULT values are present in QMEE start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_qmeedefault'
    And I answer all basic profile questions as a "<userType>" user
    And I pause execution for 1 seconds
    Then I verify 'Qmee' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType | SurveyId | PlacementId      | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              15 |             0.1 | notnull | empty  | qmee       | empty    | Auto_qmeedefault | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | link            |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  @RT-07463
  Scenario Outline: Verify start event is recorded in log and the correct values are present in GRL start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_GRL'
    And I answer all basic profile questions as a "<userType>" user
    And I pause execution for 1 seconds
    Then I verify 'GRL' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType      | SurveyId | PlacementId | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              20 |             0.5 | notnull | empty  | generalresearch | null     | Auto_GRL    | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | hybrid          |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  @RT-07463
  Scenario Outline: Verify start event is recorded in log and the correct DEFAULT values are present in GRL start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_grldefault'
    And I answer all basic profile questions as a "<userType>" user
    And I pause execution for 1 seconds
    Then I verify 'GRL' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType      | SurveyId | PlacementId     | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              15 |             0.1 | notnull | empty  | generalresearch | notnull  | Auto_grldefault | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | hybrid          |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct values are present in JEBBIT start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Jebbit'
    And I answer all basic profile questions as a "<userType>" user
    Then I verify 'Jebbit' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType | SurveyId | PlacementId | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete |
      | PCHCOM | fromReg | fromReg |        0 |              20 |             0.5 | notnull | empty  | jebbit     |        0 | Auto_Jebbit | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct DEFAULT values are present in JEBBIT start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Jebbit_Default'
    And I answer all basic profile questions as a "<userType>" user
    Then I verify 'Jebbit' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType | SurveyId | PlacementId         | ContestEntriesGranted | TokensGranted | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete |         |
      | PCHCOM | fromReg | fromReg |        0 |              15 |             0.1 | notnull | empty  | jebbit     |        0 | Auto_Jebbit_Default | 0,empty               | 0,empty       | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | 0,empty |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct values are present in PURE SPECTRUM FUSION start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PsFusion'
    And I answer all basic profile questions as a "<userType>" user
    Then I verify 'PURESPECTRUM' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType         | SurveyId | PlacementId   | ContestEntriesGranted | TokensGranted | SurveyTokensComplete          | SurveyTokensInComplete          | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              20 |             0.5 | notnull | empty  | purespectrumfusion |        0 | Auto_PsFusion | 0,empty               | 0,empty       | 1000,auto psf survey complete | 1000,auto psf survey incomplete | 1,AW123               | 1,AW124                 | link            |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: Verify start event is recorded in log and the correct DEFAULT values are present in PURE SPECTRUM FUSION start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PsFusion_Default'
    And I answer all basic profile questions as a "<userType>" user
    Then I verify 'PURESPECTRUM' survey start event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status | SurveyType         | SurveyId | PlacementId           | ContestEntriesGranted | TokensGranted | SurveyTokensComplete          | SurveyTokensInComplete          | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |              15 |             0.1 | notnull | empty  | purespectrumfusion |        0 | Auto_PsFusion_Default | 0,empty               | 0,empty       | 1000,auto psf survey complete | 1000,auto psf survey incomplete | 0,empty               | 0,empty                 | link            |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |
