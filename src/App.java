import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
/*
This class deals with the user experience and commands.
 */
public abstract class App
{
    private static final String login = "login";
    private static final String register = "register";
    private static String email;
    private static String User;
    private static String Password;
    private static Scanner scanner;

    private static void LOGIN(Connection conn)
    {
        System.out.println("Enter username: ");
        User = scanner.nextLine();
        System.out.println("Enter password: ");
        Password = scanner.nextLine();
        try {
           // String selectQuery = "Select COUNT(*) from p320_26.USER WHERE" +
             //       " Username = " + User + "AND" + "Password = "+ Password;
            Statement selectStatement = conn.createStatement();
            ResultSet selectResult =
                    selectStatement.executeQuery("Select COUNT(*) " +
                    "from USER WHERE" +
                    " 'Username' = 'John123' AND 'Password' = 'lalala'");
            selectResult.next();
            int count = selectResult.getInt(1);
            if(count == 1)
            {
                System.out.println("Login successful.");
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

    }

    public static void UserStart(Connection conn)
    {

        System.out.println("Please enter: 'login'" +
                " or 'register'" );
        scanner = new Scanner(System.in);
        while(true)
        {
            String line = scanner.nextLine();
            if(line.equals(login))
            {
                LOGIN(conn);
            }
            else if(line.equals(register))
            {
                REGISTER(conn);
            }
        }
    }
}
