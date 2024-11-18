#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression
Feature: Verify correct loi and cpi parameters are being passed in survey urls
  In order to verify survey paramaters
  user needs to land on survey and verify

  @Sanity
  Scenario Outline: 1 Verify the correct loi and cpi values are passed in Lucid survey url for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    Then I verify query string parameters are passed in url '&loi=15,&cos=.5'

    Examples: 
      | userType |
      | full reg |

  @Sanity
  Scenario Outline: 2 Verify the correct default loi and cpi values are passed in Lucid survey url for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP_Default'
    Then I verify query string parameters are passed in url '&loi=15,&cos=0.1'

    Examples: 
      | userType |
      | full reg |
