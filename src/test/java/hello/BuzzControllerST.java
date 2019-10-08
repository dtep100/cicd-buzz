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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) /* Remote Web-driver does not like random ports */
public class BuzzControllerST {

    @LocalServerPort
    private int port;

    private RemoteWebDriver driver;
    private boolean remoteTesting = false;

    @Before
    public void setup() throws Exception {

        {
            remoteTesting = false;
            /* Check if we are using a remote driver or a local driver */
            String envVar = System.getenv("CI");

            /* Check if environment variable was actually set */
            if (null != envVar){
                if(envVar.toUpperCase() == "TRUE"){
                    remoteTesting = true;
                }
            }
        }

        if(false == remoteTesting)
        {
            driver = new SafariDriver();
        } else {
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

    @After
    public void teardown(){
        driver.close();
        if(true == remoteTesting) {
            driver.quit();
        }
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
