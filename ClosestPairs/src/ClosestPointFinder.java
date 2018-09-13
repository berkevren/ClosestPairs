import java.util.ArrayList;
import java.util.Collections;

public class ClosestPointFinder {

    private ArrayList<Point> initialPointArray;
    private int iteration;
    private PointDistanceCalculator pointDistanceCalculator;
    private double bestDistance = Double.POSITIVE_INFINITY;
    private Point bestPoint1, bestPoint2;


    public ClosestPointFinder(ArrayList<Point> initialPointArray) {

        this.initialPointArray = initialPointArray;
        this.iteration = 0;
        this.pointDistanceCalculator = new PointDistanceCalculator();
        this.bestPoint1 = null;
        this.bestPoint2 = null;
    }

    public Point getBestPoint1() {
        return bestPoint1;
    }

    public Point getBestPoint2() {
        return bestPoint2;
    }

    public void findClosestPoints() {

        if (initialPointArray.get(0).getCoordinates().size() < 3) {
            this.sortAccordingToIteration();
            findClosestPointsTwoDimensions(initialPointArray, initialPointArray, initialPointArray,
                    0, initialPointArray.size());
        }

        else {
            calculateClosestBruteForce(initialPointArray);
        }

        return;
        /*
        // in progress
        ArrayList<Point> initialPointArrayCopy1 = new ArrayList<Point>();
        ArrayList<Point> initialPointArrayCopy2 = new ArrayList<Point>();
        ArrayList<Point> initialPointArrayCopy3 = new ArrayList<Point>();

        double closestDistance = Double.POSITIVE_INFINITY;
        double closestSoFar = Double.POSITIVE_INFINITY;

        for (int i = 0; i < initialPointArray.size(); i++) {
            initialPointArrayCopy1.add(initialPointArray.get(i));
            initialPointArrayCopy2.add(initialPointArray.get(i));
            initialPointArrayCopy3.add(initialPointArray.get(i));
        }

        while (iteration < initialPointArray.get(0).getCoordinates().size()) {
            closestSoFar = findClosestPointsTwoDimensions(initialPointArrayCopy1, initialPointArrayCopy2, initialPointArrayCopy3,
                    0, initialPointArray.size());

            if (closestSoFar < closestDistance) {
                closestDistance = closestSoFar;
            }

            this.sortAccordingToIteration();

            initialPointArrayCopy1.clear();
            initialPointArrayCopy2.clear();
            initialPointArrayCopy3.clear();

            for (int i = 0; i < initialPointArray.size(); i++) {
                initialPointArrayCopy1.add(initialPointArray.get(i));
                initialPointArrayCopy2.add(initialPointArray.get(i));
                initialPointArrayCopy3.add(initialPointArray.get(i));
            }
        }
        */

    }

    public void sortAccordingToIteration() {

        for (int i = 0; i < initialPointArray.get(0).getCoordinates().size(); i++) {
            initialPointArray.get(i).setDimensionToSortAccordingTo(iteration);
        }
        Collections.sort(initialPointArray, initialPointArray.get(0));
        iteration++;
    }

    public double findClosestPointsTwoDimensions(ArrayList<Point> pointsSortedByFirstCoordinate,
                                    ArrayList<Point> getPointsSortedBySecondCoordinate,
                                    ArrayList<Point> auxillary,
                                    int lowEnd, int highEnd) {

        if (highEnd <= lowEnd) {
            return Double.POSITIVE_INFINITY;
        }

        int middle = lowEnd + (highEnd - lowEnd) / 2;
        Point medianPoint = pointsSortedByFirstCoordinate.get(middle);

        double closestPair1 = findClosestPointsTwoDimensions(pointsSortedByFirstCoordinate,
                getPointsSortedBySecondCoordinate, auxillary, lowEnd, middle);
        double closestPair2 = findClosestPointsTwoDimensions(pointsSortedByFirstCoordinate,
                getPointsSortedBySecondCoordinate, auxillary, middle+1, highEnd);
        double closestPairReal = Math.min(closestPair1, closestPair2);

        mergePointLists(getPointsSortedBySecondCoordinate, auxillary, lowEnd, middle, highEnd);

        int closerThanClosestPair = 0;
        for (int i = lowEnd; i < highEnd; i++) {
            if (Math.abs(getPointsSortedBySecondCoordinate.get(i).getSingleCoordinate(iteration-1)) -
                    medianPoint.getSingleCoordinate(iteration-1) < closestPairReal)
                auxillary.set(closerThanClosestPair++, getPointsSortedBySecondCoordinate.get(i));
        }

        for (int i = 0; i < closerThanClosestPair; i++) { // this runs at most 7 times

            for (int j = i+1; (j < closerThanClosestPair) &&
                    (auxillary.get(j).getSingleCoordinate(iteration) - auxillary.get(i).getSingleCoordinate(iteration)
                            < closestPairReal); j++) {

                pointDistanceCalculator.setPoint1(auxillary.get(i));
                pointDistanceCalculator.setPoint2(auxillary.get(j));
                double distanceBetweenThePoints = pointDistanceCalculator.calculateDistance();
                System.out.println(pointDistanceCalculator.toString());

                if (distanceBetweenThePoints < closestPairReal) {
                    closestPairReal = distanceBetweenThePoints;

                    if (distanceBetweenThePoints < bestDistance) {
                        bestDistance = distanceBetweenThePoints;
                        bestPoint1 = auxillary.get(i);
                        bestPoint2 = auxillary.get(j);
                    }
                }
            }

        }

        return closestPairReal;


    }

    public void mergePointLists(ArrayList<Point> pointsSortedBySecondCoordinate,
                                ArrayList<Point> auxillary, int lowEnd, int highEnd, int middle) {

        for (int i = lowEnd; i <= highEnd; i++) {
            auxillary.set(i, pointsSortedBySecondCoordinate.get(i));
        }

        int lowEndCopy = lowEnd, middleCopy = middle;

        for (int i = lowEnd; i < highEnd ; i++) {

            if (lowEndCopy > middle)                pointsSortedBySecondCoordinate.set(i, auxillary.get(middleCopy+1));
            else if (middleCopy+1 > highEnd)        pointsSortedBySecondCoordinate.set(i, auxillary.get(lowEndCopy));
            else if (auxillary.get(middleCopy+1).getCoordinates().get(0) >
                    auxillary.get(i).getCoordinates().get(0))
                                                    pointsSortedBySecondCoordinate.set(i, auxillary.get(middleCopy+1));
            else                                    pointsSortedBySecondCoordinate.set(i, auxillary.get(lowEndCopy));


        }
    }

    public String toString() {

        return ("best points: " + "\n" + bestPoint1.toString() +
                "\n" + bestPoint2.toString());

    }


    public void calculateClosestBruteForce(ArrayList<Point> points) {

        double closestDistanceSoFar;
        double closestDistance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < points.size(); i++) {
            pointDistanceCalculator.setPoint1(points.get(i));

            for (int j = i+1; j < points.size(); j++) {

                pointDistanceCalculator.setPoint2(points.get(j));
                closestDistanceSoFar = pointDistanceCalculator.calculateDistance();

                if (closestDistanceSoFar < closestDistance) {
                    closestDistance = closestDistanceSoFar;

                    bestPoint1 = points.get(i);
                    bestPoint2 = points.get(j);

                }

                //System.out.println(pointDistanceCalculator.toString());
            }
        }
    }
}
