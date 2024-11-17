Narrative:
In order to verify the Quiz functionality
As a user
I want to modify/retrieve the value from the Quiz joomla admin application

Meta:
@TestingType Sanity Regression

Scenario: Retrive the values from Contest Key article
Meta: @id ContestKeys
Given Login to the Quiz Admin
And Get the field value by its label name based on article 'Contest Keys / QUIZ / Complete','Contest Key'

Scenario: Retrive and modify the values from config-contest-entry-api-client article
Meta: @id ContestEntryAPI
Given Login to the Quiz Admin
And Do a search for an article 'config-contest-entry-api-client'
And Get the field value by its key name 'contestEntryApiUri'
And Modify the field by its key name 'contestEntryApiUri' with the given value 'http://cs.{env}.pch.c/EntryApi'
And User redirect to the quiz application 'ConfigurationClearCache'

Scenario: Retrive the values from Prize Machine / Tokens / Registration article
Meta: @id RegistrationTokens
Given Login to the Quiz Admin
And Do a search for an article 'Prize Machine / Tokens / Registration'
And Retrieve the description,notice,tokens,condition info from the article '1'

Scenario: Retrive values from Joomla New Promotion article and respective contest keys
Meta: @id NewPromotionValues
Given Login to the Quiz Admin
Then Do a search for an article 'Persistent Banner -Default'
And Get the field value by its val name 'acquisition_key'
And Get the field value by its val name 'exit_url'
And Get the field value by its val name 'desktop_complete_path_ck'
And Get the field value by its val name 'desktop_halfway_path_ck'