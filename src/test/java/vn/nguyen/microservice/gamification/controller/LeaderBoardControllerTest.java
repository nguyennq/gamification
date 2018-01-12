package vn.nguyen.microservice.gamification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import vn.nguyen.microservice.gamification.domain.LeaderBoardRow;
import vn.nguyen.microservice.gamification.service.LeaderBoardService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by nals on 1/12/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LeaderBoardController.class)
public class LeaderBoardControllerTest {

    @MockBean
    private LeaderBoardService leaderBoardService;
    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<List<LeaderBoardRow>> jacksonTester;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this,new ObjectMapper());
    }

    @Test
    public void getLeaderBoardRows() throws Exception {
        //given
        LeaderBoardRow leaderBoardRow = preparedLeaderBoardRow();
        List<LeaderBoardRow> expectedLeaderBoardRow = Arrays.asList(leaderBoardRow);

        given(leaderBoardService.getCurrentLeaderBoardWith10Person()).willReturn(expectedLeaderBoardRow);
        //when
        MockHttpServletResponse httpServletResponse = mockMvc.perform(get("/leaders").
                accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        //then
        assertThat(httpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(httpServletResponse.getContentAsString()).isEqualTo(jacksonTester.write(expectedLeaderBoardRow).getJson());
    }

    private LeaderBoardRow preparedLeaderBoardRow() {
        LeaderBoardRow leaderBoardRow = new LeaderBoardRow(1L,10L);
        return leaderBoardRow;
    }

}