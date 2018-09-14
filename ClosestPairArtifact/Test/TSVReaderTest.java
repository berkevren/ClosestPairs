import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TSVReaderTest {

    @org.junit.Test
    public void tsvToArrayList() {

        String tsvFileAddressForTest = "/Users/berkabbasoglu/Documents/Programs/ClosestPairs/" +
                "DeloitteTask/sample_in_out/sample_input_2_4.tsv";
        TSVReader tsvReader = new TSVReader(tsvFileAddressForTest);
        ArrayList<Point> pointsArrayList = tsvReader.tsvToArrayList();

        for (Point point: pointsArrayList) {
            assertTrue(point.equals(generateArrayListForTest().get(point.getOriginalIndex())));
        }


    }

    @Test
    public void testEmptyFileAsInput() {

        String tsvFileAddressForTest = "/Users/berkabbasoglu/Documents/Programs/ClosestPairs/" +
                "DeloitteTask/sample_in_out/empty.tsv";
        TSVReader tsvReader = new TSVReader(tsvFileAddressForTest);
        ArrayList<Point> pointsArrayList = tsvReader.tsvToArrayList();

        for (Point point: pointsArrayList) {
            assertTrue(point.equals(generateArrayListForTest().get(point.getOriginalIndex())));
        }

    }

    public ArrayList<Point> generateArrayListForTest() {

        ArrayList<Point> pointsArrayList = new ArrayList<Point>();
        ArrayList<Float> coordinatesArrayList = new ArrayList<Float>();
        int originalIndex = 0;

        coordinatesArrayList.addAll(Arrays.asList(Float.valueOf("0"), Float.valueOf("0")));
        Point point = new Point(coordinatesArrayList, originalIndex);
        pointsArrayList.add(point);
        originalIndex++;

        coordinatesArrayList = new ArrayList<Float>();
        coordinatesArrayList.addAll(Arrays.asList(Float.valueOf("3"), Float.valueOf("4")));
        point = new Point(coordinatesArrayList, originalIndex);
        pointsArrayList.add(point);
        originalIndex++;

        coordinatesArrayList = new ArrayList<Float>();
        coordinatesArrayList.addAll(Arrays.asList(Float.valueOf("12"), Float.valueOf("13")));
        point = new Point(coordinatesArrayList, originalIndex);
        pointsArrayList.add(point);
        originalIndex++;

        coordinatesArrayList = new ArrayList<Float>();
        coordinatesArrayList.addAll(Arrays.asList(Float.valueOf("6"), Float.valueOf("8")));
        point = new Point(coordinatesArrayList, originalIndex);
        pointsArrayList.add(point);

        return pointsArrayList;

    }

    @org.junit.Test
    public void writeResultToFile() {
    }
}
