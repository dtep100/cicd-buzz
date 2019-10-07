package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BuzzController {

    @RequestMapping("/")
    public String index() {
        String response = "<html><body><h1>\n" + Generator.generateBuzz() + "\n</h1></body></html>";
        return response;
    }

}