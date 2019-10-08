package hello;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.safari.SafariDriver;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuzzControllerST {

    @LocalServerPort
    private int port;

    private RemoteWebDriver driver;

    @Before
    public void setup() throws Exception {

        /* Check if we are using a remote driver or a local driver */
        String remoteTesting = System.getProperty("CI","False").toUpperCase();

        if(!remoteTesting.equals("TRUE")) {
            driver = new SafariDriver();
        } else {
            URL executorUrl;
            {
                String userName = System.getProperty("SAUCE_USERNAME","userName");
                String accessKey = System.getProperty("SAUCE_ACCESS_KEY","accessKey");
                String hubUrl = String.format("http://%s:%s@localhost:4445/wd/hub", userName, accessKey);
                executorUrl = new URL(hubUrl);
            }

            DesiredCapabilities capabilities;
            {
                capabilities = DesiredCapabilities.safari();

                capabilities.setCapability("platform", "Mac OS X 10.13");
                capabilities.setCapability("browserName", "Safari");
                capabilities.setCapability("version", "11.1");

                String buildNumber = System.getProperty("TRAVIS_BUILD_NUMBER","buildnumber");
                capabilities.setCapability("build", buildNumber);

                capabilities.setCapability("name", this.getClass().getSimpleName());

                String tunnelId = System.getProperty("TRAVIS_JOB_NUMBER","tunnel");
                capabilities.setCapability("tunnel-identifier", tunnelId);
            }

            driver = new RemoteWebDriver(executorUrl, capabilities);
        }
    }

    @After
    public void teardown(){
        driver.close();
    }

    public void helperLoadPage()
    {
        driver.get("http://localhost:" + port);
    }

    @Test
    public void testPageTitle() throws Exception {
        /* Precondition */

        /* Action */
        helperLoadPage();
        String pageTitle = driver.getTitle();

        /* Assertion */
        assertEquals("CI/CD buzz generator", pageTitle);
    }

    @Test
    public void testPageTextChangesOnReload() throws Exception {

        /* Precondition */

        /* Action */
        helperLoadPage();
        String firstBody = driver.findElementByTagName("body").getText();
        helperLoadPage();
        String secondBody = driver.findElementByTagName("body").getText();

        /* Assertion */
        assertNotEquals(firstBody, secondBody);
    }
}
