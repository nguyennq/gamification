package vn.nguyen.microservice.gamification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nguyen.microservice.gamification.domain.Badge;
import vn.nguyen.microservice.gamification.domain.GameStats;

import java.util.ArrayList;

/**
 * Created by nals on 1/12/18.
 */
@Service
public class GameServiceImpl implements GameService {

//    @Autowired
//    public GameServiceImpl(GameService gameService){
////        this.
//    }
    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
        return new GameStats(0L, 0, new ArrayList<>());
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        return null;
    }
}
