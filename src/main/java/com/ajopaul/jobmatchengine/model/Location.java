package com.ajopaul.jobmatchengine.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"longitude",
"latitude"
})
public class Location {

@JsonProperty("longitude")
private double longitude;
@JsonProperty("latitude")
private double latitude;

@JsonProperty("longitude")
public double getLongitude() {
return longitude;
}

@JsonProperty("longitude")
public void setLongitude(double longitude) {
this.longitude = longitude;
}

@JsonProperty("latitude")
public double getLatitude() {
return latitude;
}

@JsonProperty("latitude")
public void setLatitude(double latitude) {
this.latitude = latitude;
}

}