//package vn.nguyen.microservice.gamification.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import vn.nguyen.microservice.gamification.domain.Badge;
//import vn.nguyen.microservice.gamification.domain.BadgeCard;
//import vn.nguyen.microservice.gamification.domain.GameStats;
//import vn.nguyen.microservice.gamification.domain.ScoreCard;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
///**
// * Created by nals on 1/12/18.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class GameServiceTest {
//
////    @MockBean
////    private GameService gameService;
//    @Autowired
//    private GameService gameService;
//    @Test
//    public void newAttemptForUser() throws Exception {
//        //given
//        BadgeCard badgeCard = preparedBadgeCard();
//        ScoreCard scoreCard = preparedScoredCard();
//        //when
//        GameStats gameStats = gameService.newAttemptForUser(badgeCard.getUserId(),scoreCard.getAttemptId(),true);
//        //then
//        assertThat(gameStats.getUserId()).isEqualTo(0L);
//
//    }
//
//    private ScoreCard preparedScoredCard() {
//        ScoreCard scoreCard = new ScoreCard(0L,0L);
//        return scoreCard;
//    }
//
//    private BadgeCard preparedBadgeCard() {
//        BadgeCard badgeCard = new BadgeCard(0L, Badge.BRONZE_MULTIPLICATION);
//        return badgeCard;
//    }
//
//    @Test
//    public void retrieveStatsForUser() throws Exception {
//    }
//
//}