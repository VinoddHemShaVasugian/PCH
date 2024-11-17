Narrative:
In order to Verify sweepstakes
As a Tester
I want to Configure and retrive sweepstakes article details from admin.

Meta: 
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:SweepsHome}

Scenario: Play and verify sweepstakes on Homepage.
Meta: 
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When User redirect to EDL application
Then Verify the application redirected to EDL home page
And Verify the sweepstakes 'Sweepstakes edl home 1','Description'
And Play sweeps and verify the sweepstakes path 'Sweepstakes edl home 1','Sweepstakes Path'
And Launch sweeps path return URL and verify sweeps activity 'Sweepstakes edl home 1','Sweepstakes Path Return Value'
And Verify sweeps complete status and success message 'Sweepstakes edl home Completed','Description'
And Verify sweeps property on db 'Sweepstakes edl home 1','Sweepstakes Path Return Value'