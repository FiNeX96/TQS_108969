Feature: Blaze Demo Website

Scenario: Purchase flight tickets
When i open the website "http://blazedemo.com"
And i select "Paris" as departure city
And i select "London" as destination city
And i click on Find Flights button
And i select the first flight
And i enter "John" as first name
And i click on Purchase Flight button
Then i should see the message "Thank you for your purchase today!"


