package com.loganconnor44.unit.controller;

import com.loganconnor44.controller.GoalController;
import com.loganconnor44.dao.GoalDAO;
import com.loganconnor44.dao.TaskDAO;
import com.loganconnor44.entity.Goal;
import com.loganconnor44.service.GoalService;
import com.loganconnor44.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.net.URI;

@RunWith(SpringRunner.class)
@WebMvcTest(GoalController.class)
public class GoalControllerTest {
    @MockBean
    private GoalService goalService;
    @MockBean
    private GoalDAO goalDAO;
    @MockBean
    private TaskService taskService;
    @MockBean
    private TaskDAO taskDAO;

    @Test
    public void addGoalTest() throws Exception {
        Goal goal = new Goal();
        goal.setName("James Bond");
        goal.setDescription("Save the queen.");
        goal.setOwner("MI6");
        when(goalService.addGoal(goal)).thenReturn(true);



//        this.mockMvc.perform(post("/to-do/goal"))
//            .andExpect(status().isOk());
    }

}
