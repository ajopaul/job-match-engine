package com.ajopaul.jobmatchengine.controllers;

import com.ajopaul.jobmatchengine.Utils;
import com.ajopaul.jobmatchengine.errorhandling.JobMatchException;
import com.ajopaul.jobmatchengine.model.Job;
import com.ajopaul.jobmatchengine.model.Worker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ajopaul on 5/7/18.
 */

@RestController
@RequestMapping("/api")
public class JobMatchRestController {

    @Value("${workers.url}")
    private String workersUrl;

    @Value("${jobs.url}")
    private String jobsUrl;

    @GetMapping(value = "/jobmatch/{workerId}",produces = "application/json")
    @ResponseBody
    public List<Job> getJobMatch(@PathVariable(name = "workerId") Integer workerId) throws JobMatchException{

        List<Worker> workerList = null;
        List<Job> jobList = null;
        try {
            workerList = getWorkersList(workersUrl);
            jobList = getJobsList(jobsUrl);
        }catch (Exception e){
            throw new JobMatchException(JobMatchException.ERROR_CODE.DEP_ERROR
                    ,"Unable to read data source of [Workers/Jobs]");
        }

        Worker worker;
        try {
            worker = workerList
                    .stream()
                    .filter(w -> w.getUserId() == workerId)
                    .findFirst()
                    .get();
        }catch(NoSuchElementException e){
            throw new JobMatchException(JobMatchException.ERROR_CODE.REQUEST_ERROR,"Worker Id not found");
        }

        if(!worker.isIsActive()){
            return Collections.EMPTY_LIST;
        }
        //Get matched jobs and sort them in order of billed rate, but limit to max 3 jobs.
        List<Job> matchedJobs = jobList
                                .stream()
                                .filter(j -> isJobSuitable(worker, j))
                                .sorted(Comparator.comparing(Job::getBillRateAmount).reversed())
                                .limit(3)
                                .collect(Collectors.toList());

        return matchedJobs;
    }

    /*
     Fetch the list of available jobs
     */
    private List<Job> getJobsList(String url) {
        RestTemplate rest = new RestTemplate();
        List<Job> jobList;
        ResponseEntity<List<Job>> jobResponse =
        rest.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Job>>() {
            });
        jobList = jobResponse.getBody();
        return jobList;
    }

    /*
    Get the list of workers
     */
    private List<Worker> getWorkersList(String url) {
        RestTemplate rest = new RestTemplate();
        List<Worker> workerList;
        ResponseEntity<List<Worker>> workResponse =
        rest.exchange(url,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Worker>>() {
            });
        workerList = workResponse.getBody();
        return workerList;
    }

    /**
     * Match the job on the given order
     *  Driver License > JobSkills > Certificates > Distance
     * @param worker
     * @param j
     * @return
     */
    private boolean isJobSuitable(Worker worker, Job j){
        boolean isMatch;
             isMatch = !j.isDriverLicenseRequired() || worker.isHasDriversLicense();

             isMatch = isMatch && worker.getSkills().contains(j.getJobTitle());

             isMatch = isMatch && worker.getCertificates().containsAll(j.getRequiredCertificates());

             isMatch = isMatch && (int)Utils.manualDistance(worker.getJobSearchAddress().getLatitude()
                                                ,worker.getJobSearchAddress().getLongitude(),j.getLocation().getLatitude()
                                                ,j.getLocation().getLongitude()) <= worker.getJobSearchAddress().getMaxJobDistance();
        return isMatch;
    }
}
