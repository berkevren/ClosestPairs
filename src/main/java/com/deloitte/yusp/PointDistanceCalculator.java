package com.deloitte.yusp;

import java.util.logging.Level;

public class PointDistanceCalculator {

    private Point point1, point2;
    private double distanceSquareSumTotal;

    public PointDistanceCalculator() {

        this.point1 = null;
        this.point2 = null;
        distanceSquareSumTotal = 0;

    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public Point getPoint2() {
        return point2;
    }

    public double calculateDistance() {

        distanceSquareSumTotal = 0;

        for (int i = 0; i < point1.getCoordinates().size(); i++) {

            distanceSquareSumTotal +=
                Math.pow((point1.getSingleCoordinate(i) - point2.getSingleCoordinate(i)),2);

        }

        return Math.sqrt(distanceSquareSumTotal);

    }

    public String toString() {

        String stringToReturn = "The distance between Point: " + point1.getOriginalIndex() + " and " +
                point2.getOriginalIndex() + " is " + this.calculateDistance();
        ApplicationManager.applicationManager.getLogger().log(Level.ALL, stringToReturn);
        return stringToReturn;

    }

}
