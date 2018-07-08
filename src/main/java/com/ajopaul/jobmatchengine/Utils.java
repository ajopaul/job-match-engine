package com.ajopaul.jobmatchengine;

import com.ajopaul.jobmatchengine.model.Job;
import com.ajopaul.jobmatchengine.model.Worker;
import com.jayway.jsonpath.JsonPath;
import org.springframework.web.client.RestTemplate;

/**
 */
public class Utils {

    public static double manualDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                             * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return dist;
    }

    public static double googleMapsApiDistance(Worker worker, Job j, String key) {
        double distance;RestTemplate rest = new RestTemplate();
        String querySb = new StringBuilder()
                .append("https://maps.googleapis.com/maps/api/distancematrix/json?")
                .append("origins=")
                .append(worker.getJobSearchAddress().getLatitude())
                .append(",")
                .append(worker.getJobSearchAddress().getLongitude())
                .append("&destinations=")
                .append(j.getLocation().getLatitude())
                .append(",")
                .append(j.getLocation().getLongitude())
                .append("&mode=")
                .append(worker.getTransportation().equals("PUBLIC TRANSPORT") ? "transit" : "driving")
                .append("&key="+key)
                .toString();

        String response = rest.getForObject(querySb,String.class);
        distance  = (int) JsonPath.read(response, "$.rows[0].elements[0].distance.value") / 1000.0;
        return distance;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }




}
