#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression @RT-07723
Feature: Verify SQS log entries for survey start events
  In order to verify log entries
  as a user I need to select a vendor and verify ListenersLogger entries

  Scenario Outline: 2 Verify SURVEY_START event for the required mid start activity, when the user completed basic profile and landed on the suppliers/ surveys page. for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type         | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL            | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete  | Data.Tokens.Incomplete  | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
      | SURVEY_START | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  |           | https://www.pch.com/survey | 0,empty                    |             0 |                   15 |                  0.5 |           0 | empty       | 0,empty            |             0 | 5000,Desktop Complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | lucid           |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 3-4 Verify SURVEY_START event for the required survey start activity, when user takes the same mid again for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I wait for survey start page to load
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    Then I verify SQS events in the ListenersLogger log
      | Type         | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL            | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete                          | Data.Tokens.Incomplete                            | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
      | SURVEY_START | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  |           | https://www.pch.com/survey | 0,empty                    |             0 |                   10 |                 0.01 |           0 | empty       | 0,empty            |             0 | 500,Tokens for Auto_Lucid_Tokens mid complete | 200,token for survey Auto_Lucid_Tokens incomplete | 1,AW123               | 1,AW124                 | lucid           |
      | SURVEY_START | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | new                   | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  |           | https://www.pch.com/survey | 0,empty                    |             0 |                   10 |                 0.01 |           0 | empty       | 0,empty            |             0 | 500,Tokens for Auto_Lucid_Tokens mid complete | 200,token for survey Auto_Lucid_Tokens incomplete | 1,AW123               | 1,AW124                 | lucid           |

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
  Scenario Outline: 5 Verify the SURVEY_START event, when user completes GRL profile and landed on grl suppliers page for  "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_GRL'
    And I answer all basic profile questions as a "<userType>" user
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type         | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId | Data.MidKey | Data.Page | Data.RefererURL            | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete | Data.Tokens.Incomplete | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
      | SURVEY_START | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_GRL         | OK1ML3OIOO  |           | https://www.pch.com/survey |                            |               |                      |                      |             |             |                    |               |                      |                        |                       |                         |                 |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 6 Verify the SURVEY_START event, for alternate mid redirecting for  "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'auto_alttestmid'
    And I answer only non-sensitive questions as a "<userType>" user
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type         | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId | Data.MidKey | Data.Page | Data.RefererURL            | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete | Data.Tokens.Incomplete | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
      | SURVEY_START | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | auto_alttestmid  | 2OHML3HMM2  |           | https://www.pch.com/survey | 0,empty                    |             0 |                  100 |                  100 |           0 | empty       | 0,empty            |             0 | 0,empty              | 0,empty                | 0,empty               | 0,empty                 | lucid           |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 7 Verify the SURVEY_START event, for DNS mid redirecting for  "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'auto_dnstestmid'
    And I answer all basic profile questions as a "<userType>" user
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type         | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId | Data.MidKey | Data.Page | Data.RefererURL            | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete | Data.Tokens.Incomplete | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
      | SURVEY_START | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | auto_dnstestmid  | 3N1K15655M  |           | https://www.pch.com/survey | 0,empty                    |             0 |                   10 |                 0.01 |           0 | empty       | 0,empty            |             0 | 0,empty              | 0,empty                | 0,empty               | 0,empty                 | lucid           |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 8 Verify the SURVEY_START event,  event for Supplier redirect for  "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'auto_supplier_redirect'
    And I answer all basic profile questions as a "<userType>" user
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type         | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId       | Data.MidKey | Data.Page | Data.RefererURL            | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete                       | Data.Tokens.Incomplete                    | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
      | SURVEY_START | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | auto_supplier_redirect |             |           | https://www.pch.com/survey | 0,empty                    |             0 |                   15 |                  200 |           0 | empty       | 0,empty            |             0 | 100000,Participating in Todayu2019s survey | 10000,Participating in Todayu2019s survey | 0,null                | 0,null                  | qmee            |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |
