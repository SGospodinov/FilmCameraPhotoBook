package com.example.filmcameraphotobook;

import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.camera.CamerasRepository;
import com.example.filmcameraphotobook.camera.UnknownCameraException;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CamerasRepositoryTest {
    private final Camera mockCamera = new Camera(
            "mock_camera",
            "Mock camera",
            "4,5.6",
            "125,250",
            2.00f,
            8.00f);

    private final Camera smena8m = new Camera(
            "smena_8m",
            "Smena 8M",
            "4,5.6,8,11,16",
            "B,15,30,60,125,250",
            1.00f,
            8.00f);

    @Before
    public void cleanCamerasRepository() {
        CamerasRepository.getInstance().clear();
    }

    @Test
    public void getInstanceReturnsRepository() {
        CamerasRepository repository = CamerasRepository.getInstance();
        assertNotNull(repository);
        assert(repository instanceof CamerasRepository);
    }

    @Test
    public void canAddCameras() throws UnknownCameraException {
        CamerasRepository repository = CamerasRepository.getInstance();
        repository.addCamera(mockCamera);
        assertEquals(repository.getCamerasCount(), 1);
        assertEquals(repository.getCamera("mock_camera"), mockCamera);
    }

    @Test(expected = UnknownCameraException.class)
    public void throwsExceptionIfTheCameraDoesNotExist() throws UnknownCameraException {
        CamerasRepository repository = CamerasRepository.getInstance();
        repository.getCamera("canon_eos_rebel");
    }

    @Test
    public void canGetAllCameras() {
        CamerasRepository repository = CamerasRepository.getInstance();
        repository.addCamera(mockCamera);
        repository.addCamera(smena8m);

        HashSet<Camera> expectedCamerasSet = new HashSet<Camera>();
        expectedCamerasSet.add(mockCamera);
        expectedCamerasSet.add(smena8m);

        assertEquals(expectedCamerasSet, repository.getAllCameras());
    }
}