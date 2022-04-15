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
    }

    private static void searchName(Connection conn){
        System.out.print("Movie Name: ");
        Movie_Name = scanner.nextLine();
        try {
            String selectQuery = "Select m.\"Name\", m.\"Director\", m.\"Duration \", m.\"mpaa\", m.\"UserAvgRating\", a.\"ActorName\"" +
                    " from p320_26.movie m, p320_26.actinmovie a WHERE" +
                    " \"Name\" like '%" + Movie_Name + "%' AND a.\"MovieID\" = m.\"MovieID\"";
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
            System.out.println("Please enter: 'help'" +  " or 'quit' or an action described by help" );
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            if(tokens[0].equals("search"))
            {
                // Eventually this should check the type of search
                searchName(conn);
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
