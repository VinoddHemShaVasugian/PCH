#Author: mmittal@pch.com
@Desktop @Mobile @Regression
Feature: Verify SQS log entries for missed opportunity events
  In order to verify log entries
  as a user I need to select a vendor and verify ListenersLogger entries

  Scenario Outline: 1 Verify MISSED_OPPORTUNITY event for the required Lucid API mid, when the user completed basic profile and trying to land on surveys page for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_VinLucidApiTokens'
    And I answer all basic profile questions as a "<userType>" user
    And I redirected back to Survey homepage
    Then I verify SQS events in the ListenersLogger log
      | Type               | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId       | Data.MidKey | Data.Page | Data.RefererURL      										       | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.Tokens.Complete  | Data.Tokens.Incomplete  | Data.Entries.Complete | Data.Entries.Incomplete | Data.SurveyType | Data.IntegrationMode|
      | MISSED_OPPORTUNITY | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | PCHCOM     | notnull              | notnull               | notnull             |                             | fromReg  | fromReg  | Auto_VinLucidApiTokens | KKG3G11L1K  |           | https://offers.qa.pch.com/survey/profile/basic? | 0,empty                    |             0 |                   20 |                  0.5 |           0 | empty       | 0,empty            |             0 | 5000,Desktop complete | 5000,Desktop incomplete | 1,AW123               | 1,AW124                 | lucid           | api|

    @Sanity
    Examples: 
      | userType |
      | full reg |

    #Examples: 
      #| userType |
      #| guest    |
      #| mini reg |
      #| silver   |


@RT-07768
	Scenario Outline: 2 Verify MISSED_OPPORTUNITY event for the required PS API mid, when the user completed basic profile and trying to land on surveys page for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PSplacement'
    And I answer all basic profile questions as a "<userType>" user
    And I redirected back to Survey homepage
    Then I verify SQS events in the ListenersLogger log
      | Type                         | IsTestData | ReferenceId | TransactionId | Client.DeviceType | Source.OriginatingUrl            | Source.TrackingToken | Source.CBL	  | Session.SessionToken | Session.MidSessionKey | Session.GaSessionId | Session.ParentMidSessionKey | Data.GMT | Data.OAT | Data.PlacementId | Data.MidKey | Data.Page | Data.RefererURL   			         | Data.ContestEntriesGranted | Data.Duration | Data.LengthInMinutes | Data.MinimumGrossCPI | Data.Payout | Data.Status | Data.TokensGranted | Data.SurveyId | Data.SurveyType    | Data.IntegrationMode|
      | SRE_MATCH_MISSED_OPPORTUNITY | true       | notnull     | notnull       | DESKTOP           | https://offers.qa.pch.com/survey | empty                | SURVEYTAB     | notnull              | notnull               | notnull      		   |                             | fromReg  | fromReg  | Auto_PSplacement | P2IG1GJ4IN  |           | https://offers.qa.pch.com/survey | 0,empty                    |             0 |                   0  |                   50 |           0 | empty       | 0,empty            |             0 | purespectrumfusion |	hybrid     					|

    @Sanity
    Examples: 
      | userType |
      | full reg |

    #Examples: 
      #| userType |
      #| guest    |
      #| mini reg |
      #| silver   |
      
 