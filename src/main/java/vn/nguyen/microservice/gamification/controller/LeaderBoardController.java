package vn.nguyen.microservice.gamification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nguyen.microservice.gamification.domain.LeaderBoardRow;
import vn.nguyen.microservice.gamification.service.LeaderBoardService;

import java.util.List;

/**
 * Created by nals on 1/12/18.
 */
@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

    private final LeaderBoardService leaderBoardService;

    public LeaderBoardController(final LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }
    @GetMapping
    public List<LeaderBoardRow> getLeaderBoardRows(){
        return leaderBoardService.getCurrentLeaderBoardWith10Person();
    }
}
