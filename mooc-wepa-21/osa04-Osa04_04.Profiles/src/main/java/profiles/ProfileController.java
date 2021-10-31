package profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfileController {

    @Autowired
    private Environment environment;

    @ResponseBody
    @GetMapping("/profile")
    public String profile() {
        return environment.getActiveProfiles()[0];
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("active", this.environment.getActiveProfiles()[0]);
        return "index";
    }
    /**/

}
