package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModel;

import com.example.filmcameraphotobook.photo.Photo;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PreviewViewModel extends ViewModel {
    private static final String userId = "qguMQjYyh1k8w8p8zES0";

    private final FirebaseStorage cloudStorage = FirebaseStorage.getInstance();
    private Photo photo;

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Photo getPhoto() {
        return photo;
    }

    public StorageReference getPhotoRef() {
        return cloudStorage.getReference().child(userId + "/" + photo.getPictureRef());
    }
}
