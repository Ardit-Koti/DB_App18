import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.Object.*;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Duration;
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
    static private String[] Directors;
    static private String[] ReleaseDate;
    static private String MovieName;
    static private String MovieID;
    static private String MPAA;
    static private String duration;
    static private int a;
    static private int ActID;
    static private int MovieGenreID;
    static private final String quotes = "\"";

    static private String[] Group(int i, String[] stuff)
    {
        int beg = a;
        while(a < stuff.length)
        {
            if(stuff[a].startsWith(quotes))
            {
                break;
            }
            a++;
        }
        String[] list = new String[a-beg+1];
        System.arraycopy(stuff, beg, list, a, list.length);
        return list;
    }

    public static void MovieTransfer(Connection conn)
    {
        ActID=0;
        MovieGenreID = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:/DB_App/rotten_tomatoes_top_movies.csv"));
            reader.readLine();
            for(int i =0; i<500; i++)
            {
                String line = reader.readLine();
                String[] tokens = line.split(",");
                a = 0;
                MovieID = tokens[a];
                a++;
                if(tokens[a].startsWith(quotes))
                {
                    Studios = Group(a, tokens);
                }
                else
                {
                    Studios = new String[1];
                    Studios[0] = tokens[a];
                }
                a++;
                if(tokens[a].startsWith(quotes))
                {
                    Genres = Group(a, tokens);
                }
                else
                {
                    Genres = new String[1];
                    Genres[0] = tokens[a];
                }
                a++;
                MovieName = tokens[a];
                a++;
                MPAA = tokens[a];
                a++;
                if(tokens[a].startsWith(quotes))
                {
                    ReleaseDate = Group(a, tokens);
                }
                a++;
                duration = tokens[a];
                a++;
                if(tokens[a].startsWith(quotes))
                {
                    Directors = Group(a, tokens);
                }
                else
                {
                    Directors = new String[1];
                    Directors[0] = tokens[a];
                }
                a++;
                if(tokens[a].startsWith(quotes))
                {
                    Actors = Group(a, tokens);
                }
                else
                {
                    Actors = new String[1];
                    Actors[0] = tokens[a];
                }
                for(String string : Studios)
                {
                    string = string.replace(quotes, "");
                }
                for(String string : Genres)
                {
                    string = string.replace(quotes, "");
                }
                for(String string : Actors)
                {
                    string =string.replace(quotes, "");
                }
                for(String string : Directors)
                {
                    string = string.replace(quotes, "");
                }
                for(String string: ReleaseDate)
                {
                    string = string.replace(quotes, "");
                }
                String v = MovieID + ", " + Studios[0] + ", " + "0.0" + ", "
                        + "0" + ", " + Genres[0] + ", " + MovieName + ", "
                        + MPAA + ", " + ReleaseDate[0] + ", " + duration
                        + ", " + Directors[0];
                String insertQuery = "insert into movie (*) VALUES ("+ v + ")";
                Statement insertStatement = conn.createStatement();
                insertStatement.executeUpdate(insertQuery);
                for(String actor : Actors)
                {
                    insertQuery = "insert into actinmovie (*) VALUES " +
                            "(" + ActID + ", " + actor + ", " + MovieID+ ")";
                    Statement actInMovie = conn.createStatement();
                    actInMovie.executeUpdate(insertQuery);
                    ActID++;
                }
                Statement DirectorsInsert = conn.createStatement();
                DirectorsInsert.executeUpdate("insert into director (NAME)" +
                        " VALUE ("+ Directors[0] + ")");
                for(String genre: Genres)
                {
                    insertQuery = "insert into moviegenre (*) VALUES"
                            + " (" + MovieGenreID + ", " + genre + ", "
                            + MovieID + ")";
                }

            }
        }catch (Exception ignored){};

    }
}
