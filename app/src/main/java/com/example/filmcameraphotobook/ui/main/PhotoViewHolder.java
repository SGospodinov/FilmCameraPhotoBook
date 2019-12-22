package com.example.filmcameraphotobook.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.filmcameraphotobook.R;
import com.example.filmcameraphotobook.photo.Photo;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

class PhotoViewHolder extends RecyclerView.ViewHolder {
    private FirebaseStorage cloudStorage = FirebaseStorage.getInstance();

    private Context context;
    private ImageView thumbnail;
    private CircularProgressDrawable loadingSpinner;
    private NavController navController;

    public PhotoViewHolder(@NonNull View itemView, Context context, NavController navController) {
        super(itemView);

        this.context = context;
        this.navController = navController;

        loadingSpinner = new CircularProgressDrawable(context);
        loadingSpinner.setStyle(CircularProgressDrawable.LARGE);

        thumbnail = itemView.findViewById(R.id.photo_thumbnail);
    }

    public void bindPhoto(Photo photo, String userId) {
        StorageReference thumbnailRef = cloudStorage.getReference()
                .child(userId + "/" + photo.getPictureRef());

        GalleryFragmentDirections.PreviewPhoto action = GalleryFragmentDirections.previewPhoto(photo);
        itemView.setOnClickListener(view -> navController.navigate(action));

        loadingSpinner.start();
        Glide.with(context)
                .load(thumbnailRef)
                .placeholder(loadingSpinner)
                .into(thumbnail);
    }
}
