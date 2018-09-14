package test;

import main.java.ApplicationManager;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.logging.Level;

public class TestRunner {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(TSVReaderTest.class);

        ApplicationManager applicationManager = new ApplicationManager();

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            applicationManager.logger.log(Level.ALL, failure.toString());
        }

        applicationManager.logger.log(Level.ALL, applicationManager.resultStateToString(result));
        applicationManager.getLogger().info(applicationManager.resultStateToString(result));
    }
}