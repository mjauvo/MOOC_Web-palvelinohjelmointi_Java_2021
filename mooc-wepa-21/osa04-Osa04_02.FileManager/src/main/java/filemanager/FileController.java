package filemanager;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    @Autowired
    private FileObjectRepository fileObjectRepository;

    private List<FileObject> fileObjects;

    @PostMapping("/files")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {

        FileObject fo = new FileObject();

        fo.setName(file.getOriginalFilename());
        fo.setContentType(file.getContentType());
        fo.setContentLength(file.getSize());
        fo.setContent(file.getBytes());

        fileObjectRepository.save(fo);

        return "redirect:/files";
    }

    @PostMapping("/files/{id}")
    public String delete(@PathVariable Long id) throws IOException {

        fileObjectRepository.deleteById(id);
        return "redirect:/files";
    }

    @GetMapping("/files")
    public String getAll(Model model) {

        model.addAttribute("files", fileObjectRepository.findAll());
        return "/files";
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getById(@PathVariable Long id) {
        FileObject fo = fileObjectRepository.getOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getContentType()));
        headers.setContentLength(fo.getContentLength());
        headers.add("Content-Disposition", "attachment; filename=" + fo.getName());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }

}
