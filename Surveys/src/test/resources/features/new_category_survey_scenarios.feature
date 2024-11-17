#Author: bmacaluso@pch.com
Feature: Verify new category labels are available in both feature and main survey sections
  In order to verify new category labels on Survey page
  user needs to land on the surveys page and count new labels

  @RT-07061 @RT-07060 @Desktop @Mobile
  Scenario: 1 Verify new category label available in both feature and main survey sections for full reg user
    When I land on the Surveys Page as a existing user with email "fullreguser@pchmail.com" and gmt "36fee6ac-0c25-4dd6-9b16-91c7935be29b"
    And I close the Take a Tour popup
    Then I verify that new category surveys are available in featured section
    And I verify that all category surveys not marked with new label in featured section
    Then I verify that new category surveys are available in main survey section
    And I verify that all category surveys not marked with new label in main survey section

  @RT-07061 @RT-07060 @Desktop @Mobile
  Scenario: 2 Verify new category label available in both feature and main survey sections for silver user
    When I land on the Surveys Page as a existing user with email "silverreguser2@pchmail.com" and gmt "1c97d0f4-bb4a-4e98-9d07-4746b5646d8b"
    And I close the Take a Tour popup
    Then I verify that new category surveys are available in featured section
    And I verify that all category surveys not marked with new label in featured section
    Then I verify that new category surveys are available in main survey section
    And I verify that all category surveys not marked with new label in main survey section

  @RT-07061 @RT-07060 @Desktop @Mobile
  Scenario: 3 Verify new category label available in both feature and main survey sections for mini reg user
    When I land on the Surveys Page as a existing user with email "minireguser@pchmail.com" and gmt "fede9f9c-4b43-4d28-98b7-a33b27c75dc2"
    And I close the Take a Tour popup
    Then I verify that new category surveys are available in featured section
    And I verify that all category surveys not marked with new label in featured section
    Then I verify that new category surveys are available in main survey section
    And I verify that all category surveys not marked with new label in main survey section

  @RT-07061 @RT-07060 @Desktop @Mobile
  Scenario: 4 Verify new category label available in both feature and main survey sections for social reg user
    When I land on the Surveys Page as a existing user with email "socialreguser@pchmail.com" and gmt "dd8934b9-f1c0-4485-9d57-a397a8879eec"
    And I close the Take a Tour popup
    Then I verify that new category surveys are available in featured section
    And I verify that all category surveys not marked with new label in featured section
    Then I verify that new category surveys are available in main survey section
    And I verify that all category surveys not marked with new label in main survey section
