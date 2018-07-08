# job-match-engine

Demo Rest API that accepts a worker id and compares the given job list to perform a best match algorithm to return the 3 best suited jobs for the worker.

usage: /jobmatch/{workerId}

Assumptions made:
1. The Json structure of the Workers and Jobs list is not validated
2. If worker 'isActive' flag is valid no match is performed and returns 'Worker not active' message.
3. The suitability of the match is performed in the follwoing order as follows
  a. If the Job has 'workersRequired' value of at least 1.
  b. If the Worker matches with the Licence, Skills and Certificates requirements.
  c. If the distance between the worker and job location is within worker's max distance.
  d. The final list is further limited to max 3 count ordered by highest bill rate first.
  

sample: http://13.238.92.18:8080/jobengine/jobmatch/8
