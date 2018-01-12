package vn.nguyen.microservice.gamification.service;

import vn.nguyen.microservice.gamification.domain.GameStats;

/**
 * Created by nals on 1/12/18.
 */
public interface GameService {

    /**
     * Process a new Attempt from a given user.
     * @param userId the user' unique id
     * @param attemptId the attempt id, can be used to retrieve extra data if needed
     * @param correct indicates if the attempt was correct
     *
     * @return a {@link GameStats} object containing the new score and badge cards obtained
     * */
    GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct);

    /**
     * Gets the game statistics for a given user
     * @param userId
     * @return a {@link GameStats} the total statistics for that user
     * */
    GameStats retrieveStatsForUser(Long userId);
}
