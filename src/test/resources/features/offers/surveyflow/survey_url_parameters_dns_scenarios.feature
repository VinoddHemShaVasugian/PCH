#Author: bmacaluso@pch.com
Feature: Verify age, gender, zip are being passed or not in survey urls
  In order to verify age, gender, zip passed or not passed based on DNS
  user needs to land on all surveys


  @Desktop @Mobile
  Scenario: 1 Verify the age, gender, zip are passed in url for Lucid survey for DNS 0 user
    When I land on the Surveys Page as a existing user with email "fullregbasicanswereduser@pchmail.com" and gmt "ae914cc3-e6c5-46fe-ba3c-e243dbcf541e"
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    Then I verify query string parameters are passed in url '&42=,&43=,&45='

 
  @Desktop @Mobile 
  Scenario: 2 Verify the age, gender, zip are passed in url for Lucid survey for DNS 1 user
    When I land on the Surveys Page as a existing user with email "fullreguserdns@pchmail.com" and gmt "795e917f-87d0-49b3-b576-c6b7260fb4a9"
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    Then I verify query string parameters are not passed in url '&42=,&43=,&45='
