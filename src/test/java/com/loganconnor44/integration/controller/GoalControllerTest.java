package com.loganconnor44.integration.controller;

import com.google.common.base.CharMatcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
public class GoalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    final private ResultMatcher httpOkay = MockMvcResultMatchers.status().isOk();
    final private ResultMatcher httpCreated = MockMvcResultMatchers.status().isCreated();

    @LocalServerPort
    int randomServerPort;

    private Double keepNameUnique = Math.random();

    public MvcResult createGoal() throws Exception {
        String mockApplicationJson = String.format(
                "{\n" +
                "\t\"name\": \"Build Rest API %1f \",\n" +
                "\t\"description\" : \"Build a rest api for a to-do app that I can use as a microservice.\",\n" +
                "\t\"owner\": \"Logan Connor\"\n" +
                "}",
                keepNameUnique
        );

        //Create a post request with an accept header for application\json
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/to-do/goal/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockApplicationJson)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(requestBuilder).andReturn();
    }

    public MvcResult deleteGoal(Integer goalId) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(String.format("/to-do/goal/%d", goalId))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(requestBuilder).andReturn();
    }

    public Integer retrieveUniqueId(MockHttpServletResponse response) {
        String goalId = CharMatcher.inRange('0','9').retainFrom(
                response.getHeader("location")
        );
        return Integer.parseInt(goalId);
    }

    @Test
    public void addGoalTest() throws Exception {
        MvcResult result = this.createGoal();
        MockHttpServletResponse response = result.getResponse();
        //Verify request succeed
        Assert.assertEquals(
                201,
                response.getStatus()
        );
    }

    /**
     * @throws Exception
     */
    @Test
    public void deleteGoalWithNoTasks() throws Exception {
        MvcResult goalResult = this.createGoal();
        MockHttpServletResponse goalResponse = goalResult.getResponse();
        Integer goalId = this.retrieveUniqueId(goalResponse);
        MvcResult result = this.deleteGoal(goalId);
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    /**
     * @throws Exception
     */
    @Test
    public void deleteGoalWithTasks() throws Exception {
        MvcResult goalResult = this.createGoal();
        MockHttpServletResponse goalResponse = goalResult.getResponse();
        Integer goalId = this.retrieveUniqueId(goalResponse);

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

    @Test
    public void retrieveGoalTest() throws Exception {
        MvcResult goalResult = this.createGoal();
        MockHttpServletResponse goalResponse = goalResult.getResponse();
        Integer goalId = this.retrieveUniqueId(goalResponse);
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
     * Be aware that passing the enum Status.COMPLETED will not evaluate as the same as the json response status.
     *
     * @throws Exception
     */
    @Test
    public void updateGoalAsCompleteTest() throws Exception {
        MvcResult goalResult = this.createGoal();
        MockHttpServletResponse goalResponse = goalResult.getResponse();
        Integer goalId = this.retrieveUniqueId(goalResponse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(
                        String.format("/to-do/goal/%1d", goalId)
                )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(this.httpOkay);

        RequestBuilder getRequestBuilder = MockMvcRequestBuilders
                .get(
                        String.format("/to-do/goal/%1d", goalId)
                )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(getRequestBuilder)
                .andExpect(
                        jsonPath("$.status").value("COMPLETED")
                );
    }
}