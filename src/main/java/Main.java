//========= MAIN =========//
import Pages.LogIn;
import Pages.UserRegistry;

import static Pages.UserRegistry.users;

public class Main {
    public static void main(String[] args) {

        // TO LOAD USERS
        UserRegistry.loadUsers();

        LogIn logIn = new LogIn();
        logIn.setVisible(true);

        System.out.println(users);
    }
}
