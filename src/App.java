import java.sql.Connection;
import java.util.Scanner;

public abstract class App
{
    private final String login = "login";

    private void LOGIN()
    {
        System.out.println("Enter user name: ");

        System.out.println("Enter pass word: ");
    }
    public void UserStart()
    {
        System.out.println("Please enter: 'login'" +
                " or 'register" );
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            String line = scanner.nextLine();
            if(line.equals(login))
            {
                LOGIN();
            }
        }
    }
}
