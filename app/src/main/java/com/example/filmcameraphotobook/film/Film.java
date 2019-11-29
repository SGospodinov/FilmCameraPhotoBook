package com.example.filmcameraphotobook.film;

public class Film {
    private final String id;
    private final String name;
    private final int iso;

    public Film(String id, String name, int iso) {
        this.id = id;
        this.name = name;
        this.iso = iso;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIso() {
        return iso;
    }

    private final static String TO_STRING_FORMAT = "%s (ISO %s)";

    public String toString() {
        return String.format(TO_STRING_FORMAT, name, iso);
    }
}
