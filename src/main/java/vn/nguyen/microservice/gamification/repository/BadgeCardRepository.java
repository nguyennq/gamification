package vn.nguyen.microservice.gamification.repository;

import org.springframework.data.repository.CrudRepository;
import vn.nguyen.microservice.gamification.domain.BadgeCard;

import java.util.List;

/**
 * Created by nals on 1/11/18.
 */
public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {
    /**
     * Retrieves all BadgeCards for a given user.
     *
     * @param userId the id of the user to look for BadgeCards
     * @return the list of BadgeCards, sorted by most recent.
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);
}
