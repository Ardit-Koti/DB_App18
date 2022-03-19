import java.lang.Object.*;
import java.util.Scanner;

/*
Name: DataDumper
Authors:
Description: Dumps data from comma seperated files into
             the database
 */

public class DataDumper
{
    static void MovieTransfer()
    {
        Scanner fileSc = new Scanner("C:/DB_App/rotten_tomatoes_top_movies.csv");
        for(int i =0; i<500; i++)
        {
            String line = fileSc.nextLine();
            String[] tokens = line.split(",");
        }
    }
}
