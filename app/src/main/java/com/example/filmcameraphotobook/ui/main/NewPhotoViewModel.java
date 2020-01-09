package com.example.filmcameraphotobook.ui.main;

import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.film.Film;
import com.example.filmcameraphotobook.photo.Photo;
import com.example.filmcameraphotobook.photo.PhotoBuilder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class NewPhotoViewModel extends ViewModel {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseStorage cloudStorage = FirebaseStorage.getInstance();
    private final FirebaseFirestore databaseRef = FirebaseFirestore.getInstance();

    private SharedPreferences sharedPreferences;
    private File imageFile;
    private PhotoBuilder photoBuilder = new PhotoBuilder();

    private OnPhotoSavedListener onPhotoSavedListener;

    private FusedLocationProviderClient locationProviderClient;

    public Task<QuerySnapshot> getCameras() {
        return databaseRef.collection("cameras").orderBy("name").get();
    }

    public Task<QuerySnapshot> getFilms() {
        return databaseRef.collection("films").orderBy("name").get();
    }

    public void setPreferences(SharedPreferences preferences) {
        sharedPreferences = preferences;
    }

    public void setLocationClient(FusedLocationProviderClient locationProviderClient) {
        this.locationProviderClient = locationProviderClient;
    }

    public void setImageFile(String imageAbsolutePath) {
        this.imageFile = new File(imageAbsolutePath);
    }

    public void setOnPhotoDeletedListener(OnPhotoSavedListener onPhotoSavedListener) {
        this.onPhotoSavedListener = onPhotoSavedListener;
    }

    public String getCameraPreference() {
        return sharedPreferences.getString("favorite_camera_preference", null);
    }

    public String getFilmPreference() {
        return sharedPreferences.getString("film_preference", null);
    }

    public boolean isLocationEnabled() {
        return sharedPreferences.getBoolean("location_preference", true);
    }

    public void disableLocationPreference() {
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putBoolean("location_preference", false);
        preferencesEditor.commit();
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setSelectedCamera(Camera camera) {
        photoBuilder.setCamera(camera);
    }

    public void setSelectedFilm(Film film) {
        photoBuilder.setFilm(film);
    }

    public void setSelectedAperture(Float aperture) {
        photoBuilder.setAperture(aperture);
    }

    public void setSelectedShutterSpeed(String shutterSpeed) {
        photoBuilder.setShutterSpeed(shutterSpeed);
    }

    public void setSelectedNote(String note) {
        photoBuilder.setNote(note);
    }

    public void setSelectedFocusDistance(float focusDistance) {
        photoBuilder.setFocusDistance(focusDistance);
    }

    public void setLocationAndSavePhoto() {
        locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                Location location = task.getResult();
                photoBuilder.setLocation(location.getLatitude(), location.getLongitude());
            }
            savePhoto();
        });
    }

    public String getCurrentUserUid() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    public void savePhoto() {
        String imageName = imageFile.getName();
        StorageReference imageReference = cloudStorage
                .getReference(getCurrentUserUid() + "/" + imageName + ".jpg");
        CollectionReference photosCollection = databaseRef
                .collection("users")
                .document(getCurrentUserUid())
                .collection("photos");

        Photo newPhoto = photoBuilder.setPictureRef(imageName).build();

        imageReference.putFile(Uri.fromFile(imageFile)).addOnCompleteListener(uploadTask -> {
            if (uploadTask.isSuccessful()) {
                photosCollection.add(newPhoto).addOnCompleteListener(savePhotoTask -> {
                    if (savePhotoTask.isSuccessful()) {
                        imageFile.delete();
                        newPhoto.setId(savePhotoTask.getResult().getId());
                        onPhotoSavedListener.onPhotoSaved(newPhoto);
                    }
                });
            }
        });
    }

    public interface OnPhotoSavedListener {
        void onPhotoSaved(Photo photo);
    }
}
