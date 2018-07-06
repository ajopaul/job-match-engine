package com.ajopaul.jobmatchengine.controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajopaul on 6/7/18.
 */
public class Worker {
 private int rating;
 private boolean isActive;
 List<String> certificates = new ArrayList<>();
 List<String> skills = new ArrayList< >();
 JobSearchAddress JobSearchAddressObject;
 private String transportation;
 private boolean hasDriversLicense;
 List<String> availability = new ArrayList<>();
 private String phone;
 private String email;
 Name NameObject;
 private int age;
 private String guid;
 private int userId;


 // Getter Methods

 public int getRating() {
  return rating;
 }

 public boolean getIsActive() {
  return isActive;
 }

 public JobSearchAddress getJobSearchAddress() {
  return JobSearchAddressObject;
 }

 public String getTransportation() {
  return transportation;
 }

 public boolean getHasDriversLicense() {
  return hasDriversLicense;
 }

 public String getPhone() {
  return phone;
 }

 public String getEmail() {
  return email;
 }

 public Name getName() {
  return NameObject;
 }

 public int getAge() {
  return age;
 }

 public String getGuid() {
  return guid;
 }

 public int getUserId() {
  return userId;
 }

 // Setter Methods

 public void setRating(int rating) {
  this.rating = rating;
 }

 public void setIsActive(boolean isActive) {
  this.isActive = isActive;
 }

 public void setJobSearchAddress(JobSearchAddress jobSearchAddressObject) {
  this.JobSearchAddressObject = jobSearchAddressObject;
 }

 public void setTransportation(String transportation) {
  this.transportation = transportation;
 }

 public void setHasDriversLicense(boolean hasDriversLicense) {
  this.hasDriversLicense = hasDriversLicense;
 }

 public void setPhone(String phone) {
  this.phone = phone;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public void setName(Name nameObject) {
  this.NameObject = nameObject;
 }

 public void setAge(int age) {
  this.age = age;
 }

 public void setGuid(String guid) {
  this.guid = guid;
 }

 public void setUserId(int userId) {
  this.userId = userId;
 }
}
class Name {
 private String last;
 private String first;


 // Getter Methods

 public String getLast() {
  return last;
 }

 public String getFirst() {
  return first;
 }

 // Setter Methods

 public void setLast(String last) {
  this.last = last;
 }

 public void setFirst(String first) {
  this.first = first;
 }
}
class JobSearchAddress {
 private String unit;
 private int maxJobDistance;
 private String longitude;
 private String latitude;


 // Getter Methods

 public String getUnit() {
  return unit;
 }

 public int getMaxJobDistance() {
  return maxJobDistance;
 }

 public String getLongitude() {
  return longitude;
 }

 public String getLatitude() {
  return latitude;
 }

 // Setter Methods

 public void setUnit(String unit) {
  this.unit = unit;
 }

 public void setMaxJobDistance(int maxJobDistance) {
  this.maxJobDistance = maxJobDistance;
 }

 public void setLongitude(String longitude) {
  this.longitude = longitude;
 }

 public void setLatitude(String latitude) {
  this.latitude = latitude;
 }
}