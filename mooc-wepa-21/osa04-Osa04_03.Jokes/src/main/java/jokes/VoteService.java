package jokes;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public String vote(Long id, String value) {

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

    @Transactional
    public Vote findByJokeId(Long id) {
        return voteRepository.findByJokeId(id);
        
    }

    @Transactional
    public void save(Vote vote) {
        voteRepository.save(vote);
    }
}
