#Author: bmacaluso@pch.com
Feature: Verify GA3 and GA4 events are being fired for survey start and complete events accross different users and devices
   In order to verify qa tags events are being fired
  user needs to land on the surveys page and start interacting

  @Desktop @RT-07645
  Scenario: 1 Verify GA3 and GA4 tags are being logged for survey start event for full reg user on desktop
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'desktop'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'desktop'

  @Desktop @RT-07645
  Scenario: 2 Verify GA3 and GA4 tags are being logged for survey start event for silver reg user on desktop
    When I land on the Surveys Page as a recognized silver user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'desktop'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'desktop'

  @Desktop @RT-07645
  Scenario: 3 Verify GA3 and GA4 tags are being logged for survey start event for mini reg user on desktop
    When I land on the Surveys Page as a recognized mini reg user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'desktop'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'desktop'

  @Desktop @RT-07645
  Scenario: 4 Verify GA3 and GA4 tags are being logged for survey start event for social reg user on desktop
    When I land on the Surveys Page as a recognized social user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'desktop'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'desktop'

  @Mobile @RT-07645
  Scenario: Verify GA3 and GA4 tags are being logged for survey start event for full reg user on mobile
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'mobile'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'mobile'

  @Mobile @RT-07645
  Scenario Outline: Verify GA3 and GA4 tags are being logged for survey start event for silver reg user on mobile
    When I land on the Surveys Page as a recognized silver user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'mobile'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'mobile'

  @Mobile @RT-07645
  Scenario: Verify GA3 and GA4 tags are being logged for survey start event for mini reg user on mobile
    When I land on the Surveys Page as a recognized mini reg user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'mobile'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'mobile'

  @Mobile @RT-07645
  Scenario: Verify GA3 and GA4 tags are being logged for survey start event for social reg user on mobile
    When I land on the Surveys Page as a recognized social user
    And I close the Take a Tour popup
    And I select a survey 'lucidSkipBasic'
    And I navigates to survey 'lucidSkipBasic'
    Then I verify 'surveystart' event GA3 tags are being recorded in GoogleAnalyticsLogger logfile for supplier 'lucid' and device 'mobile'
    Then I verify 'surveystart' event GA4 tags are being recorded in Ga4 logfile for supplier 'lucid' and device 'mobile'
