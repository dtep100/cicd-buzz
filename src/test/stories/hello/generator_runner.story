
Narrative:
In order to expand the expressiveness of the testing suite
As a development team
I want to use Behaviour-Driven Development

Scenario:  Single word random sample
Given a list containing "foo", "bar" and "foobar"
When the generator is asked for 1 value(s) from the list
Then the generator result should be 1 of "foo", "bar" or "foobar"