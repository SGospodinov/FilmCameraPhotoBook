package com.example.filmcameraphotobook.film;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

public class Film {
    private String id;
    private String name;
    private long iso;

    public Film() { }

    public Film(String id, String name, long iso) {
        this.id = id;
        this.name = name;
        this.iso = iso;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    @DocumentId
    public String getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setIso(long iso) {
        this.iso = iso;
    }

    public long getIso() {
        return iso;
    }

    private final static String TO_STRING_FORMAT = "%s (ISO %s)";

    public String toString() {
        return String.format(TO_STRING_FORMAT, name, iso);
    }
}
