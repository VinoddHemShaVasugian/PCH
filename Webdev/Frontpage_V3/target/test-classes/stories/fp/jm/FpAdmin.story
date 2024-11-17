Narrative:
In order to verify the Frontpage functionality
As a user
I want to modify/retrieve the value from the Frontpage joomla admin application

Meta:
@TestingType Sanity Regression

Scenario: Retrive the values from config-sitepagesearch article
Meta: @id Config_SitePageSearch
Given Login to the Frontpage Admin
And Do a search for an article 'config-sitepagesearch'
And Get the field value by its key name 'searchbox_hiddenInputs.nfsp'

Scenario: Retrive the values from Config-infospace article
Meta: @id Config_Infospace
Given Login to the Frontpage Admin
And Do a search for an article 'Config-infospace'
And Get the field value by its key name 'segments'
And Get the field value by its key name 'nfspAccessIds'
And Get the field value by its key name 'algo_only_segment'

Scenario: Retrive the values from Config-riskmanagement article
Meta: @id Config_RiskManagement
Given Login to the Frontpage Admin
And Do a search for an article 'Config-riskmanagement'
And Get the field value by its key name 'max_searches_before_login'
And Get the field value by its key name 'max_searches_per_day'
And Get the field value by its key name 'search_warnings_first'
And Get the field value by its key name 'search_warnings_later'
And Get the field value by its key name 'additional_search_warnings'
And Get the field value by its key name 'duplicate_search_max_rate'
And Get the field value by its key name 'max_chances_per_day'
And Get the field value by its key name 'algo_only_searches_recognized'
And Get the field value by its key name 'algo_only_searches_unrecognized'

Scenario: Retrive the values from Scratchpath Redirect article
Meta: @id Scratchcard
Given Login to the Frontpage Admin
And Do a search for an article 'Tokens / Bonus Scratchcard Completion'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive the values from Contest Key article
Meta: @id ContestKeys
Given Login to the Frontpage Admin
And Get the field value by its label name based on article 'Contest Keys / Frontpage / Registration','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Frontpage / FirstSearch','Contest Key'

Scenario: Retrive and modify the values from config-contest-entry-api-client article
Meta: @id ContestEntryAPI
Given Login to the Frontpage Admin
And Do a search for an article 'config-contest-entry-api-client'
And Get the field value by its key name 'contestEntryApiUri'
And Modify the field by its key name 'contestEntryApiUri' with the given value 'http://cs.{env}.pch.c/EntryApi'
And User redirect to the Frontpage application 'ClearCache'

Scenario: Retrive the values from Consecutive search Contest Key articles
Meta: @id CSContestKeys
Given Login to the Frontpage Admin
And Get the field value by its label name based on article 'Contest Keys / Search / FirstSearch','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / Second Search','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / Third Search','Contest Key'

Scenario: Retrive the values from Consecutive Visit Contest Key articles
Meta: @id CVContestKeys
Given Login to the Frontpage Admin
And Get the field value by its label name based on article 'Contest Keys / Search / Consecutive Search 1','Contest Key'

Scenario: Retrive the values from Tokens / First Search article
Meta: @id TokensFirstSearch
Given Login to the Frontpage Admin
And Do a search for an article 'Tokens / First Search'
And Get the field value by its label name 'Alternative Description'
And Get the field value by its label name 'Alternative Notice'
And Get the field value by its label name 'Tokens'
And Get the dropdown field value by its label name and position 'Segments Included','2'

Scenario: Retrive the values from Tokens / LinkPromotion article
Meta: @id TokensLinkPromotion
Given Login to the Frontpage Admin
And Do a search for an article 'Tokens / Link Promotion / SegId 111814-021815'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive the values from Tokens / Extension article
Meta: @id ExtensionTokens
Given Login to the Frontpage Admin
And Do a search for an article 'Tokens / Extension'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive the values from Config_search article
Meta: @id ConfigSearch
Given Login to the Frontpage Admin
And Do a search for an article 'Config-search'
And Get the field value by its key name 'extension_download_url'

Scenario: Retrive the values from SERP Message articles for recognized user
Meta: @id SerpMessage
Given Login to the Frontpage Admin
And Do a search for an article 'Frontpage Messaging'
And Get the field value by its label name with position 'Desktop - First Search', '1'
And Get the field value by its label name with position 'Desktop - Later Search', '1'
And Get the field value by its label name with position 'Desktop - First Search', '2'
And Get the field value by its label name with position 'Desktop - Later Search', '2'

Scenario: Modify the tokenbank api value from Config-Prizemachine article
Meta: @id ModifyTokenBankAPI
Given User redirect to the Frontpage application 'ProcessTokens'
And User redirect to the Frontpage application 'ProcessRetryTokens'
Given Login to the Frontpage Admin
And Do a search for an article 'Config-Prizemachine'
And Get the field value by its key name 'tokenBankApiBaseUri'
And Modify the field by its key name 'tokenBankApiBaseUri' with the given value 'testurl'
And User redirect to the Frontpage application 'ClearCache'

Scenario: Retrive the values from Tokens / Video Claim Tokens article
Meta: @id VideoClaimTokens
Given Login to the Frontpage Admin
And Do a search for an article 'Tokens / Video Claim Tokens'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive the values from Tokens / Story Claim Tokens article
Meta: @id StoryClaimTokens
Given Login to the Frontpage Admin
And Do a search for an article 'Tokens / Story Claim Tokens'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive the sweepstakes values for Homepage 
Meta: @id SweepsHome
Given Login to the Frontpage Admin
And Get the field value by its label name based on article 'Sweepstakes edl home 1','Description'
And Get the field value by its label name based on article 'Sweepstakes edl home 1','Sweepstakes Path'
And Get the field value by its label name based on article 'Sweepstakes edl home 1','Sweepstakes Path Return Value'
And Get the field value by its label name based on article 'Sweepstakes edl home Completed','Description'

Scenario: Retrive the values from Tokens / Weather article
Meta: @id WeatherClaimTokens
Given Login to the Frontpage Admin
And Do a search for an article 'Tokens / Weather'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive the values from Tokens / Lottery article
Meta: @id LotteryClaimTokens
Given Login to the Frontpage Admin
And Do a search for an article 'Tokens / Lottery'
And Retrieve the description,notice,tokens,condition info from the article '1'