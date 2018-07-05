package com.ajopaul.jobmatchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ajopaul on 5/7/18.
 */

@RestController
public class JobMatchRestController {

    @GetMapping(value = "/jobmatch/{workerId}")
    public ResponseEntity<?> getJobMatch(@PathVariable(name = "workerId") String workerId){

        RestTemplate rest = new RestTemplate();
        Object[] array = rest.getForObject("http://test.swipejobs.com/api/jobs", Object[].class);

//        JsonObject jsonObject1 = JsonValue.EMPTY_JSON_OBJECT;
//        JsonObject jsonObject2 = JsonValue.EMPTY_JSON_OBJECT;
//        JsonObject jsonObject3 = JsonValue.EMPTY_JSON_OBJECT;
//
//        JsonArray jsonArray = JsonValue.EMPTY_JSON_ARRAY;
//        jsonArray.add(jsonObject1);
//        jsonArray.add(jsonObject2);
//        jsonArray.add(jsonObject3);

        return ResponseEntity.ok().body(array);
    }
}
