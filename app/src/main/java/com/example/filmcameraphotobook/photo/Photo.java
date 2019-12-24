package com.example.filmcameraphotobook.photo;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Photo implements Serializable {
    private String id;

    private String cameraId;
    private String filmId;

    private float aperture;
    private String shutterSpeed;
    private float focusDistance;
    private String note;
    private String pictureRef;

    private Date timestamp;

    @DocumentId
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public float getAperture() {
        return aperture;
    }

    public void setAperture(float aperture) {
        this.aperture = aperture;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public float getFocusDistance() {
        return focusDistance;
    }

    public void setFocusDistance(float focusDistance) {
        this.focusDistance = focusDistance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPictureRef() {
        return pictureRef;
    }

    public void setPictureRef(String pictureRef) {
        this.pictureRef = pictureRef;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
