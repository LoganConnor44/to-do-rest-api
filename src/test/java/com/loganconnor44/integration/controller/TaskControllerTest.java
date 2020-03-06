//package com.loganconnor44.integration.controller;
////
////import org.junit.Assert;
////import org.junit.Test;
////import org.junit.runner.RunWith;
////import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
////import org.springframework.boot.test.context.SpringBootTest;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.MediaType;
////import org.springframework.mock.web.MockHttpServletResponse;
////import org.springframework.test.context.junit4.SpringRunner;
////import org.springframework.test.web.servlet.MvcResult;
////import org.springframework.test.web.servlet.RequestBuilder;
////import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
////
////import static org.junit.Assert.assertEquals;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
////
////@RunWith(SpringRunner.class)
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@AutoConfigureMockMvc(secure = false)
////public class TaskControllerTest extends AbstractControllerTest {
////
////    private MvcResult createTask(Integer taskId) throws Exception {
////        String mockApplicationJson = String.format(
////                "{\n" +
////                        "\t\"name\" : \"Learn Spring\",\n" +
////                        "\t\"description\" : \"Learn how to create a restful api using the Spring Boot framework.\",\n" +
////                        "\t\"goal\" : {\n" +
////                        "\t\t\"id\"  :\"%1d\"\n" +
////                        "\t}\n" +
////                        "}",
////                taskId
////        );
////
////        //Create a post request with an accept header for application\json
////        RequestBuilder requestBuilder = MockMvcRequestBuilders
////                .post("/to-do/task/")
////                .accept(MediaType.APPLICATION_JSON)
////                .content(mockApplicationJson)
////                .contentType(MediaType.APPLICATION_JSON);
////
////        return mockMvc.perform(requestBuilder).andReturn();
////    }
////
////    /**
////     * Create and save a task then verify the response status.
////     *
////     * @throws Exception
////     */
////    @Test
////    public void addTaskTest() throws Exception {
////        MvcResult goalResult = this.createGoal();
////        MockHttpServletResponse goalResponse = goalResult.getResponse();
////        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);
////
////        MvcResult result = this.createTask(goalId);
////        MockHttpServletResponse response = result.getResponse();
////        //Verify request succeed
////        Assert.assertEquals(
////                201,
////                response.getStatus()
////        );
////    }
////
////    @Test
////    public void addSubTaskTest() throws Exception {
////        MvcResult goalResult = this.createGoal();
////        MockHttpServletResponse goalResponse = goalResult.getResponse();
////        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);
////
////        MvcResult parentTaskResult = this.createTask(goalId);
////        MockHttpServletResponse parentTaskResponse = parentTaskResult.getResponse();
////        Integer parentTaskId = this.retrieveUniqueIdFromHeader(parentTaskResponse);
////
////        MvcResult subTaskResult = this.createTask(parentTaskId);
////        MockHttpServletResponse subTaskResponse = subTaskResult.getResponse();
////
////        //Verify request succeed
////        Assert.assertEquals(
////                201,
////                subTaskResponse.getStatus()
////        );
////    }
////
////    /**
////     * Create and save a goal.
////     * Retrieve the goal id.
////     * Create and save a task.
////     * Retrieve the task id.
////     * Retrieve the task via http get.
////     * Verify status code.
////     * Verify returned values.
////     *
////     * @throws Exception
////     */
////    @Test
////    public void retrieveTaskTest() throws Exception {
////        MvcResult goalResult = this.createGoal();
////        MockHttpServletResponse goalResponse = goalResult.getResponse();
////        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);
////
////        MvcResult taskResult = this.createTask(goalId);
////        MockHttpServletResponse taskResponse = taskResult.getResponse();
////        Integer taskId = this.retrieveUniqueIdFromHeader(taskResponse);
////        RequestBuilder requestBuilder = MockMvcRequestBuilders
////                .get(
////                        String.format("/to-do/task/%1d", taskId)
////                )
////                .accept(MediaType.APPLICATION_JSON)
////                .contentType(MediaType.APPLICATION_JSON);
////
////        mockMvc.perform(requestBuilder)
////                .andExpect(this.httpOkay)
////                .andExpect(
////                        jsonPath("$.goal.id").value(goalId)
////                )
////                .andExpect(
////                        jsonPath("$.id").value(taskId)
////                );
////    }
////
////    /**
////     * Create and save a goal.
////     * Retrieve the goal id.
////     * Create and save a task.
////     * Retrieve the task id.
////     * Update the task via http put.
////     * Verify expected status code.
////     * Retrieve the task via http get.
////     * Verify expected updated values.
////     * <p>
////     * Be aware that passing the enum Status.COMPLETED will not
////     * evaluate as the same as the json response status.
////     *
////     * @throws Exception
////     */
////    @Test
////    public void updateTaskAsCompleteTest() throws Exception {
////        MvcResult goalResult = this.createGoal();
////        MockHttpServletResponse goalResponse = goalResult.getResponse();
////        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);
////
////        MvcResult taskResult = this.createTask(goalId);
////        MockHttpServletResponse taskResponse = taskResult.getResponse();
////        Integer taskId = this.retrieveUniqueIdFromHeader(taskResponse);
////        RequestBuilder requestBuilder = MockMvcRequestBuilders
////                .put(
////                        String.format("/to-do/task/%1d", taskId)
////                )
////                .accept(MediaType.APPLICATION_JSON)
////                .contentType(MediaType.APPLICATION_JSON);
////
////        mockMvc.perform(requestBuilder)
////                .andExpect(this.httpOkay);
////
////        RequestBuilder getRequestBuilder = MockMvcRequestBuilders
////                .get(
////                        String.format("/to-do/task/%1d", taskId)
////                )
////                .accept(MediaType.APPLICATION_JSON)
////                .contentType(MediaType.APPLICATION_JSON);
////        mockMvc.perform(getRequestBuilder)
////                .andExpect(
////                        jsonPath("$.status").value("COMPLETED")
////                );
////    }
////
////    @Test
////    public void deleteTaskWithSubTasksTest() throws Exception {
////        MvcResult goalResult = this.createGoal();
////        MockHttpServletResponse goalResponse = goalResult.getResponse();
////        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);
////
////        MvcResult parentTaskResult = this.createTask(goalId);
////        MockHttpServletResponse parentTaskResponse = parentTaskResult.getResponse();
////        Integer parentTaskId = this.retrieveUniqueIdFromHeader(parentTaskResponse);
////
////        this.createTask(parentTaskId);
////
////        MvcResult result = this.deleteTask(parentTaskId);
////        MockHttpServletResponse response = result.getResponse();
////
////        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
////    }
////
////    @Test
////    public void deleteTaskWithoutSubTasksTest() throws Exception {
////        MvcResult goalResult = this.createGoal();
////        MockHttpServletResponse goalResponse = goalResult.getResponse();
////        Integer goalId = this.retrieveUniqueIdFromHeader(goalResponse);
////
////        MvcResult taskResult = this.createTask(goalId);
////        MockHttpServletResponse taskResponse = taskResult.getResponse();
////        Integer taskId = this.retrieveUniqueIdFromHeader(taskResponse);
////
////        MvcResult result = this.deleteTask(taskId);
////        MockHttpServletResponse response = result.getResponse();
////
////        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
////    }
////}