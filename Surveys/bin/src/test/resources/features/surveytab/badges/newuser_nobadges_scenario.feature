Feature: Verify default badge section when a new user lands on the surveys tab
  In order to verify default badge
  as a new user I need to land on the surveys badge
  
  
  @Desktop @Mobile
  Scenario: Verify default badge settings for a guest user
    Given I land on the Surveys Page as a guest user
    Then I verify surveys completed count is "0"
    And I verify badges received count is "0"
    And I verify badge header text is "Collect your 1st badge"
    And I verify badge title text is "Influencer"
    And I verify badge footer text is "PCHGuest, collect your first badge by taking surveys today!"