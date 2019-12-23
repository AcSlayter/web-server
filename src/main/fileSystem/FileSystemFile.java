package main.fileSystem;



import main.fileSystem.exception.ReadFileException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by aaron on 4/29/2018.
 */
public class FileSystemFile {
    public static byte[] getFileSystemFile(String root, String requestURL) throws ReadFileException {
        if( requestURL.equals("index.html") || requestURL.equals("")){
            return readAllBytes(root.concat("/index.html"));
        }else if (requestURL.endsWith("/")) {
            return readAllBytes(root.concat(requestURL).concat("index.html"));
        } else {
             return readAllBytes(root.concat(requestURL).split("\\?")[0]);
        }
    }

    private static byte[] readAllBytes(String filePath) throws ReadFileException {
        try
        {
            return  Files.readAllBytes( Paths.get(filePath) );
        } catch (Exception e) {
            throw new ReadFileException("File not Available", e);
        }
    }

    public static void writeLogFile(String fileName, String print){
        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter(fileName, true); //true tells to append data.
            out = new BufferedWriter(fstream);
            out.write( print.concat("\n") );
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
