package vn.nguyen.microservice.gamification.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.nguyen.microservice.gamification.domain.LeaderBoardRow;
import vn.nguyen.microservice.gamification.repository.ScoreCardRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by nals on 1/12/18.
 */
public class LeaderBoardServiceImplTest {

    @Mock
    private ScoreCardRepository scoreCardRepository;
    @InjectMocks
    LeaderBoardServiceImpl leaderBoardService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        leaderBoardService = new LeaderBoardServiceImpl(scoreCardRepository);
    }

    @Test
    public void getCurrentLeaderBoardWith10Person_ShouldReturnListLeaderBoardWithin10Person() throws Exception {
        //given
        LeaderBoardRow leaderBoardRow = preparedLeaderBoardRow();
        List<LeaderBoardRow> expectedLeaderBoardRow = Collections.singletonList(leaderBoardRow);
        given(scoreCardRepository.findFirst10()).willReturn(expectedLeaderBoardRow);
        //when
        List<LeaderBoardRow> leaderBoardWith10Person = leaderBoardService.getCurrentLeaderBoardWith10Person();
        //then
        assertThat(leaderBoardWith10Person).isEqualTo(expectedLeaderBoardRow);


    }

    private LeaderBoardRow preparedLeaderBoardRow() {
        LeaderBoardRow leaderBoardRow = new LeaderBoardRow(1L,10L);
        return leaderBoardRow;
    }

}