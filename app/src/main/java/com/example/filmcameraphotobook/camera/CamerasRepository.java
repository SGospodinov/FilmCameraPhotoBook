package com.example.filmcameraphotobook.camera;

import java.util.HashMap;
import java.util.HashSet;

public class CamerasRepository {
    private static CamerasRepository instance = null;
    private HashMap<String, Camera> cameras;

    private CamerasRepository() {
        this.cameras = new HashMap<String, Camera>();
        Camera smena8m = new Camera("smena_8m",
                "Smena 8M",
                "4,5.6,8,11,16",
                "B,15,30,60,125,250",
                1.00f,
                8.00f);
        Camera olympusTrip35 = new Camera("olympus_trip_35",
                "Olympus Trip 35",
                "2.8,4,5.6,8,11,16,22",
                "40,200",
                1.00f,
                3.00f);

        cameras.put(smena8m.getID(), smena8m);
        cameras.put(olympusTrip35.getID(), olympusTrip35);
    }

    public static CamerasRepository getInstance() {
        if (instance == null) instance = new CamerasRepository();
        return instance;
    }

    public void addCamera(Camera camera) {
        cameras.put(camera.getID(), camera);
    }

    public Camera getCamera(String cameraID) throws UnknownCameraException {
        Camera camera = cameras.get(cameraID);
        if (camera == null) throw new UnknownCameraException(cameraID);
        return camera;
    }

    public HashSet<Camera> getAllCameras() {
        return new HashSet<Camera>(cameras.values());
    }

    public int getCamerasCount() {
        return cameras.size();
    }

    public void clear() {
        cameras.clear();
    }
}
