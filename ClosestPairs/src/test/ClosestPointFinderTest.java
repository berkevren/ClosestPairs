package test;

import main.java.ClosestPointFinder;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import main.java.Point;


public class ClosestPointFinderTest {

    @Test
    public void findClosestPoints() {

        TSVReaderTest tsvReaderTest = new TSVReaderTest();
        ArrayList<Point> points = tsvReaderTest.generateArrayListForTest();

        ClosestPointFinder closestPointFinder = new ClosestPointFinder(points);
        closestPointFinder.findClosestPoints();

        ArrayList<Float> actualCoordinates = new ArrayList<Float>();
        actualCoordinates.addAll(Arrays.asList(0f, 0f));
        int actualOriginalIndex = 0;
        Point actualPoint = new Point(actualCoordinates, actualOriginalIndex);

        assertTrue(actualPoint.equals(closestPointFinder.getBestPoint1()));
    }
}
