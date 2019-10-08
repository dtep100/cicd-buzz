package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BuzzController {

    @RequestMapping("/")
    public String index() {
        String response = buildBuzzPage(Generator.generateBuzz());
        return response;
    }

    public String buildBuzzPage(String bodyText){
        String html;
        html =  "<html>\n" +
                "  <head>\n" +
                "    <title>CI/CD buzz generator</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lobster\" \n" +
                "      rel=\"stylesheet\">\n" +
                "    <style>\n" +
                "      body {\n" +
                "        padding: 0; \n" +
                "        background: url('background.jpg') no-repeat;\n" +
                "        background-size: cover; \n" +
                "        background-color: #58576a; \n" +
                "        background-blend-mode: multiply; \n" +
                "      }\n" +
                "      .buzz { \n" +
                "        color: #fff; \n" +
                "        font-family: 'Lobster', cursive; \n" +
                "        font-size: 56px;\n" +
                "      }\n" +
                "      div.buzz { \n" +
                "        max-width: 50%; \n" +
                "        margin: 0 auto; \n" +
                "        text-align: center; \n" +
                "        position: relative; \n" +
                "        top: 40%;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"buzz\">\n" +
                "      <p class=\"buzz\">" + bodyText + "</p>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        return(html);
    }

}