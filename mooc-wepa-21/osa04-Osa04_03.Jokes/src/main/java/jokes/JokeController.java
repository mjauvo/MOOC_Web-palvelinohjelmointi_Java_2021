package jokes;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JokeController {

    @Autowired
    private JokeService jokeService;

    @Autowired
    //private VoteRepository voteRepository;
    private VoteService voteService;

    @GetMapping("/jokes")
    public String getRandom(Model model) {
        Joke joke = jokeService.getRandomJoke();
        model.addAttribute("joke", joke);

        Long jokeId = joke.getId();

        if (voteService.findByJokeId(jokeId) == null) {
        //if (voteRepository.findByJokeId(jokeId) == null) {
            Vote v = new Vote(jokeId, 0, 0);
            voteService.save(v);
            //voteRepository.save(v);
        }
        model.addAttribute("vote", voteService.findByJokeId(jokeId));
        //model.addAttribute("vote", voteRepository.findByJokeId(jokeId));
        return "jokes";
    }

    @PostMapping("/jokes/{id}/vote")
    public String vote(@PathVariable Long id, @RequestParam String value) {
        this.voteService.vote(id, value);
        return "redirect:/jokes";
    }

    /*
    @Transactional
    @PostMapping("/jokes/{id}/vote")
    public String vote(@PathVariable Long id, @RequestParam String value) {

        Vote vote = this.voteRepository.findByJokeId(id);
        if (vote == null) {
            vote = new Vote(id, 0, 0);
        }
        if ("up".equals(value)) {
            vote.setUpVotes(vote.getUpVotes() + 1);
        } else if ("down".equals(value)) {
            vote.setDownVotes(vote.getDownVotes() + 1);
        }
        voteRepository.save(vote);
        return "redirect:/jokes";
    }
    */

}
