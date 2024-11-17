Scenario: Verify Token credit queue record by making Invalid API url on admin.

Given Navigate to Joomla admin and retrieve the Contest Key details
And Create a Full Reg. user
When Verify the Token details in the queue for Registration
And Do a First Search
Then Verify the Token details in the queue for First Search
And Verify the Token status details in the queue after running the cron
And Reset the Joomla Admin values
And Re-Run the cron and verify the contest entry queue table