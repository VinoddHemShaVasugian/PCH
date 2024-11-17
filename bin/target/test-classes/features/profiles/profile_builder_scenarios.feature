Feature: Build a new user profile
  In order to build a profile
  as a user I need to first sign in or create new registration
  and answer all presented questions


@Desktop
	Scenario: Verify profile builder page title, top section and exit button
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    When I click Profile Builder lets go button
    Then I verify updated title is "My PCH Profile"
    And I verify % complete progress bar under the top bar
    And I verify the text copy next to % complete is "Get matched with the best opportunities for you by completing your profile! It's quick, easy and you'll get Tokens for each question you answer. Choose any topic or answer the question below to get started now. Privacy Policy"
    And I verify privacy policy link in the text copy
    And I verify exit button in the bottom of the page

@Desktop @Mobile
  Scenario: User answers 10 profile builder questions and is awarded profile genius badge
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    When I click Profile Builder lets go button
    And I select a profile category 'home'
    And I answer 10 profile builder questions
    And I click Exit button on question page
    Then I verify the number of questions answered is recorded in Redis DB    
    And I click Exit button
    When I pause execution for 2 seconds
    Then  I verify badges received count is "1"
    And I verify badge header text is "Recently Received"
    And I verify badge title text is "Profile Genius"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Profile Genius Badge! Now go for more!"


@Desktop @Mobile @xxxxx
  Scenario: User answers all profile builder questions verify question count is correct in Redis DB
    When I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    Then I verify profile builder status message is 'Tell us more about you!' and % complete is "0%"
    When I click Profile Builder lets go button
    And I agree to the terms of the Data Collection Consent
    And I select a profile category 'home'
    And I answer all profile builder questions
    And I click Exit button
    When I pause execution for 10 seconds
    Then I verify profile builder status message is 'Your profile is complete.' and % complete is "100%"
    And I verify the number of questions answered is recorded in Redis DB    
    And I verify badges received count is "3"
    And I verify badge header text is "Recently Received"
    And I verify badge title text is "Token Titan Gold"
    And I verify badge footer text is "<FIRSTNAME>, nice job getting your Token Titan Gold Badge! Now go for more!"

 
@Desktop @Mobile @Defect-D-14020
  Scenario: Social user answers profile question and is prompted for password verify default name
  Given I land on the Surveys Page as a recognized social user
  And I close the Take a Tour popup
  And I click Profile Builder lets go button
  And I select a profile category 'home'
  And I answer question 'What best describes your residence?' with 'Rented apartment'
  And I click the save button
  Then I verify user name on create password page is 'PCH User,'