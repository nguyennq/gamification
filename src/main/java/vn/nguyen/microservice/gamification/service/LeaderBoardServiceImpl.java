package vn.nguyen.microservice.gamification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nguyen.microservice.gamification.domain.LeaderBoardRow;
import vn.nguyen.microservice.gamification.repository.ScoreCardRepository;

import java.util.List;

/**
 * Created by nals on 1/12/18.
 */
@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private ScoreCardRepository scoreCardRepository;

    public LeaderBoardServiceImpl(ScoreCardRepository scoreCardRepository) {
        this.scoreCardRepository = scoreCardRepository;
    }

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoardWith10Person() {
        return scoreCardRepository.findFirst10();
    }
}
