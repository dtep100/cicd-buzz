package hello;

import org.jbehave.core.annotations.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BuzzSteps {

    private int port;

    private RemoteWebDriver driver;
    private boolean remoteTesting;

    String lastPageText = "";

    public BuzzSteps(int servicePort)
    {
        port = servicePort;
    }

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
            remoteTesting = true;
            URL executorUrl;
            {
                String userName = System.getenv("SAUCE_USERNAME");
                String accessKey = System.getenv("SAUCE_ACCESS_KEY");
                String hubUrl = String.format("http://%s:%s@localhost:4445/wd/hub", userName, accessKey);
                executorUrl = new URL(hubUrl);
            }

            DesiredCapabilities capabilities;
            {
                capabilities = DesiredCapabilities.safari();

                capabilities.setCapability("platform", "Mac OS X 10.13");
                capabilities.setCapability("browserName", "Safari");
                capabilities.setCapability("version", "11.1");

                String buildNumber = System.getenv("TRAVIS_BUILD_NUMBER");
                capabilities.setCapability("build", buildNumber);

                capabilities.setCapability("name", this.getClass().getSimpleName());

                String tunnelId = System.getenv("TRAVIS_JOB_NUMBER");
                capabilities.setCapability("tunnel-identifier", tunnelId);
            }

            driver = new RemoteWebDriver(executorUrl, capabilities);
        }
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
