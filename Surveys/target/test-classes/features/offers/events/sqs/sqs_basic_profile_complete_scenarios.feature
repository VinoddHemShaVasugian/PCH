#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression @RT-07720
Feature: Verify SQS log entries for basic profile completed events
  In order to verify log entries
  as a user I need to select a vendor and verify ListenersLogger entries

  Scenario Outline: 1 Verify PROFILE_COMPLETED event for the required profile completed activity, for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type              | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl                                   | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL                                         | Data.Consent |
      | PROFILE_COMPLETED | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey/profile/complete/basic | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://offers.qa.pch.com/survey/profile/complete/basic | Yes          |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 2 Verify PROFILE_COMPLETED event for the required profile completed activity, for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions
    And I wait for survey start page to load
    Then I verify SQS events in the ListenersLogger log
      | Type              | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl             | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL                   | Data.Consent |
      | PROFILE_COMPLETED | true       | empty       | notnull       | DESKTOP           | https://offers.qa.pch.com/survey/ | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://offers.qa.pch.com/survey/ | Yes          |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |
