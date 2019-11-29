package com.example.filmcameraphotobook.film;

import java.util.HashMap;
import java.util.HashSet;

public class FilmsRepository {
    private static FilmsRepository instance = null;
    private HashMap<String, Film> films;

    private FilmsRepository() {
        films = new HashMap<String, Film>();
        Film ilfordHP5 = new Film("ilford_hp5", "Ilford HP5", 400);
        Film fujicolorC200 = new Film("fujicolor_c2000", "FujiColor C200", 200);
        films.put(ilfordHP5.getID(), ilfordHP5);
        films.put(fujicolorC200.getID(), fujicolorC200);
    }

    public static FilmsRepository getInstance() {
        if (instance == null) instance = new FilmsRepository();
        return instance;
    }

    public HashSet<Film> getAllFilms() {
        return new HashSet<Film>(films.values());
    }
}
