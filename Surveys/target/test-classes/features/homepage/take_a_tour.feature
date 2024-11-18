#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression
Feature: Verify Take a tour slides on survey tab
  In order to verify take a tour popup is displayed or not displayed
  as a user I need to test with new user and existing user

  Scenario: A new full reg user lands on the survey tab and views take a tour popup slides
    When I land on the Surveys Page as a recognized full reg user
    Then I verify Take a Tour popup is 'displayed'
    And I verify Take a Tour popup message slides

  Scenario: A existing full reg user lands on the survey tab take a tour popup is not displayed
    When I land on the Surveys Page as a existing user with email "fullreguser@pchmail.com" and gmt "36fee6ac-0c25-4dd6-9b16-91c7935be29b"
    Then I verify Take a Tour popup is 'notdisplayed'

  Scenario: A existing full reg user lands on the survey tab clicks take a tour link
    When I land on the Surveys Page as a existing user with email "fullreguser@pchmail.com" and gmt "36fee6ac-0c25-4dd6-9b16-91c7935be29b"
    Then I verify Take a Tour popup is 'notdisplayed'
    When I click on the Take a Tour link
    Then I verify Take a Tour popup is 'displayed'
    And I verify Take a Tour popup message slides
