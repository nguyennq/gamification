package vn.nguyen.microservice.gamification.service;

import vn.nguyen.microservice.gamification.domain.LeaderBoardRow;
import vn.nguyen.microservice.gamification.domain.ScoreCard;

import java.util.List;

/**
 * Created by nals on 1/12/18.
 */
public interface LeaderBoardService {
    List<LeaderBoardRow> getCurrentLeaderBoardWith10Person();
}
