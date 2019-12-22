package main.webserver.exception;

/**
 * Created by aaron on 5/27/2018.
 */
public class ApiNotFoundException extends Exception {

    public ApiNotFoundException(String message) {
        super ( "Api Not Found : ".concat(message) );
        System.out.println( "Api Not Found : ".concat(message) );


    }
}
