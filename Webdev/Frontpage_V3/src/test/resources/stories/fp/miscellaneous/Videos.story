Narrative:
In order to verify the Video details logs in database
As a user 
I want to watch the video from Frontpage site and verify the video details on video log table

Meta:
@TestingType Regression
GivenStories: stories/fp/jm/FpAdmin.story#{id:VideoClaimTokens}

Scenario: Verify video page for guest user
Given Go to Frontpage site
When Verify the video landing page
Then Verify video landing page content
And Verify video player for guest user

Scenario: Verify the video page redirection and claimed tokens by playing video.
Meta:
@TestingType Sanity
Given Register a fully registered user through RF and login to the site
When Verify the video landing page
Then Play video and verify claimed tokens
And Verify video landing page content
And Navigate to Token History Page
And Verify token transaction details based on admin configured article 'tokens','description','1'
And User redirect to the Frontpage application
And Verify the video playlist and video on category pages
And User redirect to the Frontpage application
And Verify the video playlist and video on sub category pages

Scenario: Verify the video details on video_log table for Full Reg user
Given Register a fully registered user through RF and login to the site
When Click video from Top stories
Then Verify the Video log details for featured article and claimed status '1'
And User redirect to the Frontpage application
And Verify the Video log details for category page video
And Verify the Video log details for sub category page video

Scenario: Verify the video details on video_log table for Mini reg user type
Given Register a mini registered user through RF and login to the site
When Click video from Top stories
And Verify complete registration button on the uninav
Then Verify complete registration message on Video page
And Verify the Video log details for featured article and claimed status '0'
And Do complete registration to become full reg user from Mini Reg user
And Verify user successfully landed on homepage
And Verify the Video log details for featured article and claimed status '1'

Scenario: Verify the video details on video_log table for Social user type
Given Register a social user through RF and login to the site
When Click video from Top stories
And Verify complete registration button on the uninav
Then Verify complete registration message on Video page
And Verify the Video log details for featured article and claimed status '0'
And Complete registration to become full reg user from social reg user
And Verify user successfully landed on homepage
And Verify the Video log details for featured article and claimed status '1'

Scenario: Verify the video details on video_log table for Silver user type
Given Register a silver user through RF and login to the site
When Click video from Top stories
And Verify complete registration button on the uninav
Then Verify complete registration message on Video page
And Verify the Video log details for featured article and claimed status '0'
And Do complete registration to become fully registered user from Silver User
And Verify user successfully landed on homepage
And Verify the Video log details for featured article and claimed status '1'