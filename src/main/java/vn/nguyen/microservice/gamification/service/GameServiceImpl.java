package vn.nguyen.microservice.gamification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.nguyen.microservice.gamification.client.MultiplicationResultAttemptClient;
import vn.nguyen.microservice.gamification.client.dto.MultiplicationResultAttempt;
import vn.nguyen.microservice.gamification.domain.Badge;
import vn.nguyen.microservice.gamification.domain.BadgeCard;
import vn.nguyen.microservice.gamification.domain.GameStats;
import vn.nguyen.microservice.gamification.domain.ScoreCard;
import vn.nguyen.microservice.gamification.repository.BadgeCardRepository;
import vn.nguyen.microservice.gamification.repository.ScoreCardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by nals on 1/12/18.
 */
@Service
@Slf4j
public class GameServiceImpl implements GameService {

    public static final int LUCKY_NUMBER = 42;
    private BadgeCardRepository badgeCardRepository;
    private ScoreCardRepository scoreCardRepository;
    private MultiplicationResultAttemptClient resultAttemptClient;

    public GameServiceImpl(BadgeCardRepository badgeCardRepository,
                           ScoreCardRepository scoreCardRepository,
                           MultiplicationResultAttemptClient resultAttemptClient) {
        this.badgeCardRepository = badgeCardRepository;
        this.scoreCardRepository = scoreCardRepository;
        this.resultAttemptClient = resultAttemptClient;
    }

    @Override
    public GameStats newAttemptForUser(final Long userId, final Long attemptId,final boolean correct) {
        //For the first phase we'll give points only if it' correct
        if(correct){
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("User with id {} scored {} points for attempt id {}", userId, scoreCard.getScore(), attemptId);
            List<BadgeCard> badgeCards = processForBadgeCard(userId, attemptId);
            return new GameStats(userId,scoreCard.getScore(),
                    badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
        }
        return GameStats.emptyStats(userId);
    }

    /**
     * Checks the total score and the different score cards
     * obtained
     * to give new badges in case their conditions are met.
     */
    private List<BadgeCard> processForBadgeCard(final Long userId, final Long attemptId) {
        List<BadgeCard> badgeCards = new ArrayList<>();
        int totalScoreForUser = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("New score for user {} is {}", userId, totalScoreForUser);
        List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        //matching Badges depending on score
        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.BRONZE_MULTIPLICATION,
                totalScoreForUser,
                100,
                userId).ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.SILVER_MULTIPLICATION,
                totalScoreForUser,
                500,
                userId).ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.GOLD_MULTIPLICATION,
                totalScoreForUser,
                999,
                userId).ifPresent(badgeCards::add);
        //First won Badge
        if (scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
            BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
            badgeCards.add(firstWonBadge);
        }

        //process Lucky number badge
        MultiplicationResultAttempt resultAttemptLuckyNumber =
                resultAttemptClient.retrieveMultiplicationResultAttemptById(attemptId);
        if(!containsBadge(badgeCardList,Badge.LUCKY_NUMBER) &&
                (LUCKY_NUMBER == resultAttemptLuckyNumber.getMultiplicationFactorNumberA() ||
                        LUCKY_NUMBER == resultAttemptLuckyNumber.getMultiplicationFactorNumberB())){
            BadgeCard luckyNumberBadge = giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
            badgeCards.add(luckyNumberBadge);
        }

        return badgeCards;
    }
    /**
     * Convenience method to check the current score against
     * the different thresholds to gain badges.
     * It also assigns badge to user if the conditions are met.
     * */
    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCardList,
                                                   final Badge bronzeMultiplication,
                                                   final int totalScoreForUser,
                                                   final int scoreThreshold,
                                                   Long userId) {
        if(totalScoreForUser >= scoreThreshold && !containsBadge(badgeCardList,bronzeMultiplication)){
            return Optional.of(giveBadgeToUser(bronzeMultiplication,userId));
        }
        return Optional.empty();

    }

    private BadgeCard giveBadgeToUser(final Badge bronzeMultiplication, final Long userId) {
        BadgeCard badgeCard = new BadgeCard(userId, bronzeMultiplication);
        badgeCardRepository.save(badgeCard);
        log.info("User with id {} won a new Badge: {}", userId,bronzeMultiplication);
        return badgeCard;
    }
    /**
     * Checks if the passed list of badges includes the one being checked
     * */
    private boolean containsBadge(final List<BadgeCard> badgeCardList, final Badge bronzeMultiplication) {
        return badgeCardList.stream().anyMatch(b -> b.getBadge().equals(bronzeMultiplication));
    }

    @Override
    public GameStats retrieveStatsForUser(final Long userId) {
        int totalScoreForUser = scoreCardRepository.getTotalScoreForUser(userId);
        List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

        return new GameStats(userId,
                totalScoreForUser,
                badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
    }
}
