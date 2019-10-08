package hello;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.safari.SafariDriver;

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

    private SafariDriver driver;

    @Before
    public void setup(){
        driver = new SafariDriver();
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
