package gifbin;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GifController {

    private int count;
    private long current;
    private long previous;
    private long next;

    private List<FileObject> fileObjects;

    @Autowired
    private FileObjectRepository fileObjectRepository;

    @GetMapping("/gifs")
    public String redirect() {
        fileObjects = fileObjectRepository.findAll();
        current = 1;
        return "redirect:/gifs/1";
    }

    @GetMapping("/gifs/{id}")
    public String showById(Model model, @PathVariable Long id) {

        count = fileObjectRepository.findAll().size();
        current = id;

        //Kuvia on mahdolisuus pyörittää ympäri kuin karusellissa
        if (count == 1) {
            current = count;
        }
        else if (count == 2) {
            if (current == 1) {
                next = 2;
            }
            else if (current == 2) {
                previous = 1;
            }
        }
        else {
            if (current == count) {
                next = 1;
                previous = current-1;
            }
            else if (current == 1) {
                next = current+1;
                previous = count;
            }
            else {
                next = current+1;
                previous = current-1;
            }
        }
             
        model.addAttribute("count", count);
        model.addAttribute("current", current);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);

        return "gifs";
    }

    @GetMapping(path = "/gifs/{id}/content", produces = "image/gif")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return fileObjects.get((int) current-1).getContent();
    }

    @PostMapping("/gifs")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {

        // Mitään tiedostoa ei ole valittu
        if (file.isEmpty()) {
            System.out.println("\nEMPTY FILE!!");
        }
        // Tiedosto ei ole mediatyyppiä 'image/gif'
        else if (!"image/gif".equals(file.getContentType())) {
            System.out.println("\nWRONG CONTENT TYPE: " + file.getContentType());
        }
        // Tallennetaan annettu gif-tidosto tietokantaan
        else {
            FileObject fo = new FileObject();
            fo.setContent(file.getBytes());
            fileObjectRepository.save(fo);
            count++;
        }

        return "redirect:/gifs";
    }
}