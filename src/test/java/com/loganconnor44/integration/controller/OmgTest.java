//package com.loganconnor44.integration.controller;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class OmgTest extends ThisIsATest {
//
//    @Test
//    public void whenUserControllerInjected_thenNotNull() throws Exception {
//        assertThat(goalController).isNotNull();
//    }
//
//    /**
//     //     * Create and save a goal then verify the response status.
//     //     *
//     //     * @throws Exception
//     //     */
//    @Test
//    public void addGoalTest() throws Exception {
//        MvcResult result = this.createGoal();
//        MockHttpServletResponse response = result.getResponse();
//        this.retrieveUniqueIdFromHeader(response);
//        //Verify request succeed
//        Assert.assertEquals(
//                201,
//                response.getStatus()
//        );
//    }
//
//}
