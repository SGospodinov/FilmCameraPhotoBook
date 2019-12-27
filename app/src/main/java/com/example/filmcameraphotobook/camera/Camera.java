package com.example.filmcameraphotobook.camera;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class Camera {
    private String id;
    private String name;
    private ArrayList<String> availableShutterSpeeds;
    private ArrayList<Float> availableApertures;
    private Double minFocusDistance; // in meters
    private Double maxFocusDistance; // in meters

    public Camera() {}

    public Camera(
            String id,
            String name,
            ArrayList<Float> availableApertures,
            ArrayList<String> availableShutterSpeeds,
            Double minFocusDistance,
            Double maxFocusDistance) {
        this.id = id;
        this.name = name;
        this.minFocusDistance = minFocusDistance;
        this.maxFocusDistance = maxFocusDistance;
        this.availableApertures = availableApertures;
        this.availableShutterSpeeds = availableShutterSpeeds;
    }

    @DocumentId
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getAvailableShutterSpeeds() {
        return availableShutterSpeeds;
    }

    public void setAvailableShutterSpeeds(ArrayList<String> availableShutterSpeeds) {
        this.availableShutterSpeeds = availableShutterSpeeds;
    }

    public ArrayList<Float> getAvailableApertures() {
        return availableApertures;
    }

    public void setAvailableApertures(ArrayList<Float> availableApertures) {
        this.availableApertures = availableApertures;
    }

    public Double getMinFocusDistance() {
        return minFocusDistance;
    }

    public void setMinFocusDistance(Double minFocusDistance) {
        this.minFocusDistance = minFocusDistance;
    }

    public Double getMaxFocusDistance() {
        return maxFocusDistance;
    }

    public void setMaxFocusDistance(Double maxFocusDistance) {
        this.maxFocusDistance = maxFocusDistance;
    }

    @Override
    public String toString() {
        return name;
    }
}
