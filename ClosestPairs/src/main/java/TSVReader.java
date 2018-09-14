package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class TSVReader {

    private String tsvFileAddress, nextLine, tsvSplitBy, fileName;
    private ArrayList<Point> allPointsInTSVFile;
    private File tsvFile;

    public TSVReader(String tsvFileAddress) {

        this.tsvFileAddress = tsvFileAddress;
        this.tsvFile = new File(tsvFileAddress);
        this.fileName = tsvFile.getName();
        this.nextLine = "";
        this.tsvSplitBy = "\t";
        this.allPointsInTSVFile = new ArrayList<Point>();

    }
    public ArrayList<Point> tsvToArrayList() {

        if (!(this.containsAtLeastOneLine()))
            return null;

        try (BufferedReader br = new BufferedReader(new FileReader(tsvFileAddress))) {

            String[] pointsStringByLine;
            ArrayList<Float> pointsInSingleLineAsFloat;
            int originalIndex = 0;

            while ((nextLine = br.readLine()) != null) {

                pointsStringByLine = nextLine.split(tsvSplitBy);
                pointsInSingleLineAsFloat = new ArrayList<Float>();

                for (int j = 0; j < pointsStringByLine.length; j++) {
                    pointsInSingleLineAsFloat.add(Float.valueOf(pointsStringByLine[j]));
                }

                allPointsInTSVFile.add(new Point(pointsInSingleLineAsFloat, originalIndex));
                originalIndex++;

            }

        } catch (IOException e) {
            ApplicationManager.applicationManager.logger.log(Level.SEVERE, String.valueOf(e.fillInStackTrace()));
        }

        return allPointsInTSVFile;

    }

    public boolean containsAtLeastOneLine() {

        try (BufferedReader br = new BufferedReader(new FileReader(tsvFileAddress))) {

            String firstLine = "";
            nextLine = br.readLine();
            firstLine = nextLine.split(tsvSplitBy)[0];

            if (firstLine == "") {
                ApplicationManager.applicationManager.logger.log(Level.SEVERE, "File is empty");
                return false;
            }
            return true;

        } catch (IOException e) {
        ApplicationManager.applicationManager.logger.log(Level.SEVERE, String.valueOf(e.fillInStackTrace()));
    }

    return false;

    }

    public void writeToFile(String result) {

        ApplicationManager.logger.log(Level.FINE, result);
        ApplicationManager.logger.info(result);

        try {
            writeResultToFile(result);

            ApplicationManager.logger.log(Level.FINE, "The results are saved in a file named: " + fileName + "_result");
            ApplicationManager.logger.info("The results are saved in a file named: " + fileName + "_result");

        } catch (IOException e) {

            ApplicationManager.logger.log(Level.SEVERE, "File could not be saved.");
            ApplicationManager.logger.info("File could not be saved.");

            ApplicationManager.logger.log(Level.SEVERE, String.valueOf(e.fillInStackTrace()));
        }
    }

    public void writeResultToFile(String result) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "_result"));
        writer.write(result);
        writer.close();

    }
}
