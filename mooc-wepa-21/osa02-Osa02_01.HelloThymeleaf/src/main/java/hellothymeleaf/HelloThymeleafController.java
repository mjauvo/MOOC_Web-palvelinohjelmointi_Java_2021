package hellothymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloThymeleafController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/video")
    public String video() {
        return "video";
    }
}
