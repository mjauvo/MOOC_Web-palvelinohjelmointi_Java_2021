package hellomodel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloModelController {

    @GetMapping("/")
    public String home(@RequestParam String title, @RequestParam String person, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("person", person);
        return "index";
    }
}
