#Author: vsankar@pch.com
@Desktop @Mobile @Regression @RT-07996
Feature: Verify survey rewards based on basic profile completion.
  In order to verify survey rewards
  as a user I need to answer basic profile questions

  Scenario Outline: 1 Verify the survey rewards for "<userType>" user, when answer all the questions in basic profile.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I switch to the Surveys Page
    Then I verify surveys completed count is "1"
    And I verify the Bonus game locked state
    When I navigates to Badges page
    Then I verify "Influencer" badge is locked

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |
      | silver   |

  Scenario Outline: 2 Verify the survey rewards for "<userType>" user, when answer non-sensitive questions in basic profile.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer only non-sensitive questions as a "<userType>" user
    And I switch to the Surveys Page
    Then I verify surveys completed count is "1"
    And I verify the Bonus game locked state
    When I navigates to Badges page
    Then I verify "Influencer" badge is locked

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |
      | silver   |

  Scenario Outline: 3 Verify the survey rewards for "<userType>" user, when complete 2 surveys and answer all the questions in basic profile.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify "Influencer" user level on homepage
    And I verify surveys completed count is "3"
    And I verify the Bonus game unlocked state
    When I navigates to Badges page
    Then I verify "Influencer" badge is unlocked

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |
      | silver   |

  Scenario Outline: 4 Verify the survey rewards for "<userType>" user, when answer non-sensitive and sensitive questions subsequently in basic profile.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer only non-sensitive questions as a "<userType>" user
    And I switch to the Surveys Page
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I switch to the Surveys Page
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I switch to the Surveys Page
    Then I verify surveys completed count is "1"
    And I verify the Bonus game locked state
    When I navigates to Badges page
    Then I verify "Influencer" badge is locked

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |
      | silver   |

  Scenario Outline: 5 Verify the survey rewards for "<userType>" user, when complete 2 surveys, answer non-sensitive and sensitive questions subsequently in basic profile.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer only non-sensitive questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I switch to the Surveys Page
    And I navigates to survey 'Auto_Lucid_Tokens'
    And I answer all basic profile questions as a "<userType>" user
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    Then I verify surveys completed count is "3"
    And I verify "Influencer" user level on homepage
    And I verify the Bonus game unlocked state
    When I navigates to Badges page
    Then I verify "Influencer" badge is unlocked

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | mini reg |
      | silver   |
