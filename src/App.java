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
    private static Scanner scanner;

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
        }
        catch(Exception e){
            System.out.println(e);
        };

    }

    private static void searchName(Connection conn, String name){

    }

    public static void UserStart(Connection conn)
    {
        scanner = new Scanner(System.in);
        while(true)
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
    }
}
