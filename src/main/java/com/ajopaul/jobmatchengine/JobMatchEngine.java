package com.ajopaul.jobmatchengine;

import com.ajopaul.jobmatchengine.errorhandling.JobMatchException;
import com.ajopaul.jobmatchengine.model.Job;
import com.ajopaul.jobmatchengine.model.Worker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Created by ajopaul on 6/7/18.
 */
@Service
public class JobMatchEngine {

    /**
     * Return a list of jobs for a given worker id based on the suitability
     * @param workersUrl
     * @param jobsUrl
     * @param workerId
     * @return
     */
    public List<Job> getJobMatches(final String workersUrl, final String jobsUrl, final Integer workerId){
        final List<Worker> workerList;
        final List<Job> jobList;

        try {
            workerList = getWorkersList(workersUrl);
        }catch (Exception e){
            throw new JobMatchException(JobMatchException.ERROR_CODE.DEP_ERROR
                    ,"Unable to read data source of [Workers/Jobs]","");
        }

        final Worker worker = getWorker(workerId, workerList);

        try {
            jobList = getJobsList(jobsUrl);
        }catch(Exception e){
            throw new JobMatchException(JobMatchException.ERROR_CODE.DEP_ERROR
                    ,"Unable to read data source of [Workers/Jobs]","");
        }

       return filteredJobs(worker, jobList);
    }

    /*
        Filter the JobList based on the worker's match.

        1. Iterate trough jobs that meet the suitability criteria.
        2. Sort the matched jobs based on the bill rate $ amount, Highest 1st.
        3. Limit the list to max 3
     */
    private List<Job> filteredJobs(Worker worker, List<Job> jobList) {
        return jobList
                .stream()
                .filter(j -> isJobSuitable(worker, j))
                .sorted(Comparator.comparing(Job::getBillRateAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /*
        Get the worker based on worker id from workersList
        IF worker is Inactive, return with message Worker not active
     */
    private Worker getWorker(Integer workerId, List<Worker> workerList) {
        Worker worker;
        try {
            worker = workerList
                    .stream()
                    .filter(w -> w.getUserId() == workerId)
                    .findFirst()
                    .get();
        }catch(NoSuchElementException e){
            throw new JobMatchException(JobMatchException.ERROR_CODE.REQUEST_ERROR,"Worker Id not found","");
        }

        if(!worker.isIsActive()){
            throw new JobMatchException(JobMatchException.ERROR_CODE.WORKER_INACTIVE,"Worker not active","");
        }
        return worker;
    }

    /**
     * Match the job on the given order
     * WorkersRequired > Driver License > JobSkills > Certificates > Distance
     * @param worker
     * @param job
     * @return
     */
    private boolean isJobSuitable(Worker worker, Job job){
        boolean isMatch = checkWorkersRequired(job);

        isMatch = isMatch && checkDriversLicense(worker, job);

        isMatch = isMatch && checkSkills(worker, job);

        isMatch = isMatch && checkCertificates(worker, job);

        isMatch = isMatch && checkDistanceSuitable(worker, job);

        return isMatch;
    }

    /*
   Check if the job needs workers at the moment i.e >= 1
    */
    private boolean checkWorkersRequired(Job job) {
        return job.getWorkersRequired() >= 1;
    }

    /*
    Check if the worker meets the driving licence requirement
     */
    private boolean checkDriversLicense(Worker worker, Job job) {
        return !job.isDriverLicenseRequired() || worker.isHasDriversLicense();
    }

    /*
     Check if the worker has all the skill needed in the job desc
      */
    private boolean checkSkills(Worker worker, Job job) {
        return  worker.getSkills().contains(job.getJobTitle());
    }

    /*
   Check if the worker has all the certificates required in the job desc
    */
    private boolean checkCertificates(Worker worker, Job job) {
        return worker.getCertificates().containsAll(job.getRequiredCertificates());
    }

    /*
    Check if the distance to be travelled is within workers' max dist
     */
    private boolean checkDistanceSuitable(Worker worker, Job job) {
        int distance =(int) Utils.manualDistance(worker.getJobSearchAddress()
                        , job.getLocation()) ;

        return distance <= worker.getJobSearchAddress().getMaxJobDistance();
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
}
