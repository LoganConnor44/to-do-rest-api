package com.loganconnor44.integration.controller;

import com.google.common.base.CharMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    final protected ResultMatcher httpOkay = MockMvcResultMatchers.status().isOk();
    final protected ResultMatcher httpCreated = MockMvcResultMatchers.status().isCreated();

    @LocalServerPort
    protected int randomServerPort;

    protected Double keepNameUnique = Math.random();

    protected MvcResult createGoal() throws Exception {
        String mockApplicationJson = String.format(
                "{\n" +
                        "\t\"name\": \"Build Rest API %1f \",\n" +
                        "\t\"description\" : \"Build a rest api for a to-do app that I can use as a microservice.\",\n" +
                        "\t\"owner\": \"Logan Connor\"\n" +
                        "}",
                this.keepNameUnique
        );

        //Create a post request with an accept header for application\json
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/to-do/goal/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockApplicationJson)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(requestBuilder).andReturn();
    }

    protected MvcResult deleteGoal(Integer goalId) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(String.format("/to-do/goal/%d", goalId))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(requestBuilder).andReturn();
    }

    protected Integer retrieveUniqueIdFromHeader(MockHttpServletResponse response) {
        String id = CharMatcher.inRange('0', '9').retainFrom(
                response.getHeader("location")
        );
        return Integer.parseInt(id);
    }
}
