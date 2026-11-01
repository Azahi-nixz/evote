import Pages.LogIn;
import Pages.UserRegistry;
import Pages.CandidateRegistry;
import Pages.dbconnection;

import static Pages.UserRegistry.users;

public class Main {
    public static void main(String[] args) {

        dbconnection.createDatabase();
        dbconnection.createTables();

        UserRegistry.loadUsers();
        CandidateRegistry.loadPendingFromDB();

        new LogIn();

        System.out.println(users);
    }
}
