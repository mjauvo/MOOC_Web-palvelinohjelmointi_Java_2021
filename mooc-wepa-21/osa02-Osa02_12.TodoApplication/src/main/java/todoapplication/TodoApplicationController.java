package todoapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoApplicationController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("items", taskRepository.findAll());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Long id) {
        model.addAttribute("item", taskRepository.getOne(id));
        return "todo";
    }

    @PostMapping("/")
    public String create(@RequestParam String name) {
        taskRepository.save(new Task(name));
        return "redirect:/";
    }
}
