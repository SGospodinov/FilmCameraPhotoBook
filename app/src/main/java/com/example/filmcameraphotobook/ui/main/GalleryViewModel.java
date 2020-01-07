package com.example.filmcameraphotobook.ui.main;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GalleryViewModel extends ViewModel {
    private static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore databaseRef = FirebaseFirestore.getInstance();

    public Query getPhotosDesc () {
        return databaseRef
                .collection("users")
                .document(getCurrentUser().getUid())
                .collection("photos")
                .orderBy("timestamp", Query.Direction.DESCENDING);
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}
