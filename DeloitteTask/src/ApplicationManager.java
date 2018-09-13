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

    public ApplicationManager() {

        logger = Logger.getLogger(ApplicationManager.class.getName());

        Handler fileHandler  = null;

        //Assigning handlers to LOGGER object
        try {
            fileHandler = new FileHandler("./logFile.log");
            logger.addHandler(fileHandler);

        } catch(IOException exception){
            logger.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }

    }

    public Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        applicationManager = new ApplicationManager();

        applicationManager.logger.log(Level.ALL, "Enter tsv file directory: ");
        applicationManager.logger.info("Enter tsv file directory: ");

        String tsvDir = scan.nextLine();
        applicationManager.logger.log(Level.ALL, "The file path is : " + tsvDir);

        TSVReader tsvReader = new TSVReader(tsvDir);
        ArrayList<Point> pointsArrayList = tsvReader.tsvToArrayList();

        if (pointsArrayList == null) {
            applicationManager.logger.log(Level.ALL, "Array is null");
            applicationManager.logger.warning("Array is null");
            return;
        }

        ClosestPointFinder closestPointFinder = new ClosestPointFinder(pointsArrayList);
        closestPointFinder.findClosestPoints();
        tsvReader.writeToFile(closestPointFinder.toString());
    }

    public static String resultStateToString(Result result) {

        if (result.wasSuccessful()) {
            return "Test was successful";
        }

        return "Test failed";

    }
}
