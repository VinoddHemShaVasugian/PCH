Feature: Verify correct parameters are being passed in survey urls
  In order to verify survey paramaters
  user needs to land on all surveys that do not override defaults

  
   #Scenario: 5 Verify the correct default loi and cpi values are passed in LucidApi survey url
    #Given I land on the Surveys Page as a recognized full reg user
    #And I navigates to survey 'luciddefault'
    #And I answer all basic profile questions
    #When I start chrome network session capture
    #And I navigates to survey 'luciddefault'
    #And I stop chrome network session capture
    #Then I verify query string parameters are passed in url '&loi=15,&cos=0.05'
#
#
#
   Scenario: 5 Verify the correct default loi and cpi values are passed in LucidApi survey url
    Given I land on the Surveys Page as a recognized full reg user
    And I navigates to survey 'lucidApiDefault'
    When I start chrome network session capture
    And I answer all basic profile questions
    And I navigates to survey 'lucidApiDefault'
    And I stop chrome network session capture
    Then I verify query string parameters are passed in url '&loi=15,&cos=0.05'
#
#
#
#
#
#
  #Scenario: Recognized full reg user starts and completes a Lucid survey
    #Given I land on the Surveys Page as a recognized full reg user
    #And I close the Take a Tour popup
    #And I select a survey 'lucid'
    #And I answer all basic profile questions
    #And I navigates to survey 'lucid'
    #And I generate the survey endpoint url with 'complete' status
    #And I call the survey endpoint url
    #Then I verify survey end json is recorded in 'Lucid' EventsLogger
    #And I verify 'Lucid' survey end event json contains the following elements
      #| Element | Value    |
      #| Status  | complete |
#
#




 
   #
   #Scenario: 5 Verify the correct default loi and cpi values are passed in LucidApi survey url
    #Given I land on the Surveys Page as a recognized full reg user
    #And I navigates to survey 'qmeedefault'
    #And I close the Take a Tour popup
        #And I answer all basic profile questions
    #When I start chrome network session capture
    #And I navigates to survey 'qmeedefault'
    #And I stop chrome network session capture
    #Then I verify query string parameters are passed in url '&loi=15,&cos=0.05'
#


   #Scenario: 5 Verify the correct default loi and cpi values are passed in LucidApi survey url
    #Given I land on the Surveys Page as a recognized full reg user
    #And I navigates to survey 'psdefault'
    #And I close the Take a Tour popup
        #And I answer all basic profile questions
    #When I start chrome network session capture
    #And I navigates to survey 'psdefault'
    #And I stop chrome network session capture
    #Then I verify query string parameters are passed in url '&loi=15,&cos=0.05'
 
 
    #Scenario: 5 Verify the correct default loi and cpi values are passed in LucidApi survey url
    #Given I land on the Surveys Page as a recognized full reg user
    #And I navigates to survey 'PSplacement'
    #And I close the Take a Tour popup
    #When I start chrome network session capture
    #And I answer all basic profile questions
    #And I navigates to survey 'PSplacement'
    #And I stop chrome network session capture
    #Then I verify query string parameters are passed in url '&loi=15,&cos=0.05'
 #
 #
  
 
 