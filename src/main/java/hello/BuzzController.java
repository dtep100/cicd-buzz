package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BuzzController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("buzz", Generator.generateBuzz());
        return "index";
    }
}