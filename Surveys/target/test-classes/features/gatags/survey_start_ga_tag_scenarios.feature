#Author: bmacaluso@pch.com
Feature: Verify GA3 and GA4 events are being fired for survey start and complete events accross different users and devices
   In order to verify qa tags events are being fired
  user needs to land on the surveys page and start interacting



  @Desktop @RT-07645 @GaTags @Sanity
  Scenario Outline: 1a Verify GA3 and GA4 tags are being logged for survey start event for "<userType>" user on desktop
   When I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'desktop'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'desktop'

   Examples: 
      | userType |
      | full reg |
      | silver   |
      | social   |
			|	mini		 |




  @Mobile @RT-07645 @GaTags @Sanity
  Scenario Outline: 2a Verify GA3 and GA4 tags are being logged for survey start event for full reg user on mobile
    When I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'mobile'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'mobile'

Examples: 
      | userType |
      | full reg |
      | silver   |
      | social   |
			|	mini		 |
