#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression
Feature: Verify different survey end events are posted to the correct log files
  In order to verify survey end events are being logged
  user needs to land on the surveys page from different vendors

  # no end events for Qmee
  Scenario Outline: "<userType>" user starts and completes a Lucid survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify 'Lucid' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status   | SurveyType | SurveyId | PlacementId       | ContestEntriesGranted | TokensGranted         | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |               0 |               0 | notnull | complete | lucid      | notnull  | Auto_Lucid_Tokens | 1,AW123               | 5000,Desktop Complete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | link            |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: "<userType>" user starts and incompletes a Lucid survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    Then I verify 'Lucid' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status     | SurveyType | SurveyId | PlacementId       | ContestEntriesGranted | TokensGranted           | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |               0 |               0 | notnull | incomplete | lucid      | notnull  | Auto_Lucid_Tokens | 1,AW124               | 5000,Desktop incomplete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | link            |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: "<userType>" user starts and completes a LUCIDAPI survey
    Given I land on the Surveys Page as a "<userType>" user
    When I insert all basic profile questions and answers into Redis DB
    And I navigates to survey 'Auto_VinLucidApiTokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify 'LucidApi' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status   | SurveyType | SurveyId | PlacementId            | ContestEntriesGranted | TokensGranted         | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |               0 |               0 | notnull | complete | lucid      | notnull  | Auto_VinLucidApiTokens | 1,AW123               | 5000,Desktop Complete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | api             |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: "<userType>" user starts and incompletes a LUCIDAPI survey
    Given I land on the Surveys Page as a "<userType>" user
    When I insert all basic profile questions and answers into Redis DB
    And I navigates to survey 'Auto_VinLucidApiTokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    Then I verify 'LucidApi' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status     | SurveyType | SurveyId | PlacementId            | ContestEntriesGranted | TokensGranted           | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg |        0 |               0 |               0 | notnull | incomplete | lucid      | notnull  | Auto_VinLucidApiTokens | 1,AW124               | 5000,Desktop incomplete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | api             |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  @RT-07400
  Scenario Outline: "<userType>" user starts and completes a PURESPECTRUMFUSION survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PsFusion'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify 'PURESPECTRUM' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status   | SurveyType         | SurveyId | PlacementId   | ContestEntriesGranted | TokensGranted         | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg | notnull  |               0 |               0 | notnull | complete | purespectrumfusion |        0 | Auto_PsFusion | 1,AW123               | 5000,Desktop Complete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | link            |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: "<userType>" user starts and incompletes a PURESPECTRUMFUSION survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PsFusion'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    Then I verify 'PURESPECTRUM' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status     | SurveyType         | SurveyId | PlacementId   | ContestEntriesGranted | TokensGranted           | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg | notnull  |               0 |               0 | notnull | incomplete | purespectrumfusion |        0 | Auto_PsFusion | 1,AW124               | 5000,Desktop incomplete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | link            |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: "<userType>" user starts and completes a GRL survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_GRL'
    And I answer all basic profile questions as a "<userType>" user
    When I generate the GRL survey endpoint url with 'complete' status
    When I call the survey endpoint url
    Then I verify 'GRL' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status   | SurveyType      | SurveyId | PlacementId | ContestEntriesGranted | TokensGranted         | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg | notnull  |               0 |               0 | notnull | complete | generalresearch | null     | Auto_GRL    | 1,AW123               | 5000,Desktop Complete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | hybrid          |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: "<userType>" user starts and incompletes a GRL survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_GRL'
    And I answer all basic profile questions as a "<userType>" user
    When I generate the GRL survey endpoint url with 'incomplete' status
    When I call the survey endpoint url
    Then I verify 'GRL' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status     | SurveyType      | SurveyId | PlacementId | ContestEntriesGranted | TokensGranted           | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete | IntegrationMode |
      | PCHCOM | fromReg | fromReg | notnull  |               0 |               0 | notnull | incomplete | generalresearch | null     | Auto_GRL    | 1,AW124               | 5000,Desktop incomplete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | hybrid          |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |

  @RT-07400
  Scenario Outline: "<userType>" user starts and completes a PURESPECTRUM API survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PSplacement'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify 'PURESPECTRUMAPI' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status   | SurveyType         | SurveyId | PlacementId      | ContestEntriesGranted | TokensGranted         | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete |
      | PCHCOM | fromReg | fromReg | notnull  |               0 |               0 | notnull | complete | purespectrumfusion |        0 | Auto_PSplacement | 1,AW123               | 5000,Desktop Complete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  @RT-07400
  Scenario Outline: "<userType>" user starts and incompletes a PURESPECTRUM API survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PSplacement'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    Then I verify 'PURESPECTRUMAPI' survey end event json contains the following elements
      | CBL    | OAT     | GMT     | Duration | LengthInMinutes | MinimumGrossCPI | Payout  | Status     | SurveyType         | SurveyId | PlacementId      | ContestEntriesGranted | TokensGranted           | SurveyTokensComplete  | SurveyTokensInComplete  | SurveyEntriesComplete | SurveyEntriesInComplete |
      | PCHCOM | fromReg | fromReg | notnull  |               0 |               0 | notnull | incomplete | purespectrumfusion |        0 | Auto_PSplacement | 1,AW124               | 5000,Desktop incomplete | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 |

    Examples: 
      | userType |
      | full reg |
      | guest    |
      | mini reg |
      | silver   |
