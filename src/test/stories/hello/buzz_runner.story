
Narrative:
In order to expand the expressiveness of the testing suite
As a development team
I want to use Behaviour-Driven Development for the web interface

Scenario: User loads Buzz page
When the Buzz page is loaded
Then the page title should be CI/CD buzz generator

Scenario: User loads Buzz page
Given the Buzz page is loaded
When the Buzz page is loaded
Then the page text should change
