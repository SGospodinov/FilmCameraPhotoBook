package com.example.filmcameraphotobook.camera;

import java.util.HashSet;

public class Camera {
    private final String id;
    private final String name;
    private HashSet<String> availableShutterSpeeds;
    private HashSet<String> availableApertures;
    private final float minFocusDistance; // in meters
    private final float maxFocusDistance; // in meters

    public Camera(
            String id,
            String name,
            String availableApretures,
            String availableShutterSpeeds,
            float minFocusDistance,
            float maxFocusDistance) {
        this.id = id;
        this.name = name;
        this.minFocusDistance = minFocusDistance;
        this.maxFocusDistance = maxFocusDistance;
        this.availableApertures = parseCommaSeparatedString(availableApretures);
        this.availableShutterSpeeds = parseCommaSeparatedString(availableShutterSpeeds);
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashSet<String> getAvailableShutterSpeeds() {
        return availableShutterSpeeds;
    }

    public HashSet<String> getAvailableApertures() {
        return availableApertures;
    }

    public float getMinFocusDistance() {
        return minFocusDistance;
    }

    public float getMaxFocusDistance() {
        return maxFocusDistance;
    }

    private HashSet<String> parseCommaSeparatedString(String commaSeparatedString) {
        HashSet<String> result = new HashSet<String>();
        for (String value : commaSeparatedString.split(",")) {
            result.add(value);
        }
        return result;
    }
}
