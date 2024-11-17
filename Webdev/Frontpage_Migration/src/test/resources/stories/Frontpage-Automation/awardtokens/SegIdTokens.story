Scenario: Verify the token scenario for Seg Id user.
Given Navigate to Joomla Admin application to getSeg Id Token value "user_name" "password"
|user_name| password  |
|vsankar  |testing1234|
When Create a Full Reg user from CS page
And Login to the application with the Seg Id and do a first search
Then Verify the Seg Id token value for the same user second time
And Update the Daily search count and verify the Seg Id tokens for the same user