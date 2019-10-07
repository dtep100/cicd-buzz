package hello;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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


    @Test
    public void testPageLoadsWithoutError() throws Exception {
        assertTrue("Unimplemented test",false);
    }

    @Test
    public void testPageTextChangesOnReload() throws Exception {
        assertTrue("Unimplemented test",false);
    }

}


    //def _load_buzz_page(self):
    //    self.driver.get("http://localhost:5000")
    //
    //    def test_page_loads_without_error(self):
    //    self._load_buzz_page()
    //    assert "buzz" in self.driver.title
    //
    //    def test_page_text_changes_on_reload(self):
    //    self._load_buzz_page()
    //    first_text = self.driver.find_element_by_tag_name("body").text
    //    print("\"" + first_text + "\"")
    //    self._load_buzz_page()
    //    second_text = self.driver.find_element_by_tag_name("body").text
    //    print("\"" + second_text + "\"")
    //    assert first_text != second_text

