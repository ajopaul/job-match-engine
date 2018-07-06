package com.ajopaul.jobmatchengine.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by ajopaul on 5/7/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(JobMatchRestController.class)
public class TestJobMatchRestController {

    @Autowired
    private JobMatchRestController jobMatchRestController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testApiReturnsOk() throws Exception {
        mockMvc.perform(get("/jobmatch/"+1))
                .andExpect(status().isOk());
    }

    @Test
    public void testIsReturnTypeJson() throws Exception {
        mockMvc.perform(get("/jobmatch/"+1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void testValidJob() throws Exception{
        MvcResult result = mockMvc.perform(get("/jobmatch/" + 4))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.length()", lessThan(4)))
                .andReturn()
                ;


        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testNoJobFound() throws Exception{
        MvcResult result = mockMvc.perform(get("/jobmatch/" + 6))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.length()", is(0)))
                .andReturn()
                ;


        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testTwoJobs() throws Exception{
        MvcResult result = mockMvc.perform(get("/jobmatch/" + 14))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.length()", is(2)))
                .andReturn()
                ;


        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testJobs() throws Exception{
        MvcResult result = mockMvc.perform(get("/jobmatch/" + 19))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.length()", greaterThan(1)))
                .andReturn()
                ;


        System.out.println(result.getResponse().getContentAsString());
    }
}
