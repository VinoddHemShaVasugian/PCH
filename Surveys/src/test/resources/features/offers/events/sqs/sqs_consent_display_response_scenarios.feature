#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression @RT-07747
Feature: Verify SQS log entries for consent display and response events
  In order to verify log entries
  as a user I need to select a vendor and verify ListenersLogger entries

  Scenario Outline: 1 Verify CONSENT_BOX_DISPLAY and CONSENT_RESPONSE is Yes events for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type                | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl                                    | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL                                  | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete | Data.Tokens.Incomplete | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe | Data.Consent |
      | CONSENT_BOX_DISPLAY | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/profilebuilder/consent/counter | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://www.qa.pch.com/                          |                            |               |                      |                      |             |             |                    |               |                      |                        |                       |                         |                 |              |
      | CONSENT_RESPONSE    | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/profilebuilder/consent         | empty                | PCHCOM     | previous             | previous              | previous            |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://offers.qa.pch.com/profilebuilder/consent |                            |               |                      |                      |             |             |                    |               |                      |                        |                       |                         |                 | Yes          |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 2 Verify CONSENT_BOX_DISPLAY and CONSENT_RESPONSE is No events for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer only non-sensitive questions as a "<userType>" user
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type                | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl                                    | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL                                  | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete | Data.Tokens.Incomplete | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyTYpe | Data.Consent |
      | CONSENT_BOX_DISPLAY | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/profilebuilder/consent/counter | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://www.qa.pch.com/                          |                            |               |                      |                      |             |             |                    |               |                      |                        |                       |                         |                 |              |
      | CONSENT_RESPONSE    | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/profilebuilder/consent         | empty                | PCHCOM     | previous             | previous              | previous            |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://offers.qa.pch.com/profilebuilder/consent |                            |               |                      |                      |             |             |                    |               |                      |                        |                       |                         |                 | No           |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |
