# job-match-engine

Demo Rest API that accepts a worker id and compares the given job list to perform a best match algorithm to return the 3 best suited jobs for the worker.

usage: /jobmatch/{workerId}

Assumptions made:
1. Worker List and Job List Json structure is assumed to be valid and final.
2. If worker 'isActive' flag is false no match is performed and api returns 'Worker not active' message.
3. The suitability of the match is performed in the follwoing order as follows
  * If the Job has 'workersRequired' value of at least 1.
  * If the Worker matches with the Licence, Skills and Certificates requirements.
  * If the distance between the worker and job location is within worker's max distance.
  * The final list is further limited to max 3 count ordered by highest bill rate first.
  

sample: http://13.238.92.18:8080/jobengine/jobmatch/8
