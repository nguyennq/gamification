package vn.nguyen.microservice.gamification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.nguyen.microservice.gamification.domain.GameStats;
import vn.nguyen.microservice.gamification.service.GameService;

/**
 * Created by nals on 1/12/18.
 */
@RestController
@RequestMapping("/stats")
public class UserStatsController {
    private final GameService gameService;

    public UserStatsController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public GameStats getStatsForUser(@RequestParam("userId") final Long userId){
        return gameService.retrieveStatsForUser(userId);
    }
}
