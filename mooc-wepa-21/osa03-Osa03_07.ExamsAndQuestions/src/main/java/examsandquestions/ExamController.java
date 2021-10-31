package examsandquestions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExamController {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/exams")
    public String listExams(Model model) {
        Sort sortable = Sort.by(Sort.Direction.DESC, "examDate");
        model.addAttribute("exams", examRepository.findAll(sortable));
        return "exams";
    }

    @GetMapping("/exams/{id}")
    public String viewExam(Model model, @PathVariable Long id) {
        Exam exam = examRepository.getOne(id);
        List<Question> questions = questionRepository.findAll();

        model.addAttribute("exam", exam);
        model.addAttribute("questions", questions);
        return "exam";
    }

    @PostMapping("/exams")
    public String addExam(@RequestParam String subject,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate examDate) {

        examRepository.save(new Exam(subject, examDate, new ArrayList<>()));
        return "redirect:/exams";
    }

    @PostMapping("/exams/{examId}/questions/{questionId}")
    @Transactional
    public String addQuestionToExam(@PathVariable Long examId, @PathVariable Long questionId) {

        Exam exam = examRepository.getOne(examId);
        Question question = questionRepository.getOne(questionId);

        question.getExams().add(exam);
        questionRepository.save(question);

        return "redirect:/exams/" + examId;
    }
}

