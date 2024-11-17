Narrative:
In order to verify ads in quiz tab
As a Tester
I want to login to Quiz tab and verify

Meta:
@TestingType Regression

Scenario: Verify GPT ads on Homepage
Given Create '<userType>' user and login to quiz tab
When Verify left rail ad '160','600'
Then Verify right rail ad '300','250'
And Verify right rail bottom ad '300','250'
And Verify right rail multiple ad '300','600','300','629','300','250'
And User redirect to the current URL 'googfc'
And Verify page tagged status at adFrame '5'
Examples:
|userType|
|guest|
|fully registered|
|mini registered|
|silver|
|social|

Scenario: Verify Playbuzz ads on quiz page
Given Create '<userType>' user and login to quiz tab
When Navigate to Trending quiz page
Then Verify playbuzz right rail ad
And Answer '1' question(s)
And Verify playbuzz bottom ad
Examples:
|userType|
|guest|
|fully registered|
|mini registered|
|silver|
|social|

Scenario: Verify GPT ads on quiz page
Given Create '<userType>' user and login to quiz tab
When Navigate to Trending quiz page
Then Verify left rail ad '160','600'
And Verify right rail bottom ad '300','250'
And Verify right rail multiple ad '300','600','300','629','300','250'
And Answer '1' question(s)
And Verify inline ad '728','90'
And User redirect to the current URL 'googfc'
And Verify page tagged status at adFrame '5'
Examples:
|userType|
|guest|
|fully registered|
|mini registered|
|silver|
|social|

Scenario: Verify GPT ads on category pages
Given Create '<userType>' user and login to quiz tab
When Navigate to '<category>' Category page
Then Verify left rail ad '160','600'
And Verify right rail ad '300','250'
And Verify right rail multiple ad '300','600','300','629','300','250'
And Verify right rail bottom ad '300','250'
And Verify inline ad in category page '728','90'
And User redirect to the current URL 'googfc'
And Verify page tagged status at adFrame '8'
Examples:
|userType|category|
|guest|Trivia|
|fully registered|Travel|
|mini registered|History|
|silver|Animals|
|social|Animals|