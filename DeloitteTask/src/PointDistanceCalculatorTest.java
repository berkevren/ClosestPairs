import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PointDistanceCalculatorTest {

    @Test
    public void calculateDistance() {

        PointDistanceCalculator pointDistanceCalculator = new PointDistanceCalculator();
        ArrayList<Float> coordinatesForPoint1 = new ArrayList<Float>();
        ArrayList<Float> coordinatesForPoint2 = new ArrayList<Float>();

        coordinatesForPoint1.addAll(Arrays.asList(0f, 0f, 0f));
        coordinatesForPoint2.addAll(Arrays.asList(6f, 8f, 10f));

        Point point1 = new Point(coordinatesForPoint1, 0);
        Point point2 = new Point(coordinatesForPoint2, 0);

        pointDistanceCalculator.setPoint1(point1);
        pointDistanceCalculator.setPoint2(point2);

        assertEquals(Math.sqrt(200), pointDistanceCalculator.calculateDistance(), 0);

    }
}
