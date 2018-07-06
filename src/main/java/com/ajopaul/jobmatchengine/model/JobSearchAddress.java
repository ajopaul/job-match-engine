package com.ajopaul.jobmatchengine.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"unit",
"maxJobDistance",
"longitude",
"latitude"
})
public class JobSearchAddress {

@JsonProperty("unit")
private String unit;
@JsonProperty("maxJobDistance")
private int maxJobDistance;
@JsonProperty("longitude")
private double longitude;
@JsonProperty("latitude")
private double latitude;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("unit")
public String getUnit() {
return unit;
}

@JsonProperty("unit")
public void setUnit(String unit) {
this.unit = unit;
}

@JsonProperty("maxJobDistance")
public int getMaxJobDistance() {
return maxJobDistance;
}

@JsonProperty("maxJobDistance")
public void setMaxJobDistance(int maxJobDistance) {
this.maxJobDistance = maxJobDistance;
}

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

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}