#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression @RT-07721
Feature: Verify SQS log entries for basic profile display events
  In order to verify log entries
  as a user I need to select a vendor and verify ListenersLogger entries

  Scenario Outline: 1-2-3 Verify BASIC_PROFILE_DISPLAY event when landing on basic profile page for  "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    Then I verify SQS events in the ListenersLogger log
      | Type                  | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl                          | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId  | Data.MidKey | Data.Page | Data.RefererURL                                 |
      | BASIC_PROFILE_DISPLAY | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey/profile/basic | empty                | PCHCOM     | notnull              | notnull               | empty               |                             | fromReg  | fromReg  | Auto_Lucid_Tokens | 4HOIIL1K1O  | basic     | https://offers.qa.pch.com/survey/profile/basic? |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 4-5-6 Verify the GRL_PROFILE_DISPLAY event, when user lands on GRL profile page for  "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_GRL'
    Then I verify SQS events in the ListenersLogger log
      | Type                | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl                          | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId | Data.MidKey | Data.Page | Data.RefererURL                                                      |
      | GRL_PROFILE_DISPLAY | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey/profile/basic | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_GRL         | OK1ML3OIOO  | basic     | https://offers.qa.pch.com/survey/profile/basic?msk=OK1ML3OIOO-f38ed8 |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 7-8-9 Verify the HANDRAISER_PROFILE_DISPLAY event, when user lands on handraiser profile page for  "<userType>" user
    Given I land on the Surveys Page as a bla bla bla
