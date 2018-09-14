package com.deloitte.yusp;

import org.junit.runner.Result;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationManager {
    public static Logger logger;

    public static ApplicationManager applicationManager;
    public ArrayList<Point> arrayListFromUser;
    public TSVReader tsvReader = null;
    public ClosestPointFinder closestPointFinder = null;

    public ApplicationManager() {

        logger = Logger.getLogger("ClosestPairLogger");
        Handler fileHandler;

        try {
            fileHandler = new FileHandler("logFile.log");
            logger.addHandler(fileHandler);

        } catch(IOException exception){
            logger.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }

    }

    public Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {

        applicationManager = new ApplicationManager();
        applicationManager.formArrayListFromDirectory();

        if (applicationManager.arrayListFormedFromDirectoryIsValid()) {
            applicationManager.findTheClosestPairInArrayList();
            applicationManager.tsvReader.writeToFile(applicationManager.closestPointFinder.toString());
        }
    }

    public static String resultStateToString(Result result) {

        if (result.wasSuccessful()) {
            return "Test was successful";
        }

        return "Test failed";

    }

    public void logArrayIsNullError() {
        logger.log(Level.ALL, "Array is null");
        logger.warning("Array is null");
    }

    public void logTSVFileDirectoryRequest() {
        logger.log(Level.ALL, "Enter tsv file directory: ");
        logger.info("Enter tsv file directory: ");
    }

    public void formArrayListFromDirectory() {
        tsvReader = new TSVReader(getTSVFileDirectoryFromUser());
        arrayListFromUser = tsvReader.tsvToArrayList();
    }

    public String getTSVFileDirectoryFromUser() {

        Scanner scan = new Scanner(System.in);
        logTSVFileDirectoryRequest();

        String tsvDir = scan.nextLine();
        logger.log(Level.ALL, "The file path is : " + tsvDir);

        return tsvDir;
    }

    public boolean arrayListFormedFromDirectoryIsValid() {
        if (arrayListFromUser == null) {
            logArrayIsNullError();
            return false;
        }
        return true;
    }

    public void findTheClosestPairInArrayList() {
        closestPointFinder = new ClosestPointFinder(arrayListFromUser);
        closestPointFinder.findClosestPoints();
    }
}
