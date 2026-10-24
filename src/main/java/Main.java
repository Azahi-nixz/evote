//========= MAIN =========//
import Pages.LogIn;
import Pages.UserRegistry;
import Pages.CandidateRegistry;

import static Pages.UserRegistry.users;

public class Main {
    public static void main(String[] args) {

        // TO LOAD USERS
        UserRegistry.loadUsers();

        // INITIALIZE CANDIDACY TABLE AND LOAD PENDING
        CandidateRegistry.initialize();

        LogIn logIn = new LogIn();
        logIn.setVisible(true);

        System.out.println(users);
    }
}
