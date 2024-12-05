package server.com.inventory.util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {
    private static final Logger logger = Logger.getLogger("InventoryLogger");

    static {
        try {
            // Set up the file handler
            Handler fileHandler = new FileHandler("inventory_operations.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            // Set logging level
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize logging handler: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
