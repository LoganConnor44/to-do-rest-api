//package com.loganconnor44.integration.controller;
//
//import com.google.common.base.CharMatcher;
//import com.loganconnor44.controller.GoalController;
//import com.loganconnor44.controller.TaskController;
//import com.loganconnor44.dao.GoalDAO;
//import com.loganconnor44.dao.TaskDAO;
//import com.loganconnor44.service.GoalService;
//import com.loganconnor44.service.TaskService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest
//@AutoConfigureMockMvc
//@TestPropertySource(
//    locations = "classpath:application-integration-test.properties"
//)
//abstract class ThisIsATest {
//
//    @MockBean
//    private GoalDAO goalDAO;
//
//    @MockBean
//    private TaskDAO taskDAO;
//
//    @MockBean
//    private GoalService goalService;
//
//    @MockBean
//    private TaskService taskService;
//
//    @Autowired
//    GoalController goalController;
//
//    @Autowired
//    TaskController taskController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private Double keepNameUnique = Math.random();
//
//    protected MvcResult createGoal() throws Exception {
//        String mockApplicationJson = String.format(
//                "{\n" +
//                        "\t\"name\": \"Build Rest API %1f \",\n" +
//                        "\t\"description\" : \"Build a rest api for a to-do app that I can use as a microservice.\",\n" +
//                        "\t\"owner\": \"Logan Connor\"\n" +
//                        "}",
//                this.keepNameUnique
//        );
//
//        //Create a post request with an accept header for application\json
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/to-do/goal/")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(mockApplicationJson)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        return mockMvc.perform(requestBuilder).andReturn();
//    }
//
//    protected Integer retrieveUniqueIdFromHeader(MockHttpServletResponse response) {
//        String id = CharMatcher.inRange('0', '9').retainFrom(
//                response.getHeader("location")
//        );
//        return Integer.parseInt(id);
//    }
//}
