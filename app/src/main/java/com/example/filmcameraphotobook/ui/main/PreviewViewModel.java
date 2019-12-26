package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModel;

import com.example.filmcameraphotobook.photo.Photo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PreviewViewModel extends ViewModel {
    private static final String userId = "qguMQjYyh1k8w8p8zES0";

    private final FirebaseStorage cloudStorage = FirebaseStorage.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Photo photo;

    private OnPhotoDeletedListener photoDeletedListener;

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void setPhotoDeletedListener(OnPhotoDeletedListener photoDeletedListener) {
        this.photoDeletedListener = photoDeletedListener;
    }

    public Photo getPhoto() {
        return photo;
    }

    public StorageReference getPhotoImageRef() {
        return cloudStorage.getReference().child(userId + "/" + photo.getPictureRef() + ".jpg");
    }

    public DocumentReference getPhotoRef() {
        return firestore.document("users/" + userId + "/photos/" + photo.getId());
    }

    public void deletePhoto() {
        getPhotoRef().delete().addOnCompleteListener(deletePhotoTask -> {
            if (deletePhotoTask.isSuccessful()) photoDeletedListener.onDeleted(photo);
        });
    }

    public interface OnPhotoDeletedListener {
        void onDeleted(Photo photo);
    }
}
