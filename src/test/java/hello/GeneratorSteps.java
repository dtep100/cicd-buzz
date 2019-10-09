package hello;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class GeneratorSteps {

    private Generator generator;

    @Given("a list containing $elements")
    public void aList(String elements)
    {
        System.out.println("aList: " + elements);
    }

    @When("the generator is asked for $number value(s) from the list")
    public void theGeneratorIsAskedFor(int number)
    {
        System.out.println("theGeneratorIsAskedFor: " + number);
    }

    @Then("the generator result should be $number of $elements")
    public void theGeneratorResultShouldBe(int number, String elements)
    {
        System.out.println("theGeneratorResultShouldBe: " + number + ","  + elements);
    }
}
