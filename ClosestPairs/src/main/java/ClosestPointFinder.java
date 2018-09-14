package main.java;

import java.util.ArrayList;
import java.util.Collections;

public class ClosestPointFinder {

    private ArrayList<Point> initialPointArray;
    private int iteration;
    private PointDistanceCalculator pointDistanceCalculator;
    private double closestDistance = Double.POSITIVE_INFINITY;
    private double closestDistanceSoFar;
    private Point closestPair1, closestPair2;


    public ClosestPointFinder(ArrayList<Point> initialPointArray) {

        this.initialPointArray = initialPointArray;
        this.iteration = 0;
        this.pointDistanceCalculator = new PointDistanceCalculator();
        this.closestPair1 = null;
        this.closestPair2 = null;
    }

    public Point getBestPoint1() {
        return closestPair1;
    }

    public Point getBestPoint2() {
        return closestPair2;
    }

    public void findClosestPoints() {

        if (initialPointArray.get(0).getCoordinates().size() < 3) {
            this.sortAccordingToIteration();
            findClosestPointsWithinHalvesTwoDimensions(initialPointArray, initialPointArray, initialPointArray,
                    0, initialPointArray.size());
        }

        else {
            calculateClosestBruteForce();
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

    public double findClosestPointsWithinHalvesTwoDimensions(ArrayList<Point> pointsSortedByFirstCoordinate,
                                    ArrayList<Point> getPointsSortedBySecondCoordinate,
                                    ArrayList<Point> auxiliary,
                                    int lowEnd, int highEnd) {

        if (highEnd <= lowEnd) {
            return Double.POSITIVE_INFINITY;
        }

        int middle = lowEnd + (highEnd - lowEnd) / 2;
        Point medianPoint = pointsSortedByFirstCoordinate.get(middle);

        double closestDistanceFromFirstHalf = findClosestPointsWithinHalvesTwoDimensions(pointsSortedByFirstCoordinate,
                getPointsSortedBySecondCoordinate, auxiliary, lowEnd, middle);
        double closestDistanceFromSecondHalf = findClosestPointsWithinHalvesTwoDimensions(pointsSortedByFirstCoordinate,
                getPointsSortedBySecondCoordinate, auxiliary, middle+1, highEnd);
        double closestPairReal = Math.min(closestDistanceFromFirstHalf, closestDistanceFromSecondHalf);

        mergePointLists(getPointsSortedBySecondCoordinate, auxiliary, lowEnd, middle, highEnd);

        return findClosestPointsBetweenHalvesTwoDimensions(getPointsSortedBySecondCoordinate, auxiliary, lowEnd, highEnd,
                closestPairReal, medianPoint);
    }

    public double findClosestPointsBetweenHalvesTwoDimensions(ArrayList<Point> getPointsSortedBySecondCoordinate,
                                                              ArrayList<Point> auxiliary, int lowEnd, int highEnd,
                                                              double closestPairReal, Point medianPoint) {

        int closerThanClosestPair = 0;
        for (int i = lowEnd; i < highEnd; i++) {
            if (Math.abs(getPointsSortedBySecondCoordinate.get(i).getSingleCoordinate(iteration-1)) -
                    medianPoint.getSingleCoordinate(iteration-1) < closestPairReal)
                auxiliary.set(closerThanClosestPair++, getPointsSortedBySecondCoordinate.get(i));
        }

        for (int i = 0; i < closerThanClosestPair; i++) { // this runs at most 7 times

            for (int j = i+1; (j < closerThanClosestPair) &&
                    (auxiliary.get(j).getSingleCoordinate(iteration) - auxiliary.get(i).getSingleCoordinate(iteration)
                            < closestPairReal); j++) {

                pointDistanceCalculator.setPoint1(auxiliary.get(i));
                pointDistanceCalculator.setPoint2(auxiliary.get(j));
                double distanceBetweenThePoints = pointDistanceCalculator.calculateDistance();

                if (distanceBetweenThePoints < closestPairReal) {
                    closestPairReal = distanceBetweenThePoints;

                    if (distanceBetweenThePoints < closestDistance) {
                        closestDistance = distanceBetweenThePoints;
                        closestPair1 = auxiliary.get(i);
                        closestPair2 = auxiliary.get(j);
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
        return ("best points: " + "\n" + closestPair1.toString() +
                "\n" + closestPair2.toString());
    }


    public void calculateClosestBruteForce() {

        for (int i = 0; i < initialPointArray.size(); i++) {
            pointDistanceCalculator.setPoint1(initialPointArray.get(i));

            for (int j = i+1; j < initialPointArray.size(); j++) {

                pointDistanceCalculator.setPoint2(initialPointArray.get(j));
                closestDistanceSoFar = pointDistanceCalculator.calculateDistance();

                updateClosestPairs(i, j);
            }
        }
    }

    public void updateClosestPairs(int closestPairCandidate1, int closestPairCadidate2) {
        if (closestDistanceSoFar < closestDistance) {
            closestDistance = closestDistanceSoFar;
            closestPair1 = initialPointArray.get(closestPairCandidate1);
            closestPair2 = initialPointArray.get(closestPairCadidate2);
        }
    }
}
