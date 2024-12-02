#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression @RT-07722
Feature: Verify SQS log entries for mid start events
  In order to verify log entries
  as a user I need to select a vendor and verify ListenersLogger entries

  Scenario Outline: 1 Verify MID_START event for the required survey start activity for  "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    Then I verify SQS events in the ListenersLogger log
      | Type      | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.RefererURL                                        | Data.Suppression | Data.DNC | Data.RTL | Data.Page |
      | MID_START | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | https://offers.qa.pch.com/survey?mid=Auto_Lucid_Tokens |                  |          |          |           |

    @Sanity
    Examples: 
      | userType |
      | full reg |

  Scenario Outline: 2 Verify MID_START event for the required mid start activity, when the user completed basic profile and landed on the suppliers/ surveys page. for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type      | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL                                        | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete | Data.Tokens.Incomplete | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
      | MID_START | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  |           | https://offers.qa.pch.com/survey?mid=Auto_Lucid_Tokens |                            |               |                      |                      |             |             |                    |               |                      |                        |                       |                         |                 |

    Examples: 
      | userType |
      | full reg |

  Scenario Outline: 3-4 Verify MID_START event for the required survey start activity, when user takes the same mid again for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions
    And I wait for survey start page to load
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    Then I verify SQS events in the ListenersLogger log
      | Type      | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.RefererURL                                        | Data.Suppression | Data.DNC | Data.RTL | Data.Page |
      | MID_START | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | https://offers.qa.pch.com/survey?mid=Auto_Lucid_Tokens |                  |          |          |           |
      | MID_START | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | new                   | notnull             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | https://offers.qa.pch.com/survey?mid=Auto_Lucid_Tokens |                  |          |          |           |

    Examples: 
      | userType |
      | full reg |

  #
  #@RT-07723 @RT-07722 @RT-07721 @RT-07720 @Desktop
  #Scenario Outline: 3 Verify MID_START event for the required survey start activity, when user takes the same mid again for "<userType>" user
  #Given I land on the Surveys Page as a "<userType>" user
  #And I navigates to survey 'Auto_Lucid_Tokens'
  #And I answer all basic profile questions
  #And I wait for survey start page to load
  #And I generate the survey endpoint url with 'complete' status
  #And I call the survey endpoint url
  #And I navigates to survey 'Auto_Lucid_Tokens'
  #Then I verify SQS events in the ListenersLogger log
  #| Type                  | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl                                    | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL                                                      | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted                            | Data.SurveyId | Data.Tokens.Complete                          | Data.Tokens.Incomplete                            | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
  #| MID_START             | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey                         | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  |           | https://offers.qa.pch.com/survey?mid=Auto_Lucid_Tokens               |                            |               |                      |                      |             |             |                                               |               |                                               |                                                   |                       |                         |                 |
  #| BASIC_PROFILE_DISPLAY | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey/profile/basic           | empty                | PCHCOM     | previous             | previous              | previous            |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://offers.qa.pch.com/survey/profile/basic?msk=4HOIIL1K1O-27f821 |                            |               |                      |                      |             |             |                                               |               |                                               |                                                   |                       |                         |                 |
  #| CONSENT_BOX_DISPLAY   | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/profilebuilder/consent/counter | empty                | PCHCOM     | previous             | previous              | previous            |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://www.qa.pch.com/                                              |                            |               |                      |                      |             |             |                                               |               |                                               |                                                   |                       |                         |                 |
  #| PROFILE_COMPLETED     | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey/profile/complete/basic  | empty                | PCHCOM     | previous             | previous              | previous            |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://offers.qa.pch.com/survey/profile/complete/basic              |                            |               |                      |                      |             |             |                                               |               |                                               |                                                   |                       |                         |                 |
  #| SURVEY_START          | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey                         | empty                | SURVEYTAB  | previous             | previous              | previous            | empty                       | fromReg  | fromReg  | Auto_Lucid_Tokens |             |           | https://www.pch.com/survey                                           | 0,empty                    |             0 |                   10 |                 0.01 |           0 | empty       | 0,empty                                       |             0 | 500,Tokens for Auto_Lucid_Tokens mid complete | 200,token for survey Auto_Lucid_Tokens incomplete | 1,AW123               | 1,AW124                 | lucid           |
  #| SURVEY_END            | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/surveycomplete                 | empty                | SURVEYTAB  | previous             | previous              | previous            | empty                       | fromReg  | fromReg  | Auto_Lucid_Tokens |             |           | https://www.pch.com/surveycomplete                                   | 1,AW123                    |             0 |                    0 |                    0 | notnull     | complete    | 500,Tokens for Auto_Lucid_Tokens mid complete |       8823141 | 500,Tokens for Auto_Lucid_Tokens mid complete | 200,token for survey Auto_Lucid_Tokens incomplete | 1,AW123               | 1,AW124                 | lucid           |
  #| MID_START             | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey                         | empty                | PCHCOM     | new                  | new                   | previous            |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  |           | https://offers.qa.pch.com/survey?mid=Auto_Lucid_Tokens               |                            |               |                      |                      |             |             |                                               |               |                                               |                                                   |                       |                         |                 |
  #| SURVEY_START          | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey                         | empty                | SURVEYTAB  | previous             | previous              | previous            | empty                       | fromReg  | fromReg  | Auto_Lucid_Tokens |             |           | https://www.pch.com/survey                                           | 0,empty                    |             0 |                   10 |                 0.01 | notnull     | empty       | 0,empty                                       |             0 | 500,Tokens for Auto_Lucid_Tokens mid complete | 200,token for survey Auto_Lucid_Tokens incomplete | 1,AW123               | 1,AW124                 | lucid           |
  #
  #Examples:
  #| userType |
  #| full reg |
  Scenario Outline: 5 Verify the MID_START event, when user completes GRL profile and landed on grl suppliers page for  "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_GRL'
    Then I verify SQS events in the ListenersLogger log
      | Type      | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId | Data.MidKey | Data.Page | Data.RefererURL                               | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete | Data.Tokens.Incomplete | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe |
      | MID_START | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | SURVEYTAB  | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_GRL         | OK1ML3OIOO  |           | https://offers.qa.pch.com/survey?mid=Auto_GRL |                            |               |                      |                      |             |             |                    |               |                      |                        |                       |                         |                 |

    Examples: 
      | userType |
      | full reg |

  Scenario Outline: 6 Verify the MID_START event, for alternate mid redirecting for  "<userType>" user
    Given I need to implement this test

  Scenario Outline: 7 Verify the MID_START event, for DNS mid redirecting for  "<userType>" user
    Given I need to implement this test

  Scenario Outline: 8 Verify the MID_START event,  event for Supplier redirect for  "<userType>" user
    Given I need to implement this test
