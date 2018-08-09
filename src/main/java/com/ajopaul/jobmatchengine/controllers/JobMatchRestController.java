package com.ajopaul.jobmatchengine.controllers;

import com.ajopaul.jobmatchengine.JobMatchEngine;
import com.ajopaul.jobmatchengine.ResponseData;
import com.ajopaul.jobmatchengine.errorhandling.JobMatchException;
import com.ajopaul.jobmatchengine.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by ajopaul on 5/7/18.
 */

@RestController
@ComponentScan({"com.ajopaul.jobmatchengine"})
public class JobMatchRestController {

    @Value("${workers.url}")
    private String workersUrl;

    @Value("${jobs.url}")
    private String jobsUrl;

    @Autowired
    private JobMatchEngine jobMatchEngine;

    @GetMapping(value = "/jobmatch/{workerId}",produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getJobMatch(@PathVariable(name = "workerId") Integer workerId) throws JobMatchException{

        return ResponseEntity
            .ok()
            .body(Optional
                    .ofNullable(jobMatchEngine.getJobMatches(workersUrl, jobsUrl, workerId))
                        .filter(l -> !CollectionUtils.isEmpty(l))
                        .map(ResponseData::success)
                        .orElse(ResponseData.error(HttpStatus.OK,"No Matching Jobs found.")));
    }

}
