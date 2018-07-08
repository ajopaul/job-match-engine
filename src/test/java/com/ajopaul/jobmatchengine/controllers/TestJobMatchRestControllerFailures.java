package com.ajopaul.jobmatchengine.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by ajopaul on 8/7/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(JobMatchRestController.class)
@TestPropertySource(locations="classpath:application-test.properties")
//@Ignore
public class TestJobMatchRestControllerFailures {

    @Autowired
    private JobMatchRestController jobMatchRestController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testWorkersListNotAvailable() {
        try {
            mockMvc.perform(get("/jobmatch/1"))
                    .andExpect(status().isServiceUnavailable())
                    .andExpect(jsonPath("$.message", is("Unable to read data source of [Workers/Jobs]")))
                    .andDo(print())
                    ;
            ;
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testJobsListNotAvailable() {
        try {
            mockMvc.perform(get("/jobmatch/1"))
                    .andExpect(status().isServiceUnavailable())
                    .andExpect(jsonPath("$.message", is("Unable to read data source of [Workers/Jobs]")))
                    .andDo(print())
            ;
        } catch (Exception e) {
            fail();
        }
    }
}
