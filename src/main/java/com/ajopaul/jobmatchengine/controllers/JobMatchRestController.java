package com.ajopaul.jobmatchengine.controllers;

import com.ajopaul.jobmatchengine.Utils;
import com.ajopaul.jobmatchengine.model.Job;
import com.ajopaul.jobmatchengine.model.Worker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ajopaul on 5/7/18.
 */

@RestController
public class JobMatchRestController {

    @GetMapping(value = "/jobmatch/{workerId}",produces = "application/json")
    @ResponseBody
    public List<Job> getJobMatch(@PathVariable(name = "workerId") String workerId) throws InstantiationException, IllegalAccessException {


        List<Worker> workerList = getWorkersList("http://test.swipejobs.com/api/workers");

        List<Job> jobList = getJobsList("http://test.swipejobs.com/api/jobs");

        Worker worker = workerList
                            .stream()
                            .filter(w -> w.getUserId() == Integer.parseInt(workerId))
                            .findFirst()
                            .get();

        List<Job> matchedJobs = jobList
                                .stream()
                                .filter(j -> isJobSuitable(worker, j))
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

             isMatch = isMatch && (int)Utils.distance(j.getLocation().getLatitude(),j.getLocation().getLongitude()
                                            , worker.getJobSearchAddress().getLatitude(),worker.getJobSearchAddress().getLongitude()
                                            ,worker.getJobSearchAddress().getUnit()) <= worker.getJobSearchAddress()
                                            .getMaxJobDistance();
        return isMatch;
    }
}
