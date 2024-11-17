Feature: Verify new category labels are available in both feature and main survey sections
  In order to verify new category labels on Survey page
  user needs to land on the surveys page and count new labels

  @RT-07061 @RT-07060 @Desktop @Mobile
  Scenario: 1 Verify new category label available in both feature and main survey sections for full reg user
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    Then I verify that new category surveys are available in featured section
    And I verify that all category surveys not marked with new label in featured section
    Then I verify that new category surveys are available in main survey section
    And I verify that all category surveys not marked with new label in main survey section

  @RT-07061 @RT-07060 @Desktop @Mobile
  Scenario: 2 Verify new category label available in both feature and main survey sections for silver user
    When I land on the Surveys Page as a recognized silver user
    And I close the Take a Tour popup
    Then I verify that new category surveys are available in featured section
    And I verify that all category surveys not marked with new label in featured section
    Then I verify that new category surveys are available in main survey section
    And I verify that all category surveys not marked with new label in main survey section

  @RT-07061 @RT-07060 @Desktop @Mobile
  Scenario: 3 Verify new category label available in both feature and main survey sections for mini reg user
    When I land on the Surveys Page as a recognized mini reg user
    And I close the Take a Tour popup
    Then I verify that new category surveys are available in featured section
    And I verify that all category surveys not marked with new label in featured section
    Then I verify that new category surveys are available in main survey section
    And I verify that all category surveys not marked with new label in main survey section

  @RT-07061 @RT-07060 @Desktop @Mobile
  Scenario: 4 Verify new category label available in both feature and main survey sections for social reg user
    When I land on the Surveys Page as a recognized social user
    And I close the Take a Tour popup
    Then I verify that new category surveys are available in featured section
    And I verify that all category surveys not marked with new label in featured section
    Then I verify that new category surveys are available in main survey section
    And I verify that all category surveys not marked with new label in main survey section

