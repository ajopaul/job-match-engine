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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    public void testMinArray() throws Exception{
        MvcResult result = mockMvc.perform(get("/jobmatch/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andReturn()
                ;
                //.andExpect(jsonPath("$", hasSize(3)));
        System.out.println(result.getResponse().getContentAsString());
    }
}
