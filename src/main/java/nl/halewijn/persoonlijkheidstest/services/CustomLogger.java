package nl.halewijn.persoonlijkheidstest.services;

import org.apache.log4j.Logger;

public class CustomLogger {

    private static final Logger logger = Logger.getLogger(Logger.class);

    public void log(String message) {
        logger.info(message);
    }
}
