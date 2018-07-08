package com.ajopaul.jobmatchengine.controllers;

import com.jayway.jsonpath.JsonPath;
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
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    public void setup()  {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testInvalidPath(){
        try {
            mockMvc.perform(get("/api/jobmatch1/" + 1))
                    .andExpect(status().isNotFound())
                    ;
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testMissingWorkerId() {
        try {
            mockMvc.perform(get("/api/jobmatch/"))
                .andExpect(status().isNotFound())
                .andDo(print())
                    ;
            ;
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testWrongRequestType() {
        try{
            mockMvc.perform(post("/api/jobmatch/1"))
                    .andDo(print())
            ;
        }catch (Exception e){
            fail();
        }
    }

    @Test
    public void testWrongpath() {
        try {
            mockMvc.perform(get("/api/jobmatch/abc/1"))
                    .andExpect(status().isNotFound())
                    .andDo(print())
            ;
            ;
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testInvalidWorkerId() {
        try {
            mockMvc.perform(get("/api/jobmatch/null"))
                    .andDo(print())
            ;
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testWorkerNotFound(){
        try {
            mockMvc.perform(get("/api/jobmatch/" + -1))
                    .andDo(print())
            ;
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testApiReturnsOk()  {
        try {
            mockMvc.perform(get("/api/jobmatch/"+1))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testIsReturnTypeJson()  {
        try {
            mockMvc.perform(get("/api/jobmatch/"+1))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testValidJob() {
        try {
            mockMvc.perform(get("/api/jobmatch/" + 4))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.length()", lessThan(4)))
                    .andDo(print())
                    ;
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testNoJobFound() {
        try {
            mockMvc.perform(get("/api/jobmatch/" + 6))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$", hasSize(0)))
                    .andDo(print())
                    ;
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testThreeJobs() {
        try {
            mockMvc.perform(get("/api/jobmatch/" + 30))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andDo(print())
                    ;
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testJobs() {
        try {
            mockMvc.perform(get("/api/jobmatch/" + 30))
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(contentType))
                       .andExpect(jsonPath("$.length()", greaterThan(1)))
                       .andDo(print())
                       ;
        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testMax3Jobs() {
        try {
            mockMvc.perform(get("/api/jobmatch/" + 8))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andDo(print())
                   // .andExpect(jsonPath("$.length()", lessThanOrEqualTo(3)))
                    ;
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testInActiveWorker() {
        try{
            mockMvc.perform(get("/api/jobmatch/"+1))
                    .andDo(print())
                    ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //@Test
//    public void testWithGoogleMapsKey()  {
//
//        MvcResult result = mockMvc.perform(get("/jobmatch/" + 16 + "?key=AIzaSyDOE0kpj************"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andReturn()
//                ;
//        System.out.println(result.getResponse().getContentAsString());
//    }





    @Test
    public void printAllMatches() throws Exception {
        for(int i=1;i<=49;i++){
            MvcResult result = mockMvc.perform(get("/api/jobmatch/" + i))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andReturn()
                    //.andDo(print())
                    ;
            String response = result.getResponse().getContentAsString();
            Integer matchSize = JsonPath.read(response, "$.length()");
            System.out.println("Worker Id "+ i+", match size "+matchSize);
        }
    }

}
