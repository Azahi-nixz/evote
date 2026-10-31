import Pages.LogIn;
import Pages.UserRegistry;
import Pages.CandidateRegistry;


public class Main {
    public static void main(String[] args) {


        CandidateRegistry.initializeDatabase();
        CandidateRegistry.initialize();
        UserRegistry.loadUsers();

        new LogIn();

    }
}

// commented pages are currently not working properly