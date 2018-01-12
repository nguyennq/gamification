package vn.nguyen.microservice.gamification.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import vn.nguyen.microservice.gamification.domain.LeaderBoardRow;
import vn.nguyen.microservice.gamification.domain.ScoreCard;

import java.util.List;

/**
 * Created by nals on 1/11/18.
 */
public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

    /**
     * Gets the total score for a given user, being the sum of the scores of all his ScoreCards
     *
     * @param userId
     * @return the total score for the given user
     */
    @Query("SELECT SUM(s.userId) FROM vn.nguyen.microservice.gamification.domain.ScoreCard s " +
            "WHERE s.userId = :userId GROUP BY s.userId")
    int getTotalScoreForUser(@Param("userId") final String userId);

    /**
     * Retrieves a list of {@link LeaderBoardRow}s representing the Leader Board of users and their total score.
     *
     * @return the leader board, sorted by highest score first
     */
    @Query("SELECT NEW vn.nguyen.microservice.gamification.domain.LeaderBoardRow(s.userId, SUM (s.score))" +
            " FROM vn.nguyen.microservice.gamification.domain.ScoreCard s GROUP BY s.userId ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirst10();

    /**
     * Retrieves all the ScoreCard for a given user, identified by his user id
     *
     * @param userId
     * @return a list containing all the ScoreCards for the given user, sorted by most recent
     */
    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
