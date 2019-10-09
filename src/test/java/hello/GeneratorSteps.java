package hello;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class GeneratorSteps {

    private boolean findInList(ArrayList<String> searchList, String searchItem)
    {
        boolean foundEntry = false;
        for(String sample: searchList){
            if(searchItem.equals(sample)){
                foundEntry = true;
            }
        }
        return (foundEntry);
    }

    private Generator generator;
    private ArrayList<String> elementList;
    private ArrayList<String> responseList;

    @Given("a list containing $elements")
    public void aList(ArrayList<String> elements)
    {
        elementList = elements;
    }

    @When("the generator is asked for $number value(s) from the list")
    public void theGeneratorIsAskedFor(int number)
    {
        responseList = Generator.sample(elementList, number);
    }

    @Then("the generator result should be $number of $elements")
    public void theGeneratorResultShouldBe(int number, ArrayList<String> elements)
    {
        assertThat("Result length should match parameter", responseList.size() == number);
        for(String response: responseList)
        {
            assertThat("Result values should be from expected list", findInList(elements, response));
        }
    }
}
