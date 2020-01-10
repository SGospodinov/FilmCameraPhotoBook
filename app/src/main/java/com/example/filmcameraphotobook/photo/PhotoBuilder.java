package com.example.filmcameraphotobook.photo;

import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.film.Film;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class PhotoBuilder {
    private Camera camera;
    private Film film;
    private Float aperture;
    private String shutterSpeed;
    private String pictureRef;
    private float focusDistance;
    private String note;
    private GeoPoint location = null;

    public Photo build() {
        Photo photo = new Photo();

        photo.setCameraName(camera.getName());
        photo.setFilmName(film.getName());
        photo.setAperture(aperture.floatValue());
        photo.setShutterSpeed(shutterSpeed);
        photo.setPictureRef(pictureRef);
        photo.setFocusDistance(focusDistance);
        photo.setNote(note);
        photo.setTimestamp(new Date());
        photo.setLocation(location);
        
        return photo;
    }

    public PhotoBuilder setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public PhotoBuilder setFilm(Film film) {
        this.film = film;
        return this;
    }

    public PhotoBuilder setAperture(Float aperture) {
        this.aperture = aperture;
        return this;
    }

    public PhotoBuilder setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
        return this;
    }

    public PhotoBuilder setPictureRef(String pictureRef) {
        this.pictureRef = pictureRef;
        return this;
    }

    public PhotoBuilder setNote(String note) {
        this.note = note;
        return this;
    }

    public PhotoBuilder setFocusDistance(float focusDistance) {
        this.focusDistance = focusDistance;
        return this;
    }

    public PhotoBuilder setLocation(double latitude, double longitude) {
        this.location = new GeoPoint(latitude, longitude);
        return this;
    }
}
