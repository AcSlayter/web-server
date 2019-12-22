package main.fileSystem.exception;

/**
 * Created by aaron on 5/28/2018.
 */
public class ReadFileException extends Exception {

    public ReadFileException(String s, Exception e) {
        super(s,e);
    }
}
