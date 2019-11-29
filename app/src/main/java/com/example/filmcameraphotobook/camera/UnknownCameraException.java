package com.example.filmcameraphotobook.camera;

public class UnknownCameraException extends Exception {
    private String cameraName;

    UnknownCameraException(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getMessage() {
        return "Could not find camera called " + cameraName;
    }
}
