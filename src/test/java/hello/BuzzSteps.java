package hello;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BuzzSteps {

    //@LocalServerPort
    private int port = 5000;

    private RemoteWebDriver driver;
    private boolean remoteTesting;

    String lastPageText = "";


    @BeforeStory
    public void setup() throws Exception {

        /* Check if we are using a remote driver or a local driver */
        String envVar = System.getenv("CI");
        if(null == envVar)
        {
            envVar = "FALSE";
        }
        envVar = envVar.toUpperCase();
        remoteTesting = false;

        if(!envVar.equals("TRUE")) {
            driver = new SafariDriver();
        } else {
            assertEquals(true,false);
        }

        System.out.println("Constructor, Port = " + port);
    }

    @AfterStory
    public void teardown(){
        driver.close();
        if(true == remoteTesting) {
            driver.quit();
        }
    }

    private String getBodyText(){
        String bodyText = driver.findElementByTagName("body").getText();
        return(bodyText);
    }

    @Given("the Buzz page is loaded")
    @When("the Buzz page is loaded")
    public void theBuzzPageIsLoaded() {
        lastPageText = getBodyText();
        driver.get("http://localhost:" + port);
    }

    @Then("the page title should be $title")
    public void thenThePageTitleShouldBe(String title) {
        String pageTitle = driver.getTitle();
        assertEquals(title, pageTitle);
    }

    @Then("the page text should change")
    public void thenThePageTextShouldChange(){
        String newPageText = getBodyText();
        assertNotEquals(lastPageText, newPageText);
    }
}
