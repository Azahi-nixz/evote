import Pages.LogIn;
import Pages.UserRegistry;
import Pages.CandidateRegistry;

import static Pages.UserRegistry.users;

public class Main {
    public static void main(String[] args) {

        UserRegistry.loadUsers();
        CandidateRegistry.initialize();

        new LogIn();

        System.out.println(users);
    }
}
