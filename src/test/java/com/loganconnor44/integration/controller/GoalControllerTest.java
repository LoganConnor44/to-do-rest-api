package com.loganconnor44.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loganconnor44.entity.Goal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
public class GoalControllerTest extends AbstractControllerTest {

    /**
     * Create and save a goal then verify the response status.
     *
     * @throws Exception
     */
    @Test
    public void addGoalTest() throws Exception {
        MvcResult result = this.createGoal();
        MockHttpServletResponse response = result.getResponse();
        this.retrieveUniqueIdFromHeader(response);
        //Verify request succeed
        Assert.assertEquals(
                201,
                response.getStatus()
        );
    }

    /**
     * Create and save a goal.
     * Retrieve the goal id.
     * Delete goal.
     * Verify expected response status.
     *
     * @throws Exception
     */
    @Test
    public void deleteGoalWithNoTasksTest() throws Exception {
        MvcResult goalResult = this.createGoal();
        MockHttpServletResponse goalResponse = goalResult.getResponse();
        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);
        MvcResult result = this.deleteGoal(goalId);
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /**
     * Create and save a goal.
     * Retrieve the goal id.
     * Create and save a task.
     * Verify expected response status.
     * Delete the goal with a task.
     * Verify expected response status.
     *
     * @throws Exception
     */
    @Test
    public void deleteGoalWithTasksTest() throws Exception {
        MvcResult goalResult = this.createGoal();
        MockHttpServletResponse goalResponse = goalResult.getResponse();
        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);

        String mockApplicationJson = String.format(
                "{\n" +
                        "\t\"name\" : \"Learn Spring\",\n" +
                        "\t\"description\" : \"Learn how to create a restful api using the Spring Boot framework.\",\n" +
                        "\t\"goal\" : {\n" +
                        "\t\t\"id\"  :\"%1d\"\n" +
                        "\t}\n" +
                        "}",
                goalId
        );
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/to-do/task/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockApplicationJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(this.httpCreated);

        MvcResult result = this.deleteGoal(goalId);
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /**
     * Create and save a goal.
     * Retrieve the goal id.
     * Retrieve the goal via http get.
     * Verify status code.
     * Verify returned values.
     *
     * @throws Exception
     */
    @Test
    public void retrieveGoalTest() throws Exception {
        MvcResult goalResult = this.createGoal();
        MockHttpServletResponse goalResponse = goalResult.getResponse();
        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(
                        String.format("/to-do/goal/%1d", goalId)
                )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(this.httpOkay)
                .andExpect(
                        jsonPath("$.owner").value("Logan Connor")
                )
                .andExpect(
                        jsonPath("$.id").value(goalId)
                );
    }

    /**
     * Create and save a goal.
     * Retrieve the goal id.
     * Update the goal via http put.
     * Verify expected status code.
     * Retrieve the goal via http get.
     * Verify expected updated values.
     * <p>
     * Be aware that passing the enum Status.COMPLETED will not
     * evaluate as the same as the json response status.
     *
     * @throws Exception
     */
    @Test
    public void updateGoalAsCompleteTest() throws Exception {


        MvcResult goalResult = this.createGoal();
        MockHttpServletResponse goalResponse = goalResult.getResponse();
        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);

        RequestBuilder getRequestBuilder = MockMvcRequestBuilders
                .get(
                        String.format("/to-do/goal/%1d", goalId)
                )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult getResult = this.mockMvc.perform(getRequestBuilder)
                .andExpect(this.httpOkay)
                .andReturn();
        String goalJson = getResult.getResponse().getContentAsString();

        RequestBuilder putRequestBuilder = MockMvcRequestBuilders
                .put(
                        String.format("/to-do/goal", goalId)
                )
                .accept(MediaType.APPLICATION_JSON)
                .content(goalJson.replaceAll("ACTIVE", "COMPLETED"))
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(putRequestBuilder)
                .andExpect(this.httpOkay);

        this.mockMvc.perform(getRequestBuilder)
                .andExpect(this.httpOkay);
    }
}