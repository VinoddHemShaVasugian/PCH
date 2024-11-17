#Author: vsankar@pch.com
Feature: Verify the survey onsite messaging popup.
  In order to display the onsite message popup
  as a user I need to land on invalid/expired surveys
  and completes/incompletes a surveys to meets frequency gap.
  and meets the survey error scenarios.

  @Desktop @Mobile
  Scenario Outline: Verify the survey onsite message for "<userType>" user, when landing on invalid mid/survey.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'INVALID_MID'
    Then I verify the onsite message popup 'INVALID_MID'
    And I close survey onsite message popup
    And I verify an absence of survey onsite message popup

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |
      | silver   |
      | social   |

  @Desktop @Mobile
  Scenario Outline: Verify the survey onsite message for "<userType>" user, when landing on expired mid/survey.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'EXPIRED_MID'
    Then I verify the onsite message popup 'INVALID_MID'
    And I close survey onsite message popup
    And I verify an absence of survey onsite message popup

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |
      | silver   |
      | social   |

  @Desktop @Mobile
  Scenario Outline: Verify the take survey feature on survey onsite message for "<userType>" user.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'INVALID_MID'
    And I click ANSWER SOME QUESTIONS button
    Then I verify page title is 'Profile'

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |
      | silver   |
      | social   |

  @Desktop @Mobile
  Scenario Outline: Verify the survey onsite message for "<userType>" user, when user meets survey complete frequency cap.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'incrmonthly'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'incrmonthly'
    And I generate the survey endpoint url with 'complete' status
    And I call the survey endpoint url
    And I navigates to survey 'incrmonthly'
    Then I verify the onsite message popup 'MID_CAP_REACHED'
    And I close survey onsite message popup
    And I verify an absence of survey onsite message popup

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |

  @Desktop @Mobile
  Scenario Outline: Verify the survey onsite message for "<userType>" user, when user meets survey complete frequency cap.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'incrmonthly'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'incrmonthly'
    And I generate the survey endpoint url with 'complete' status
    And I completed the survey using survey complete url without creating password for NoPassword user
    And I navigates to survey 'incrmonthly'
    Then I verify the onsite message popup 'MID_CAP_REACHED'
    And I close survey onsite message popup
    And I verify an absence of survey onsite message popup

    Examples: 
      | userType |
      | silver   |
      | social   |

  @Desktop @Mobile
  Scenario Outline: Verify the survey onsite message for "<userType>" user, when user meets survey incomplete/DQ frequency cap.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'incrmonthly'
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    And I navigates to survey 'incrmonthly'
    Then I verify the onsite message popup 'MID_CAP_REACHED'
    And I close survey onsite message popup
    And I verify an absence of survey onsite message popup

    Examples: 
      | userType |
      | guest    |
      | full reg |
      | mini reg |

  @Desktop @Mobile
  Scenario Outline: Verify the survey onsite message for "<userType>" user, when user meets survey incomplete/DQ frequency cap.
    Given I land on the Surveys Page as a "<userType>" user
    When I close the Take a Tour popup
    And I navigates to survey 'incrmonthly'
    And I generate the survey endpoint url with 'incomplete' status
    And I call the survey endpoint url
    And I navigates to survey 'incrmonthly'
    Then I verify the onsite message popup 'MID_CAP_REACHED'
    And I close survey onsite message popup
    And I verify an absence of survey onsite message popup

    Examples: 
      | userType |
      | silver   |
      | social   |