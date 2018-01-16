package vn.nguyen.microservice.gamification.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import vn.nguyen.microservice.gamification.client.MultiplicationResultAttemptClient;
import vn.nguyen.microservice.gamification.client.dto.MultiplicationResultAttempt;
import vn.nguyen.microservice.gamification.domain.Badge;
import vn.nguyen.microservice.gamification.domain.BadgeCard;
import vn.nguyen.microservice.gamification.domain.GameStats;
import vn.nguyen.microservice.gamification.domain.ScoreCard;
import vn.nguyen.microservice.gamification.repository.BadgeCardRepository;
import vn.nguyen.microservice.gamification.repository.ScoreCardRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by nals on 1/12/18.
 */

public class GameServiceImplTest {

    @Mock
    private ScoreCardRepository scoreCardRepository;
    @Mock
    private BadgeCardRepository badgeCardRepository;
    @Mock
    private MultiplicationResultAttemptClient resultAttemptClient;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        gameServiceImpl = new GameServiceImpl(badgeCardRepository, scoreCardRepository, resultAttemptClient);
    }

    @Test
    public void newAttemptForUser_ShouldReturnGameStatsWithScoreWhenUserAnswerFirstCorrect() throws Exception {
        //given
        ScoreCard scoreCard = preparedScoredCard();
        BadgeCard badgeCard = preparedBadgeCard(Badge.FIRST_WON);
        int totalScoreForUser = 10;
        given(scoreCardRepository.getTotalScoreForUser(scoreCard.getUserId())).willReturn(totalScoreForUser);
        //this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(scoreCard.getUserId())).
                willReturn(Collections.singletonList(scoreCard));
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(scoreCard.getUserId())).
                willReturn(Collections.emptyList());
        MultiplicationResultAttempt resultAttemptLuckyNumber = preparedMultiplicationResultAttempt("john conner",50,50,true);
        given(resultAttemptClient.retrieveMultiplicationResultAttemptById(scoreCard.getAttemptId())).willReturn(resultAttemptLuckyNumber);
        //when
        GameStats gameStats = gameServiceImpl.newAttemptForUser(scoreCard.getUserId(), scoreCard.getAttemptId(), true);
        //then
        assertThat(gameStats.getScore()).isEqualTo(scoreCard.getScore());
        assertThat(gameStats.getBadges()).containsOnly(Badge.FIRST_WON);

    }

    @Test
    public void newAttemptForUser_ShouldReturnGameStatsWhenUserAnswerFirstCorrectAndGetScoreAndBadge() throws Exception {
        //given
        ScoreCard scoreCard = preparedScoredCard();
        BadgeCard firstWonBadgeCard = preparedBadgeCard(Badge.FIRST_WON);
        int totalScoreForUser = 100;
        given(scoreCardRepository.getTotalScoreForUser(scoreCard.getUserId())).willReturn(totalScoreForUser);
        //this repository will return the just-won score card
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(scoreCard.getUserId())).
                willReturn(createNScoreCards(10,scoreCard.getUserId()));
        //the first won badge is already there
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(scoreCard.getUserId())).
                willReturn(Collections.singletonList(firstWonBadgeCard));
        MultiplicationResultAttempt resultAttemptLuckyNumber = preparedMultiplicationResultAttempt("john conner",50,50,true);
        given(resultAttemptClient.retrieveMultiplicationResultAttemptById(scoreCard.getAttemptId())).willReturn(resultAttemptLuckyNumber);
        //when
        GameStats gameStats = gameServiceImpl.newAttemptForUser(scoreCard.getUserId(), scoreCard.getAttemptId(), true);
        //then
        assertThat(gameStats.getScore()).isEqualTo(scoreCard.DEFAULT_SCORE);
        assertThat(gameStats.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATION);
    }

    private MultiplicationResultAttempt preparedMultiplicationResultAttempt(String userAlias,
             int multiplicationFactorNumberA, int multiplicationFactorNumberB, boolean correct) {
        MultiplicationResultAttempt resultAttemptLuckyNumber = new MultiplicationResultAttempt();

        return resultAttemptLuckyNumber;
    }

    @Test
    public void retrieveStatsForUser_ShouldReturnScoreAndBadge() throws Exception {
        //given
        ScoreCard scoreCard = preparedScoredCard();
        BadgeCard firstWonBadgeCard = preparedBadgeCard(Badge.SILVER_MULTIPLICATION);
        int totalScoreForUser = 1000;
        given(scoreCardRepository.getTotalScoreForUser(scoreCard.getUserId())).
                willReturn(totalScoreForUser);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(scoreCard.getUserId())).
                willReturn(Collections.singletonList(firstWonBadgeCard));
        //when
        GameStats gameStats = gameServiceImpl.retrieveStatsForUser(scoreCard.getUserId());
        //then
        assertThat(gameStats.getScore()).isEqualTo(totalScoreForUser);
        assertThat(gameStats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATION);

    }

    private List<ScoreCard> createNScoreCards(int number, Long userId) {
        return IntStream.range(0,number).mapToObj(i -> new ScoreCard(userId,(long) i)).collect(Collectors.toList());
    }


    private ScoreCard preparedScoredCard() {
        ScoreCard scoreCard = new ScoreCard(1L,8L);
        return scoreCard;
    }

    private BadgeCard preparedBadgeCard(Badge badge) {
        BadgeCard badgeCard = new BadgeCard(1L, badge);
        return badgeCard;
    }

}