package main.webserver.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by aaron on 5/27/2018.
 */
public class ApiNotFoundException extends Exception {
    static final Logger LOGGER = LogManager.getLogger(ApiNotFoundException.class.getName());
    public ApiNotFoundException(String message) {
        super ( "Api Not Found : ".concat(message) );
        LOGGER.error(String.format("Api_Not_Found=".concat(message) ));


    }
}
