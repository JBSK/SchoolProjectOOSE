package nl.halewijn.persoonlijkheidstest.services;

import org.apache.log4j.Logger;

public class CustomLogger {

    private static final Logger LOGGER = Logger.getLogger(Logger.class);

    public Logger getLogger() {
        return LOGGER;
    }
}
