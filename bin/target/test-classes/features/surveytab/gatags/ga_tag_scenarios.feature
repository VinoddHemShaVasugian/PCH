Feature: Verify ga tags aer firing as expected accross all devices
  In order to verify qa tags events are being fired
  user needs to land on the surveys page and start interacting
  
 
  @Desktop  @GaTags
  Scenario: Recognized full reg user starts a new Lucid survey veriy survey start record in log file
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    When I click the Yes Im In button in survey handraiser section
		Then Verify All GA Tags
		|EventCategory						|EventAction|EventLabel				|
		|lucid/survey_tab/desktop	|click	   	|yes_handraiser		|

		
	@Mobile  @GaTags
  Scenario: Recognized full reg user starts a new Lucid survey veriy survey start record in log file
    Given I land on the Surveys Page as a recognized full reg user
    And I close the Take a Tour popup
    When I click the Yes Im In button in survey handraiser section
		Then Verify All GA Tags
		|EventCategory						|EventAction|EventLabel				|
		|lucid/survey_tab/mobile	|click	   	|yes_handraiser		|
		