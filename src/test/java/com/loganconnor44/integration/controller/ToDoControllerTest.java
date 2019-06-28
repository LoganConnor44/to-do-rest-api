package com.loganconnor44.integration.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    final private ResultMatcher httpOkay = MockMvcResultMatchers.status().isOk();

    public MvcResult createGoal() throws Exception {
        String mockApplicationJson = "{\n" +
                "\t\"name\": \"Build Rest API\",\n" +
                "\t\"description\" : \"Build a rest api for a to-do app that I can use as a microservice.\",\n" +
                "\t\"owner\": \"Logan Connor\"\n" +
                "}";

        //Create a post request with an accept header for application\json
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/to-do/goal/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockApplicationJson)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(requestBuilder).andReturn();
    }

    @Test
    public void addGoalTest() throws Exception {
        MvcResult result = createGoal();

        MockHttpServletResponse response = result.getResponse();

        //Assert that the return status is CREATED
        assertEquals(
                HttpStatus.CREATED.value(),
                response.getStatus()
        );

        assertEquals(
                "http://localhost/goal/1",
                response.getHeader(HttpHeaders.LOCATION)
        );
    }

    @Test
    public void retrieveGoalTest() throws Exception {
        this.createGoal();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/to-do/goal/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(this.httpOkay)
                .andExpect(
                        jsonPath("$.owner").value("Logan Connor")
                )
                .andExpect(
                        jsonPath("$.id").value(1)
                );
    }

    /**
     * Be aware that passing the enum Status.COMPLETED will not evaluate as the same as the json response status.
     *
     * @throws Exception
     */
    @Test
    public void updateGoalAsCompleteTest() throws Exception {
        this.addGoalTest();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/to-do/goal/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(this.httpOkay);

        RequestBuilder getRequestBuilder = MockMvcRequestBuilders
                .get("/to-do/goal/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(getRequestBuilder)
                .andExpect(
                        jsonPath("$.status").value("COMPLETED")
                );
    }

    /**
     * 
     *
     * @throws Exception
     */
    @Test
    public void deleteGoal() throws Exception {
        this.createGoal();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/to-do/goal/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}
