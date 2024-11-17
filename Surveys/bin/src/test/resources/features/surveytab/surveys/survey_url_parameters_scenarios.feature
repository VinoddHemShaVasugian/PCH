Feature: Verify correct parameters are being passed in survey urls
  In order to verify survey paramaters
  user needs to land on all surveys that do not override defaults

  @Desktop @Mobile
  Scenario: 1 Verify the age, gender, zip are passed in url for DNS 0 user
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I answer all basic profile questions
    And I navigates to survey 'lucid'
    Then I verify query string parameters are passed in url '&42=43,&43=1,&45=11753'

  @Desktop @Mobile
  Scenario: 2 Verify the age, gender, zip are not passed in url for DNS 1 user
    When I land on the Surveys Page as a existing user with email "fullreguserdns@pchmail.com" and gmt "795e917f-87d0-49b3-b576-c6b7260fb4a9"
    And I close the Take a Tour popup
    And I click first Take Survey Button
    And I navigates to survey 'lucid'
    Then I verify query string parameters are not passed in url '&42,&43,&45'

  @Desktop @Mobile
  Scenario: 3 Verify the correct loi and cpi values are passed in Lucid survey url
    Given I land on the Surveys Page as a recognized full reg user
    And I navigates to survey 'lucidSkipBasic'
    Then I verify query string parameters are passed in url '&loi=0,&cos=0.1'


  @Desktop @Mobile
  Scenario: 4 Verify the correct default loi and cpi values are passed in Lucid survey url
    Given I land on the Surveys Page as a recognized full reg user
    And I navigates to survey 'luciddefault'
    And I answer all basic profile questions
    And I navigates to survey 'luciddefault'
    Then I verify query string parameters are passed in url '&loi=15,&cos=0.05'

  @Desktop @Mobile
  Scenario: 5 Verify the correct default loi and cpi values are passed in LucidApi survey url
    Given I land on the Surveys Page as a recognized full reg user
    And I navigates to survey 'lucidApiDefault'
    And I answer all basic profile questions
    And I navigates to survey 'lucidApiDefault'
    Then I verify query string parameters are passed in url '&loi=1,&cos=0.05'

