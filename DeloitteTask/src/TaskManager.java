//package com.mkyong.csv;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.*;

public class TaskManager implements Comparator<Point2D.Float> {

    // closest pair of points and their Euclidean distance
    private Point2D best1, best2;
    private static int bestMultiPoint1, bestMultiPoint2;
    private double bestDistance = Double.POSITIVE_INFINITY;

    private static String fileName = "";

    static Logger lg;

    public TaskManager(Point2D[] points) {

        if (points == null) throw new IllegalArgumentException("constructor argument is null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("array element " + i + " is null");
        }

        int n = points.length;
        if (n <= 1) return;

        // sort by x-coordinate (breaking ties by y-coordinate)
        Point2D[] pointsByX = new Point2D[n];
        for (int i = 0; i < n; i++)
            pointsByX[i] = points[i];
        Arrays.sort(pointsByX, new Comparator<Point2D>() {
            @Override
            public int compare(Point2D o1, Point2D o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });

        // check for coincident points
        for (int i = 0; i < n-1; i++) {
            if (pointsByX[i].equals(pointsByX[i+1])) {
                bestDistance = 0.0;
                best1 = pointsByX[i];
                best2 = pointsByX[i+1];
                return;
            }
        }

        // sort by y-coordinate (but not yet sorted)
        Point2D[] pointsByY = new Point2D[n];
        for (int i = 0; i < n; i++)
            pointsByY[i] = pointsByX[i];

        // auxiliary array
        Point2D[] aux = new Point2D[n];

        closest(pointsByX, pointsByY, aux, 0, n-1);
    }

    // find closest pair of points in pointsByX[lo..hi]
    // precondition:  pointsByX[lo..hi] and pointsByY[lo..hi] are the same sequence of points
    // precondition:  pointsByX[lo..hi] sorted by x-coordinate
    // postcondition: pointsByY[lo..hi] sorted by y-coordinate
    private double closest(Point2D[] pointsByX, Point2D[] pointsByY, Point2D[] aux, int lo, int hi) {
        if (hi <= lo) return Double.POSITIVE_INFINITY;

        int mid = lo + (hi - lo) / 2;
        Point2D median = pointsByX[mid];

        // compute closest pair with both endpoints in left subarray or both in right subarray
        double delta1 = closest(pointsByX, pointsByY, aux, lo, mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid+1, hi);
        double delta = Math.min(delta1, delta2);

        // merge back so that pointsByY[lo..hi] are sorted by y-coordinate
        merge(pointsByY, aux, lo, mid, hi);

        // aux[0..m-1] = sequence of points closer than delta, sorted by y-coordinate
        int m = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pointsByY[i].getX() - median.getX()) < delta)
                aux[m++] = pointsByY[i];
        }

        // compare each point to its neighbors with y-coordinate closer than delta
        for (int i = 0; i < m; i++) {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i+1; (j < m) && (aux[j].getY() - aux[i].getY() < delta); j++) {
                double distance = aux[i].distance(aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < bestDistance) {
                        bestDistance = delta;
                        best1 = aux[i];
                        best2 = aux[j];
                        // StdOut.println("better distance = " + delta + " from " + best1 + " to " + best2);
                    }
                }
            }
        }
        return delta;
    }

    /**
     * Returns one of the points in the closest pair of points.
     *
     * @return one of the two points in the closest pair of points;
     *         {@code null} if no such point (because there are fewer than 2 points)
     */
    public Point2D either() {
        return best1;
    }

    /**
     * Returns the other point in the closest pair of points.
     *
     * @return the other point in the closest pair of points
     *         {@code null} if no such point (because there are fewer than 2 points)
     */
    public Point2D other() {
        return best2;
    }

    /**
     * Returns the Eucliden distance between the closest pair of points.
     *
     * @return the Euclidean distance between the closest pair of points
     *         {@code Double.POSITIVE_INFINITY} if no such pair of points
     *         exist (because there are fewer than 2 points)
     */
    public double distance() {
        return bestDistance;
    }

    // is v < w ?
    /*private static boolean less(Point2D v, Point2D w) {
        return v.compareTo(w) < 0;
    } */

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
    private static void merge(Point2D[] a, Point2D[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            //else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }
    }



    /**
     * Unit tests the {@code ClosestPair} data type.
     * Reads in an integer {@code n} and {@code n} points (specified by
     * their <em>x</em>- and <em>y</em>-coordinates) from standard input;
     * computes a closest pair of points; and prints the pair to standard
     * output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws IOException {

        //logger
        lg = Logger.getLogger(TaskManager.class.getName());

        Handler fileHandler  = null;

        //Assigning handlers to LOGGER object
        try {
            fileHandler = new FileHandler("./logFile.log");
            lg.addHandler(fileHandler);

        } catch(IOException exception){
        lg.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }



    Scanner sc = new Scanner(System.in);

        lg.log(Level.ALL, "Enter tsv file directory: ");
        lg.info("Enter tsv file directory: ");

        String tsvDir = sc.nextLine();
        lg.log(Level.ALL, "The file path is : " + tsvDir);

        File f = new File(tsvDir);
        fileName = f.getName();

        final Float[][] points = tsvToArray(tsvDir);

        if (points == null) {
            lg.log(Level.ALL, "Array is null");
            lg.warning("Array is null");
            return;
        }

        int arraySize = -1;

        for (int i = 0; i < 2000; i++) {
            if (points[i][0] == null) {
                arraySize = i;
                break;
            }
        }

        if (points[0].length == 2) {

            // create 2D point array from the data in the array
            Point2D[] points2D = new Point2D[arraySize];

            for (int i = 0; i < points2D.length; i++) {

                final int finalI = i;
                points2D[i] = new Point2D() {
                    @Override
                    public double getX() {
                        return points[finalI][0];
                    }

                    @Override
                    public double getY() {
                        return points[finalI][1];
                    }

                    @Override
                    public void setLocation(double x, double y) {

                    }
                };

            }

            TaskManager closest = new TaskManager(points2D);

            int row1 = -1;
            int row2 = -1;
            for (int i = 0; i < arraySize; i++) {
                if (points[i][0] == closest.either().getX() && points[i][1] == closest.either().getY()) {
                    row1 = i;
                }
                if (points[i][0] == closest.other().getX() && points[i][1] == closest.other().getY()) {
                    row2 = i;
                }
            }

            String result = ("best points: " + "\nRow " + (row1 + 1 ) + ": " + closest.either().getX() + ", " + closest.either().getY()
                    + "\nRow " + (row2 + 1) + ": " + closest.other().getX() + ", " + closest.other().getY());

            lg.log(Level.ALL, result);
            lg.info(result);

            try {
                writeToFile(result);

                lg.log(Level.FINE, "The results are saved in a file named: " + fileName + "_result");
                lg.info("The results are saved in a file named: " + fileName + "_result");

            } catch (IOException e) {

                lg.log(Level.SEVERE, "File could not be saved.");
                lg.info("File could not be saved.");
                lg.log(Level.SEVERE, String.valueOf(e.fillInStackTrace()));
            }

        }

        else {

            bruteForceClosest(points, arraySize);
        }
    }

    public static void writeToFile(String s)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "_result"));
        writer.write(s);

        writer.close();
    }

    public static Float[][] tsvToArray(String tsvFileAddress) {

        String tsvFile = tsvFileAddress;
        String line = "";
        String tsvSplitBy = "\t";

        Float[][] points = null;

        try (BufferedReader br = new BufferedReader(new FileReader(tsvFile))) {

            line = br.readLine();

            String[] pointsString = line.split(tsvSplitBy);
            points = new Float[2000][pointsString.length];

            // get the first element
            for (int j = 0; j < line.split("\t").length; j++) {
                points[0][j] = Float.valueOf(pointsString[j]);
            }

            int i = 1;

            while ((line = br.readLine()) != null) {

                pointsString = line.split(tsvSplitBy);

                for (int j = 0; j < line.split("\t").length; j++) {
                    points[i][j] = Float.valueOf(pointsString[j]);
                }

                i++;

            }

        } catch (IOException e) {
            lg.log(Level.SEVERE, String.valueOf(e.fillInStackTrace()));
        }


        return points;

    }

    @Override
    public int compare(Point2D.Float o1, Point2D.Float o2) {
        return Double.compare(o1.getX(), o2.getX());
    }

    /*
    Subtract B from A, element by element.
    Square all differences.
    Add all squares.
    Take the square root of the sum.
    */

    public static void bruteForceClosest(Float[][] arr, int arrayLength) {

        double distance = Double.POSITIVE_INFINITY;
        double dimension = arr[0].length;
        int dimensionInt = arr[0].length;

        Float[] differences = new Float[dimensionInt];
        double differenceSum = Double.POSITIVE_INFINITY;

        double sumRoot = Double.POSITIVE_INFINITY;

        for (int i = 0; i < arrayLength; i++) {

            for (int j = 0; j < arrayLength; j++) {

                if (i != j) {

                    differenceSum = 0;

                    for (int k = 0; k < dimension; k++) {

                        differences[k] = arr[i][k] - arr[j][k];
                        differences[k] = differences[k] * differences[k];
                    }

                    for (int k = 0; k < dimension; k++) {
                        differenceSum += differences[k];
                    }

                    sumRoot = Math.sqrt(differenceSum);

                    if (sumRoot < distance) {
                        distance = sumRoot;

                        bestMultiPoint1 = i;
                        bestMultiPoint2 = j;
                    }
                }
            }

        }

        String firstPoint = "";
        String secondPoint = "";

        for (int i = 0; i < dimensionInt; i++) {

            firstPoint += arr[bestMultiPoint1][i] + ", ";
            secondPoint += arr[bestMultiPoint2][i] + ", ";

        }

        String result = ("best points: " + "\nRow " + (bestMultiPoint1 + 1) + ": " + firstPoint +
                "\nRow " + (bestMultiPoint2 + 1) + ": " + secondPoint);

        lg.log(Level.FINE, result);
        lg.info(result);

        try {
            writeToFile(result);

            lg.log(Level.FINE, "The results are saved in a file named: " + fileName + "_result");
            lg.info("The results are saved in a file named: " + fileName + "_result");

        } catch (IOException e) {

            lg.log(Level.SEVERE, "File could not be saved.");
            lg.info("File could not be saved.");

            lg.log(Level.SEVERE, String.valueOf(e.fillInStackTrace()));
        }
    }

}