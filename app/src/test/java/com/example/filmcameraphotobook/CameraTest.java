package com.example.filmcameraphotobook;

import com.example.filmcameraphotobook.camera.Camera;
import com.example.filmcameraphotobook.ui.main.UserPreferencesFragment;
import com.example.filmcameraphotobook.ui.main.UserPreferencesViewModel;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CameraTest {
    @Test
    public void constructorWorks() {
        Camera camera = new Camera(
                "smena_8m",
                "Smena 8M",
                "4,5.6,8,11,16",
                "B,15,30,60,125,250",
                1.00f,
                8.00f);

        HashSet<String> expectedApreturesSet = new HashSet<String>();
        expectedApreturesSet.add("4");
        expectedApreturesSet.add("5.6");
        expectedApreturesSet.add("8");
        expectedApreturesSet.add("11");
        expectedApreturesSet.add("16");

        HashSet<String> expectedShutterSpeedsSet = new HashSet<String>();
        expectedShutterSpeedsSet.add("B");
        expectedShutterSpeedsSet.add("15");
        expectedShutterSpeedsSet.add("30");
        expectedShutterSpeedsSet.add("60");
        expectedShutterSpeedsSet.add("125");
        expectedShutterSpeedsSet.add("250");

        assertEquals("smena_8m", camera.getID());
        assertEquals("Smena 8M", camera.getName());
        assertEquals(expectedApreturesSet, camera.getAvailableApertures());
        assertEquals(expectedShutterSpeedsSet, camera.getAvailableShutterSpeeds());
        assertEquals(1.00f, camera.getMinFocusDistance(), 0.01f);
        assertEquals(8.00f, camera.getMaxFocusDistance(), 0.01f);
    }
}