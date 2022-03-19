import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Object.*;
import java.nio.Buffer;
import java.util.Scanner;

/*
Name: DataDumper
Authors:
Description: Dumps data from comma seperated files into
             the database
 */

public class DataDumper
{
    static private String[] Genres;
    static private String[] Studios;
    static private String[] Actors;
    static void MovieTransfer()
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:/DB_App/rotten_tomatoes_top_movies.csv"));
            reader.readLine();
            for(int i =0; i<500; i++)
            {
                String line = reader.readLine();
                String[] tokens = line.split(",");
            }
        }catch (Exception ignored){};


    }
}
