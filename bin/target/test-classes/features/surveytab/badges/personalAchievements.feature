Feature: Validating Personal Achievements Page

@Desktop @Mobile
  Scenario: Verify Back to Surveys link
    Given I land on badges page as a guest user
    When I click on Back to Surveys link
    Then I should be redirected to the Surveys Tab

@Desktop @Mobile
  Scenario: Verify Program Terms link
    Given I land on badges page as a guest user
    When I click on Program Terms link
    Then I should be redirected to the Program Terms page in new tab