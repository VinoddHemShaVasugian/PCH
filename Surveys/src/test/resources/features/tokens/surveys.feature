#Author: vsankar@pch.com
@Desktop @Mobile @Regression
Feature: Verify awarded token amount as per admin config
  In order to verify awarded tokens for survey complete or incomplete
  user needs to complete or incomplete a survey and navigate to token activity page.

  Scenario Outline: 1 "<userType>" user starts and completes a survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'lucidMid'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I click on My Account
    Then I verify the awarded token of 'lucidSurveyDesktopCompleteToken' with description 'lucidSurveyCompleteDescription' for survey activity '1'
    And Verify contest entry 'lucidSurveyDesktopCompleteContestkey'

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: 2 "<userType>" user starts and incompletes a survey
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'lucidMid'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    And I click on My Account
    Then I verify the awarded token of 'lucidSurveyDesktopIncompleteToken' with description 'lucidSurveyIncompleteDescription' for survey activity '1'
    And Verify contest entry 'lucidSurveyMobileIncompleteContestkey'

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |
