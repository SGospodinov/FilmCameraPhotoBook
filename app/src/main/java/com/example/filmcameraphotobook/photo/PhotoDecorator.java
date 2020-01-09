package com.example.filmcameraphotobook.photo;


import android.content.res.Resources;
import android.net.Uri;

import com.example.filmcameraphotobook.R;

import java.text.DateFormat;
import java.util.Locale;

public class PhotoDecorator {
    private static final String ZOOM_LEVEL = "15";

    private final Resources resources;
    private final Photo photo;

    public PhotoDecorator(Photo photo, Resources resources) {
        this.photo = photo;
        this.resources = resources;
    }

    public String getFormattedAperture() {
        return String.format("ƒ/%.1f", photo.getAperture());
    }

    public String getFormattedShutterSpeed() {
        if (!photo.getShutterSpeed().matches("(.*)\\d(.*)"))
            return photo.getShutterSpeed();
        if (photo.getShutterSpeed().contains("/"))
            return String.format("%sth second", photo.getShutterSpeed());
        return String.format("%s seconds", photo.getShutterSpeed());
    }

    public String getFormattedFocusDistance() {
        return String.format("%.2f m", photo.getFocusDistance());
    }

    public String getFormattedNote() {
        if (photo.getNote() == null || photo.getNote().isEmpty())
            return resources.getString(R.string.no_note);
        return photo.getNote();
    }

    public String getFormattedDate(DateFormat dateFormat) {
        return dateFormat.format(photo.getTimestamp());
    }

    public Uri getLocationUri() {
        return Uri.parse(
                String.format(Locale.ENGLISH,"geo:%f,%f?z=%s",
                photo.getLocation().getLatitude(),
                photo.getLocation().getLongitude(),
                ZOOM_LEVEL));
    }
}
