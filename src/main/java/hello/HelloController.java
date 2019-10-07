package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/Hello")
    public String index() {
        String response = "Greetings from Spring Boot!";
        //String response = "<html><body><h1>\n" + "Greetings from Spring Boot!\n" + "</h1></body></html>";
        return response;
    }

}