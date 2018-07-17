package com.ajopaul.jobmatchengine.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"driverLicenseRequired",
"requiredCertificates",
"location",
"billRate",
"workersRequired",
"startDate",
"about",
"jobTitle",
"company",
"guid",
"jobId"
})
public class Job {

@JsonProperty("driverLicenseRequired")
private boolean driverLicenseRequired;
@JsonProperty("requiredCertificates")
private List<String> requiredCertificates = null;
@JsonProperty("location")
private Location location;
@JsonProperty("billRate")
private String billRate;
private double billRateAmount;
@JsonProperty("workersRequired")
private int workersRequired;
@JsonProperty("startDate")
private String startDate;
@JsonProperty("about")
private String about;
@JsonProperty("jobTitle")
private String jobTitle;
@JsonProperty("company")
private String company;
@JsonProperty("guid")
private String guid;
@JsonProperty("jobId")
private int jobId;

    public double getDistanceToMe() {
        return distanceToMe;
    }

    public void setDistanceToMe(double distanceToMe) {
        this.distanceToMe = distanceToMe;
    }

    private double distanceToMe;

@JsonProperty("driverLicenseRequired")
public boolean isDriverLicenseRequired() {
return driverLicenseRequired;
}

@JsonProperty("driverLicenseRequired")
public void setDriverLicenseRequired(boolean driverLicenseRequired) {
this.driverLicenseRequired = driverLicenseRequired;
}

@JsonProperty("requiredCertificates")
public List<String> getRequiredCertificates() {
return requiredCertificates;
}

@JsonProperty("requiredCertificates")
public void setRequiredCertificates(List<String> requiredCertificates) {
this.requiredCertificates = requiredCertificates;
}

@JsonProperty("location")
public Location getLocation() {
return location;
}

@JsonProperty("location")
public void setLocation(Location location) {
this.location = location;
}

@JsonProperty("billRate")
public String getBillRate() {
return billRate;
}



@JsonProperty("billRate")
public void setBillRate(String billRate) {
this.billRate = billRate;
    billRateAmount=Double.valueOf(billRate.replace("$",""));
}

@JsonProperty("workersRequired")
public int getWorkersRequired() {
return workersRequired;
}

@JsonProperty("workersRequired")
public void setWorkersRequired(int workersRequired) {
this.workersRequired = workersRequired;
}

@JsonProperty("startDate")
public String getStartDate() {
return startDate;
}

@JsonProperty("startDate")
public void setStartDate(String startDate) {
this.startDate = startDate;
}

@JsonProperty("about")
public String getAbout() {
return about;
}

@JsonProperty("about")
public void setAbout(String about) {
this.about = about;
}

@JsonProperty("jobTitle")
public String getJobTitle() {
return jobTitle;
}

@JsonProperty("jobTitle")
public void setJobTitle(String jobTitle) {
this.jobTitle = jobTitle;
}

@JsonProperty("company")
public String getCompany() {
return company;
}

@JsonProperty("company")
public void setCompany(String company) {
this.company = company;
}

@JsonProperty("guid")
public String getGuid() {
return guid;
}

@JsonProperty("guid")
public void setGuid(String guid) {
this.guid = guid;
}

@JsonProperty("jobId")
public int getJobId() {
return jobId;
}

@JsonProperty("jobId")
public void setJobId(int jobId) {
this.jobId = jobId;
}

public double getBillRateAmount() {
        return billRateAmount;
}

}