package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/Hello")
    public String index() {
        String response = "Greetings from Spring Boot!";
        return response;
    }

}