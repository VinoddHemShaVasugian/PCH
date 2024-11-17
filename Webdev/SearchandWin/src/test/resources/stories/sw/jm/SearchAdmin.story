Narrative:
In order to verify the SW functionality
As a user
I want to modify/retrieve the value from the Search joomla admin application

Meta:
@TestingType Sanity Regression

Scenario: Retrive the values from config-sitepagesearch article
Meta: @id Config_SitePageSearch
Given Login to the Search Admin
And Do a search for an article 'config-sitepagesearch'
And Get the field value by its key name 'searchbox_hiddenInputs.nfsp'

Scenario: Retrive the values from Config-infospace article
Meta: @id Config_Infospace
Given Login to the Search Admin
And Do a search for an article 'Config-infospace'
And Get the field value by its key name 'segments'
And Get the field value by its key name 'nfspAccessIds'
And Get the field value by its key name 'algo_only_segment'

Scenario: Retrive the values from Config-riskmanagement article
Meta: @id Config_RiskManagement
Given Login to the Search Admin
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
Meta: @id Scratchpath Redirect
Given Login to the Search Admin
And Do a search for an article 'Scratchpath Redirect'
And Get the field value by its label name 'Target URL'
And Get the field value by its label name 'Redirections Count'
And Get the field value by its label name 'Conditions'
And Get the dropdown field value by its label name 'Segments Included'
And Get the dropdown field value by its label name 'Segments Excluded'

Scenario: Retrive the values from Contest Key article
Meta: @id ContestKeys
Given Login to the Search Admin
And Get the field value by its label name based on article 'Contest Keys / Search / Registration','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / FirstSearch','Contest Key'

Scenario: Retrive and modify the values from config-contest-entry-api-client article
Meta: @id ContestEntryAPI
Given Login to the Search Admin
And Do a search for an article 'config-contest-entry-api-client'
And Get the field value by its key name 'contestEntryApiUri'
And Modify the field by its key name 'contestEntryApiUri' with the given value 'http://centralservices.{env}/'
And User redirect to the Search application 'ClearCache'

Scenario: Retrive the values from Consecutive search Contest Key articles
Meta: @id CSContestKeys
Given Login to the Search Admin
And Get the field value by its label name based on article 'Contest Keys / Search / FirstSearch','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / Second Search','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / Third Search','Contest Key'

Scenario: Retrive the values from Consecutive Visit Contest Key articles
Meta: @id CVContestKeys
Given Login to the Search Admin
And Get the field value by its label name based on article 'Contest Keys / Search / Consecutive Search 1','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / Consecutive Search 2','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / Consecutive Search 3','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / Consecutive Search 4','Contest Key'
And Get the field value by its label name based on article 'Contest Keys / Search / Consecutive Search 5','Contest Key'

Scenario: Retrive the token desciption and amount from Prize Machine / Tokens / Consecutive Visit article
Meta: @id CVTokens
Given Login to the Search Admin
And Do a search for an article 'Prize Machine / Tokens / Consecutive Visit'
And Get the field value by its label name with position 'Tokens', '1'
And Get the field value by its label name with position 'Alternative Description', '1'

Scenario: Retrive the values from Tokens / First Search article
Meta: @id TokensFirstSearch
Given Login to the Search Admin
And Do a search for an article 'Tokens / First Search'
And Get the field value by its label name 'Alternative Description'
And Get the field value by its label name 'Alternative Notice'
And Get the field value by its label name 'Tokens'
And Get the dropdown field value by its label name with position 'Segments Included','2'

Scenario: Retrive the values from Tokens / LinkPromotion article
Meta: @id TokensLinkPromotion
Given Login to the Search Admin
And Do a search for an article 'Tokens / LinkPromotion'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive the values from Tokens / Extension article
Meta: @id ExtensionTokens
Given Login to the Search Admin
And Do a search for an article 'Tokens / Extension'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive the values from Config_search article
Meta: @id ConfigSearch
Given Login to the Search Admin
And Do a search for an article 'Config-search'
And Get the field value by its key name 'extension_download_url'

Scenario: Retrive the values from SERP Message articles for recognized user
Meta: @id SerpMessage
Given Login to the Search Admin
And Do a search for an article 'SERP Message'
And Get the field value by its label name with position 'Desktop - First Search', '1'
And Get the field value by its label name with position 'Desktop - Later Search', '1'
And Get the field value by its label name with position 'Desktop - First Search', '2'
And Get the field value by its label name with position 'Desktop - Later Search', '2'

Scenario: Modify the tokenbank api value from Config-Prizemachine article
Meta: @id ModifyTokenBankAPI
Given User redirect to the Search application 'ProcessTokens'
And User redirect to the Search application 'ProcessRetryTokens'
And Login to the Search Admin
And Do a search for an article 'Config-Prizemachine'
And Get the field value by its key name 'tokenBankApiBaseUri'
And Modify the field by its key name 'tokenBankApiBaseUri' with the given value 'testurl'
And User redirect to the Search application 'ClearCache'

Scenario: Enable the PCH Ad Martket Place tiles on config-sitepagesearch7 article
Meta: @id EnablePCHAdMarketPlace
Given Login to the Search Admin
And Do a search for an article 'config-sitepagesearch7'
And Modify the field by its key name 'admarketplace' with the given value 'true'
And Modify the field by its key name 'pch_admarketplace' with the given value 'false'
And User redirect to the Search application 'ClearCache'

Scenario: Enable the PCH Ad Martket Place tiles on config-sitepagesearch7 article
Meta: @id AdMarketPlaceClickLimit
Given Login to the Search Admin
And Do a search for an article 'config-admarketplace'
And Modify the field by its key name 'clickLimit' with the given value '3'
And Get the field value by its key name 'clickLimit'
And User redirect to the Search application 'ClearCache'

Scenario: Enable the PCH Internal AdTiles on config-sitepagesearch7 article
Meta: @id EnablePCHInternalTiles
Given Login to the Search Admin
And Do a search for an article 'config-sitepagesearch7'
And Modify the field by its key name 'admarketplace' with the given value 'false'
And Modify the field by its key name 'pch_admarketplace' with the given value 'true'
And User redirect to the Search application 'ClearCache'