import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
This class deals with the user experience and commands.
 */
public abstract class App
{
    private static final String login = "login";
    private static final String register = "register";
    private static final String quit = "quit";
    private static String email;
    private static String User;
    private static String Password;
    private static String First_Name;
    private static String Last_Name;
    private static String Movie_Name;
    private static String Movie_Date;
    private static String Movie_Cast;
    private static String Movie_Studio;
    private static String Movie_Genre;
    private static String Collection_Name;
    private static Scanner scanner;
    private static boolean loggedin;

    private static void LOGIN(Connection conn)
    {
        System.out.println("Enter username: ");
        User = scanner.nextLine();
        System.out.println("Enter password: ");
        Password = scanner.nextLine();
        try {
            String selectQuery = "Select COUNT(*) from p320_26.users WHERE" +
                    " \"Username\" = '" + User + "'AND " + "\"Password\" = '"+ Password + "'";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult =
                    selectStatement.executeQuery(selectQuery);
            selectResult.next();
            int count = selectResult.getInt(1);
            if(count == 1)
            {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String updateQuery = "Update p320_26.users Set \"LastAccessed\"='" +
                        formatter.format(date) +"' Where \"Username\"='" + User + "'";
                try{
                    Statement updateStatement = conn.createStatement();
                    updateStatement.executeUpdate(updateQuery);
                    System.out.println("Login successful.");
                    loggedin = true;
                }
                catch(Exception e){
                    System.out.println(e);
                };
            }
            else if(count == 0)
            {
                System.out.println("Not found");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }

    private static void REGISTER(Connection conn)
    {
        System.out.println("Enter your email: ");
        email = scanner.nextLine();
        System.out.println("Enter your first name: ");
        First_Name = scanner.nextLine();
        System.out.println("Enter your last name: ");
        Last_Name = scanner.nextLine();
        System.out.println("Enter a username: ");
        User = scanner.nextLine();
        System.out.println("Enter a password: ");
        Password = scanner.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String v = "'" + User + "','" + Password + "','" + First_Name + "','" + Last_Name +
                "','" + email + "','" + formatter.format(date) + "','" + formatter.format(date) + "'" ;
        String insertQuery = "insert into p320_26.users VALUES ("+ v + ")";
        try{
            Statement insertStatement = conn.createStatement();
            insertStatement.executeUpdate(insertQuery);
            System.out.println("Registration Success!");
            // Automatically logs in on register
            loggedin = true;
        }
        catch(Exception e){
            System.out.println(e);
        };

    }

    private static void help(){
        //Please add a description of a user action when you add one
        System.out.println("'search name' will let you search for movies based on the name");
        System.out.println("'search date' will let you search for movies based on the release date");
        System.out.println("'search cast' will let you search for movies based on a cast member");
        System.out.println("'search studio' will let you search for movies based on the studio ");
        System.out.println("'search genre' will let you search for movies based on the genre ");
        System.out.println("'create' will let you create a collection to put movies in ");
        System.out.println("'add movie' will let you add a movie to a collection ");
        System.out.println("'delete movie' will let you delete a movie from a collection ");
        System.out.println("'rename' will let you rename a collection ");
        System.out.println("'delete collection' will allow you to delete a movie ");
        System.out.println("'show' will show information on your collections ");
        System.out.println("'rate will let you rate a movie ");

    }

    private static void searchName(Connection conn){
        System.out.print("Movie Name: ");
        Movie_Name = scanner.nextLine();

        try {
            String selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\"" +
                    " from p320_26.movie m, p320_26.actinmovie a WHERE" +
                    " m.\"Name\" like '%" + Movie_Name + "%' AND a.\"MovieID\" = m.\"MovieID\"" +
                    " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult = selectStatement.executeQuery(selectQuery);
            String old_movie = "";
            String new_movie = "";
            while(selectResult.next()){
                new_movie = selectResult.getString(1 );
                if(!(old_movie.equals(new_movie))){
                    System.out.print("\n" + selectResult.getString(1 ) + "\t");
                    System.out.print(selectResult.getString(2 ) + "\t");
                    System.out.print(selectResult.getInt(3 ) + " minutes\t");
                    System.out.print(selectResult.getString(4 ) + "\t");
                    System.out.print(selectResult.getDouble(5 ) + "\n");
                    System.out.print("\t" + selectResult.getString( 6 ));
                }
                else{
                    System.out.print(", " + selectResult.getString( 6 ));
                }
                old_movie = selectResult.getString(1 );
            }
        }
        catch(Exception e){
            System.out.println(e);
        };
    }

    private static void searchDate(Connection conn){
        System.out.print("Movie Release Date: ");
        Movie_Date = scanner.nextLine();

        try {
            String selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\"" +
                    " from p320_26.movie m, p320_26.actinmovie a WHERE" +
                    " m.\"ReleaseDate\" like '%" + Movie_Date + "%' AND a.\"MovieID\" = m.\"MovieID\"" +
                    " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult = selectStatement.executeQuery(selectQuery);
            String old_movie = "";
            String new_movie = "";
            while(selectResult.next()){
                new_movie = selectResult.getString(1 );
                if(!(old_movie.equals(new_movie))){
                    System.out.print("\n" + selectResult.getString(1 ) + "\t");
                    System.out.print(selectResult.getString(2 ) + "\t");
                    System.out.print(selectResult.getInt(3 ) + " minutes\t");
                    System.out.print(selectResult.getString(4 ) + "\t");
                    System.out.print(selectResult.getDouble(5 ) + "\n");
                    System.out.print("\t" + selectResult.getString( 6 ));
                }
                else{
                    System.out.print(", " + selectResult.getString( 6 ));
                }
                old_movie = selectResult.getString(1 );
            }
        }
        catch(Exception e){
            System.out.println(e);
        };
    }

    private static void searchCast(Connection conn){
        System.out.print("Cast Member: ");
        Movie_Cast = scanner.nextLine();

        try {
            String selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\"" +
                    " from p320_26.movie m, p320_26.actinmovie a WHERE" +
                    " a.\"ActorName\" like '%" + Movie_Cast + "%' AND a.\"MovieID\" = m.\"MovieID\"" +
                    " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult = selectStatement.executeQuery(selectQuery);
            while(selectResult.next()){
                try{
                    String selectQueryMovie = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\"" +
                            " from p320_26.movie m, p320_26.actinmovie a WHERE" +
                            " m.\"Name\" like '%" + selectResult.getString(1 ) + "%' AND a.\"MovieID\" = m.\"MovieID\"" +
                            " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
                    Statement selectStatementMovie = conn.createStatement();
                    ResultSet selectResultMovie = selectStatementMovie.executeQuery(selectQueryMovie);
                    String old_movie = "";
                    String new_movie = "";
                    while(selectResultMovie.next()){
                        new_movie = selectResultMovie.getString(1 );
                        if(!(old_movie.equals(new_movie))){
                            System.out.print("\n" + selectResultMovie.getString(1 ) + "\t");
                            System.out.print(selectResultMovie.getString(2 ) + "\t");
                            System.out.print(selectResultMovie.getInt(3 ) + " minutes\t");
                            System.out.print(selectResultMovie.getString(4 ) + "\t");
                            System.out.print(selectResultMovie.getDouble(5 ) + "\n");
                            System.out.print("\t" + selectResultMovie.getString( 6 ));
                        }
                        else{
                            System.out.print(", " + selectResultMovie.getString( 6 ));
                        }
                        old_movie = selectResultMovie.getString(1 );
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                };
            }
        }
        catch(Exception e){
            System.out.println(e);
        };
    }

    private static void searchStudio(Connection conn){
        System.out.print("Movie Studio: ");
        Movie_Studio = scanner.nextLine();

        try {
            String selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\"" +
                    " from p320_26.movie m, p320_26.actinmovie a WHERE" +
                    " m.\"Studio\" like '%" + Movie_Studio + "%' AND a.\"MovieID\" = m.\"MovieID\"" +
                    " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult = selectStatement.executeQuery(selectQuery);
            String old_movie = "";
            String new_movie = "";
            while(selectResult.next()){
                new_movie = selectResult.getString(1 );
                if(!(old_movie.equals(new_movie))){
                    System.out.print("\n" + selectResult.getString(1 ) + "\t");
                    System.out.print(selectResult.getString(2 ) + "\t");
                    System.out.print(selectResult.getInt(3 ) + " minutes\t");
                    System.out.print(selectResult.getString(4 ) + "\t");
                    System.out.print(selectResult.getDouble(5 ) + "\n");
                    System.out.print("\t" + selectResult.getString( 6 ));
                }
                else{
                    System.out.print(", " + selectResult.getString( 6 ));
                }
                old_movie = selectResult.getString(1 );
            }
        }
        catch(Exception e){
            System.out.println(e);
        };
    }

    private static void searchGenre(Connection conn){
        System.out.print("Movie Genre: ");
        Movie_Genre = scanner.nextLine();

        try {
            String selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\"" +
                    " from p320_26.movie m, p320_26.actinmovie a, p320_26.moviegenre g WHERE" +
                    " g.\"GenreName\" like '%" + Movie_Genre + "%' AND a.\"MovieID\" = m.\"MovieID\" AND g.\"MovieID\" = m.\"MovieID\""  +
                    " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult = selectStatement.executeQuery(selectQuery);
            String old_movie = "";
            String new_movie = "";
            while(selectResult.next()){
                try{
                    new_movie = selectResult.getString(1 );
                    if(!(old_movie.equals(new_movie))){
                        System.out.print("\n" + selectResult.getString(1 ) + "\t");
                        System.out.print(selectResult.getString(2 ) + "\t");
                        System.out.print(selectResult.getInt(3 ) + " minutes\t");
                        System.out.print(selectResult.getString(4 ) + "\t");
                        System.out.print(selectResult.getDouble(5 ) + "\n");
                        System.out.print("\t" + selectResult.getString( 6 ));
                    }
                    else{
                        System.out.print(", " + selectResult.getString( 6 ));
                    }
                    old_movie = selectResult.getString(1 );
                }
                catch(Exception e){
                    System.out.println(e);
                };
            }
        }
        catch(Exception e){
            System.out.println(e);
        };
    }

    private static void createCollection(Connection conn){
        System.out.print("Collection Name: ");
        Collection_Name = scanner.nextLine();
        try{
            // Check to make sure they don't already have a collection named that.
            String selectQuery = "Select COUNT(*) from p320_26.collection WHERE" +
                    " \"Username\" = '" + User + "'AND " + "\"Name\" = '"+ Collection_Name + "'";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult =
                    selectStatement.executeQuery(selectQuery);
            selectResult.next();
            int count = selectResult.getInt(1);
            if(count > 0){
                System.out.println("You already made a collection by that name.");
            }
            else{
                //We need to find the next available id
                int i = 0;
                while(true){
                    try{
                        String v = "'" + Collection_Name + "'," + i + ",'" + User + "'";
                        String insertQuery = "insert into p320_26.collection VALUES ("+ v + ")";
                        Statement insertStatement = conn.createStatement();
                        insertStatement.executeUpdate(insertQuery);
                        System.out.println("Creation Success!");
                        return;
                    }
                    catch(Exception e){

                    }
                    i++;
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private static void addMovieCollection(Connection conn){
        System.out.print("Collection Name: ");
        Collection_Name = scanner.nextLine();
        try{
            //Check to see if they have that collection
            String selectQuery = "Select COUNT(*) from p320_26.collection WHERE" +
                    " \"Username\" = '" + User + "'AND " + "\"Name\" = '"+ Collection_Name + "'";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult =
                    selectStatement.executeQuery(selectQuery);
            selectResult.next();
            int count = selectResult.getInt(1);
            if(count > 0){
                // Need some spicy data
                selectQuery = "Select \"CollectionID\" from p320_26.collection WHERE" +
                        " \"Username\" = '" + User + "'AND " + "\"Name\" = '"+ Collection_Name + "'";
                selectStatement = conn.createStatement();
                selectResult =
                        selectStatement.executeQuery(selectQuery);
                selectResult.next();
                int CollID = selectResult.getInt(1);
                System.out.print("Movie Name: ");
                Movie_Name = scanner.nextLine();
                try {
                    selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\", m.\"MovieID\"" +
                            " from p320_26.movie m, p320_26.actinmovie a WHERE" +
                            " m.\"Name\" like '%" + Movie_Name + "%' AND a.\"MovieID\" = m.\"MovieID\"" +
                            " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
                    selectStatement = conn.createStatement();
                    selectResult = selectStatement.executeQuery(selectQuery);
                    String old_movie = "";
                    String new_movie = "";
                    int MovID = 0;
                    //need to know if this is the first time the loop goes
                    boolean notfirst = false;
                    while(selectResult.next()){
                        new_movie = selectResult.getString(1 );
                        if(!(old_movie.equals(new_movie))){
                            if(notfirst){
                                System.out.print("\nIf that was movie you wanted to add, type 'y': ");
                                String addcheck = scanner.nextLine();
                                if(addcheck.equals("y")){
                                    try{
                                        String v =  CollID + "," + MovID;
                                        String insertQuery = "insert into p320_26.movieincollection VALUES ("+ v + ")";
                                        Statement insertStatement = conn.createStatement();
                                        insertStatement.executeUpdate(insertQuery);
                                        System.out.println("Addition Success!");
                                        return;
                                    }
                                    catch(Exception e){
                                        System.out.println(e);
                                    }
                                }
                            }
                            notfirst = true;
                            System.out.print("\n" + selectResult.getString(1 ) + "\t");
                            System.out.print(selectResult.getString(2 ) + "\t");
                            System.out.print(selectResult.getInt(3 ) + " minutes\t");
                            System.out.print(selectResult.getString(4 ) + "\t");
                            System.out.print(selectResult.getDouble(5 ) + "\n");
                            System.out.print("\t" + selectResult.getString( 6 ));
                            MovID = selectResult.getInt(7);
                        }
                        else{
                            System.out.print(", " + selectResult.getString( 6 ));
                        }
                        old_movie = selectResult.getString(1 );
                    }
                    System.out.print("\nIf that was movie you wanted to add, type 'y': ");
                    String addcheck = scanner.nextLine();
                    if(addcheck.equals("y")){
                        try{
                            String v =  CollID + "," + MovID;
                            String insertQuery = "insert into p320_26.movieincollection VALUES ("+ v + ")";
                            Statement insertStatement = conn.createStatement();
                            insertStatement.executeUpdate(insertQuery);
                            System.out.println("Addition Success!");
                            return;
                        }
                        catch(Exception e){
                            System.out.println(e);
                        }
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                };
            }
            else{
                System.out.println("You do not have a collection by that name.");
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private static void deleteMovieCollection(Connection conn){
        System.out.print("Collection Name: ");
        Collection_Name = scanner.nextLine();
        try{
            //Check to see if they have that collection
            String selectQuery = "Select COUNT(*) from p320_26.collection WHERE" +
                    " \"Username\" = '" + User + "'AND " + "\"Name\" = '"+ Collection_Name + "'";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult =
                    selectStatement.executeQuery(selectQuery);
            selectResult.next();
            int count = selectResult.getInt(1);
            if(count > 0){
                // Need some spicy data
                selectQuery = "Select \"CollectionID\" from p320_26.collection WHERE" +
                        " \"Username\" = '" + User + "'AND " + "\"Name\" = '"+ Collection_Name + "'";
                selectStatement = conn.createStatement();
                selectResult =
                        selectStatement.executeQuery(selectQuery);
                selectResult.next();
                int CollID = selectResult.getInt(1);
                try {
                    selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\", m.\"MovieID\"" +
                            " from p320_26.movie m, p320_26.actinmovie a, p320_26.movieincollection c WHERE" +
                            " c.\"CollectionID\" = " + CollID + " AND a.\"MovieID\" = m.\"MovieID\" AND c.\"MovieID\" = m.\"MovieID\" " +
                            " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
                    selectStatement = conn.createStatement();
                    selectResult = selectStatement.executeQuery(selectQuery);
                    String old_movie = "";
                    String new_movie = "";
                    int MovID = 0;
                    //need to know if this is the first time the loop goes
                    boolean notfirst = false;
                    while(selectResult.next()){
                        new_movie = selectResult.getString(1 );
                        if(!(old_movie.equals(new_movie))){
                            if(notfirst){
                                System.out.print("\nIf that was movie you wanted to delete, type 'y': ");
                                 String addcheck = scanner.nextLine();
                                if(addcheck.equals("y")){
                                    try{
                                        String deleteQuery = "delete from p320_26.movieincollection where \"MovieID\" = " + MovID + " and \"CollectionID\" = " + CollID;
                                        Statement deleteStatement = conn.createStatement();
                                        deleteStatement.executeUpdate(deleteQuery);
                                        System.out.println("Deletion Success!");
                                        return;
                                    }
                                    catch(Exception e){
                                        System.out.println(e);
                                    }
                                }
                            }
                            notfirst = true;
                            System.out.print("\n" + selectResult.getString(1 ) + "\t");
                            System.out.print(selectResult.getString(2 ) + "\t");
                            System.out.print(selectResult.getInt(3 ) + " minutes\t");
                            System.out.print(selectResult.getString(4 ) + "\t");
                            System.out.print(selectResult.getDouble(5 ) + "\n");
                            System.out.print("\t" + selectResult.getString( 6 ));
                            MovID = selectResult.getInt(7);
                        }
                        else{
                            System.out.print(", " + selectResult.getString( 6 ));
                        }
                        old_movie = selectResult.getString(1 );
                    }
                    System.out.print("\nIf that was movie you wanted to delete, type 'y': ");
                    String addcheck = scanner.nextLine();
                    if(addcheck.equals("y")){
                        try{
                            String deleteQuery = "delete from p320_26.movieincollection where \"MovieID\" = " + MovID + " and \"CollectionID\" = " + CollID;
                            Statement deleteStatement = conn.createStatement();
                            deleteStatement.executeUpdate(deleteQuery);
                            System.out.println("Deletion Success!");
                            return;
                        }
                        catch(Exception e){
                            System.out.println(e);
                        }
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                };
            }
            else{
                System.out.println("You do not have a collection by that name.");
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void renameCollection(Connection conn){
        System.out.print("Collection Name to change: ");
        Collection_Name = scanner.nextLine();
        try{
            // Check to make sure they  already have a collection named that.
            String selectQuery = "Select COUNT(*) from p320_26.collection WHERE" +
                    " \"Username\" = '" + User + "'AND " + "\"Name\" = '"+ Collection_Name + "'";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult =
                    selectStatement.executeQuery(selectQuery);
            selectResult.next();
            int count = selectResult.getInt(1);
            if(count <= 0){
                System.out.println("You don't have a collection by that name.");
            }
            else{
                System.out.print("New Collection Name: ");
                String New_Collection_Name = scanner.nextLine();
                String updateQuery = "Update p320_26.Collection Set \"Name\"='" +
                        New_Collection_Name + "' Where \"Username\"='" + User + "' and " + "\"Name\" = '"+ Collection_Name + "'";
                try{
                    Statement updateStatement = conn.createStatement();
                    updateStatement.executeUpdate(updateQuery);
                    System.out.println("Rename successful.");
                }
                    catch(Exception e){
                        System.out.println(e);
                    }
                }
            }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private static void deleteCollection(Connection conn){
        System.out.print("Collection Name: ");
        Collection_Name = scanner.nextLine();
        try{
            //Check to see if they have that collection
            String selectQuery = "Select COUNT(*) from p320_26.collection WHERE" +
                    " \"Username\" = '" + User + "'AND " + "\"Name\" = '"+ Collection_Name + "'";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult =
                    selectStatement.executeQuery(selectQuery);
            selectResult.next();
            int count = selectResult.getInt(1);
            if(count > 0) {
                // Need some spicy data
                selectQuery = "Select \"CollectionID\" from p320_26.collection WHERE" +
                        " \"Username\" = '" + User + "'AND " + "\"Name\" = '" + Collection_Name + "'";
                selectStatement = conn.createStatement();
                selectResult =
                        selectStatement.executeQuery(selectQuery);
                selectResult.next();
                int CollID = selectResult.getInt(1);
                try {
                    String deleteQuery = "delete from p320_26.movieincollection where \"CollectionID\" = " + CollID;
                    Statement deleteStatement = conn.createStatement();
                    deleteStatement.executeUpdate(deleteQuery);
                    deleteQuery = "delete from p320_26.collection where \"CollectionID\" = " + CollID;
                    deleteStatement = conn.createStatement();
                    deleteStatement.executeUpdate(deleteQuery);
                    System.out.println("Deletion Success!");
                    return;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else{
                System.out.println("You do not have a collection by that name.");
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void showCollections(Connection conn){
        try{

            String selectQuery = "Select \"Name\", \"CollectionID\" from p320_26.collection WHERE" +
                    " \"Username\" = '" + User  + "' order by  \"Name\" asc";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult =
                    selectStatement.executeQuery(selectQuery);
            while(selectResult.next()){
                String Collection_Name = selectResult.getString(1);
                int CollId = selectResult.getInt(2);
                try{
                    String selectQueryCount = "Select Count(c.*) as Movie_Count, Sum(m.\"Duration \") as time" +
                            " from p320_26.movieincollection c, p320_26.movie m WHERE" +
                            " c.\"CollectionID\" = '" + CollId  + "' and m.\"MovieID\" = c.\"MovieID\"";
                    Statement selectStatementCount = conn.createStatement();
                    ResultSet selectResultCount =
                            selectStatementCount.executeQuery(selectQueryCount);
                    selectResultCount.next();
                    int count = selectResultCount.getInt(1);
                    int time_sum = selectResultCount.getInt(2);
                    int time_hours = time_sum / 60;
                    int time_minutes = time_sum % 60;
                    System.out.print("\n" + Collection_Name + "\t #Movies " + count + "\t" + time_hours
                    + "h " + time_minutes + "t");
                    try{
                        String selectQueryMovie = "Select m.\"Name\"" +
                                " from p320_26.movie m, p320_26.movieincollection c WHERE" +
                                " c.\"CollectionID\" = " + CollId + " AND c.\"MovieID\" = m.\"MovieID\" " +
                                " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
                        Statement selectStatementMovie = conn.createStatement();
                        ResultSet selectResultMovie = selectStatementMovie.executeQuery(selectQueryMovie);
                        while(selectResultMovie.next()){
                            System.out.print("\n\t" + selectResultMovie.getString(1 ));
                        }
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }

                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void rateMovie(Connection conn){
        System.out.print("Movie Name: ");
        Movie_Name = scanner.nextLine();
        try {
            String selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\", m.\"MovieID\"" +
                    " from p320_26.movie m, p320_26.actinmovie a WHERE" +
                    " m.\"Name\" like '%" + Movie_Name + "%' AND a.\"MovieID\" = m.\"MovieID\"" +
                    " ORDER BY m.\"Name\" asc, m.\"Duration \" asc";
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult = selectStatement.executeQuery(selectQuery);
            String old_movie = "";
            String new_movie = "";
            int MovID = 0;
            //need to know if this is the first time the loop goes
            boolean notfirst = false;
            while(selectResult.next()){
                new_movie = selectResult.getString(1 );
                if(!(old_movie.equals(new_movie))){
                    if(notfirst){
                        System.out.print("\nIf that was movie you wanted to rate, type 'y': ");
                        String addcheck = scanner.nextLine();
                        if(addcheck.equals("y")){
                            try{
                                System.out.print("\nMovie Rating (decimal number from 0-5): ");
                                double rating = scanner.nextDouble();
                                String selectQueryRating = "select Count(*)" +
                                        " from p320_26.userratesmovie" +
                                        " where \"Username\" = '" + User + "' and \"MovieID\" = "+ MovID;
                                Statement selectStatementRating = conn.createStatement();
                                ResultSet selectResultRating = selectStatementRating.executeQuery(selectQueryRating);
                                selectResultRating.next();
                                if(selectResultRating.getInt(1) != 0){
                                    System.out.print("\nIf you want to override your old rating, type 'y': ");
                                    String addchecknew = scanner.next();
                                    if(addchecknew.equals("y")) {
                                        String updateQuery = "Update p320_26.userratesmovie Set \"User_Rating\"=" +
                                                rating + " Where \"Username\"='" + User + "' and " + "\"MovieID\" = "+ MovID;
                                        Statement updateStatement = conn.createStatement();
                                        updateStatement.executeUpdate(updateQuery);
                                    }
                                    else{
                                        return;
                                    }
                                }
                                else{
                                    String updateQuery = "insert into p320_26.userratesmovie values ('" +
                                             User + "'," + MovID + "," + rating + ")";
                                    Statement updateStatement = conn.createStatement();
                                    updateStatement.executeUpdate(updateQuery);
                                }
                                String selectQueryMovie = "select avg(\"User_Rating\")" +
                                        " from p320_26.userratesmovie" +
                                        " where \"MovieID\" = "+ MovID;
                                Statement selectStatementMovie = conn.createStatement();
                                ResultSet selectResultMovie = selectStatementMovie.executeQuery(selectQueryMovie);
                                selectResultMovie.next();
                                double old_rating = selectResultMovie.getDouble(1);
                                String updateQuery = "Update p320_26.movie Set \"UserAvgRating\" = " +
                                        old_rating+ " Where " +  "\"MovieID\" = "+ MovID;
                                Statement updateStatement = conn.createStatement();
                                updateStatement.executeUpdate(updateQuery);
                                System.out.println("Rating Success!");
                                return;
                            }
                            catch(Exception e){
                                System.out.println(e);
                            }
                        }
                    }
                    notfirst = true;
                    System.out.print("\n" + selectResult.getString(1 ) + "\t");
                    System.out.print(selectResult.getString(2 ) + "\t");
                    System.out.print(selectResult.getInt(3 ) + " minutes\t");
                    System.out.print(selectResult.getString(4 ) + "\t");
                    System.out.print(selectResult.getDouble(5 ) + "\n");
                    System.out.print("\t" + selectResult.getString( 6 ));
                    MovID = selectResult.getInt(7);
                }
                else{
                    System.out.print(", " + selectResult.getString( 6 ));
                }
                old_movie = selectResult.getString(1 );
            }
            System.out.print("\nIf that was movie you wanted to add, type 'y': ");
            String addcheck = scanner.nextLine();
            if(addcheck.equals("y")){
                try{
                    System.out.print("\nMovie Rating (decimal number from 0-5): ");
                    double rating = scanner.nextDouble();
                    String selectQueryRating = "select Count(*)" +
                            " from p320_26.userratesmovie" +
                            " where \"Username\" = '" + User + "' and \"MovieID\" = "+ MovID;
                    Statement selectStatementRating = conn.createStatement();
                    ResultSet selectResultRating = selectStatementRating.executeQuery(selectQueryRating);
                    selectResultRating.next();
                    if(selectResultRating.getInt(1) != 0){
                        System.out.print("\nIf you want to override your old rating, type 'y': ");
                        String addchecknew = scanner.nextLine();
                        if(addchecknew.equals("y")) {
                            String updateQuery = "Update p320_26.userratesmovie Set \"User_Rating\"=" +
                                    rating + " Where \"Username\"='" + User + "' and " + "\"MovieID\" = "+ MovID;
                            Statement updateStatement = conn.createStatement();
                            updateStatement.executeUpdate(updateQuery);
                        }
                        else{
                            return;
                        }
                    }
                    else{
                        String updateQuery = "insert into p320_26.userratesmovie values ('" +
                                User + "'," + MovID + "," + rating + ")";
                        Statement updateStatement = conn.createStatement();
                        updateStatement.executeUpdate(updateQuery);
                    }
                    String selectQueryMovie = "select avg(\"User_Rating\")" +
                            " from p320_26.userratesmovie" +
                            " where \"MovieID\" = "+ MovID;
                    Statement selectStatementMovie = conn.createStatement();
                    ResultSet selectResultMovie = selectStatementMovie.executeQuery(selectQueryMovie);
                    selectResultMovie.next();
                    double old_rating = selectResultMovie.getDouble(1);
                    String updateQuery = "Update p320_26.movie Set \"UserAvgRating\"= " +
                            old_rating+ " Where " +  "\"MovieID\" = "+ MovID;
                    Statement updateStatement = conn.createStatement();
                    updateStatement.executeUpdate(updateQuery);
                    System.out.println("Rating Success!");
                    return;
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        catch(Exception e){
            System.out.print(e);
        }
    }

    public static void UserStart(Connection conn)
    {
        scanner = new Scanner(System.in);
        loggedin = false;
        while(!loggedin)
        {
            System.out.println("Please enter: 'login'" +
                    " or 'register' or 'quit'" );
            String line = scanner.nextLine();
            if(line.equals(login))
            {
                LOGIN(conn);
            }
            else if(line.equals(register))
            {
                REGISTER(conn);
            }
            else if (line.equals(quit))
            {
                return;
            }
        }
        while(true)
        {
            System.out.println("\nPlease enter: 'help'" +  " or 'quit' or an action described by help" );
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            if(tokens[0].equals("search"))
            {
                if(tokens[1].equals("name")){
                    searchName(conn);
                }
                else if(tokens[1].equals("date")){
                    searchDate(conn);
                }
                else if(tokens[1].equals("cast")){
                    searchCast(conn);
                }
                else if(tokens[1].equals("studio")){
                    searchStudio(conn);
                }
                else if(tokens[1].equals("genre")){
                    searchGenre(conn);
                }
            }
            else if( tokens[0].equals("create")){
                createCollection(conn);
            }

            else if(tokens[0].equals("add")){
                addMovieCollection(conn);
            }
            else if(tokens[0].equals("delete")){
                if(tokens[1].equals("movie")){
                    deleteMovieCollection(conn);
                }
                else if(tokens[1].equals("collection")){
                    deleteCollection(conn);
                }
            }
            else if(tokens[0].equals("rename")){
                renameCollection(conn);
            }
            else if(tokens[0].equals("show")){
                showCollections(conn);
            }
            else if(tokens[0].equals("rate")){
                rateMovie(conn);
            }
            else if (tokens[0].equals("help")){
                help();
            }
            else if (tokens[0].equals(quit))
            {
                return;
            }
        }
    }
}
