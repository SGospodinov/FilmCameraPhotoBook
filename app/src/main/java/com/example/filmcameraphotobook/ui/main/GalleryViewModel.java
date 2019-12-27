package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GalleryViewModel extends ViewModel {
    private static final String userId = "qguMQjYyh1k8w8p8zES0";

    private FirebaseFirestore databaseRef = FirebaseFirestore.getInstance();
    private CollectionReference photos = databaseRef.collection("users")
            .document(userId).collection("photos");


    public Query getPhotosDesc () {
        return photos.orderBy("timestamp", Query.Direction.DESCENDING);
    }

    public String getUserId() {
        return userId;
    }
}
