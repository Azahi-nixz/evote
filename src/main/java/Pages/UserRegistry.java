package Pages;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserRegistry {

    public static final Map<String, Boolean> users = new HashMap<>();
    public static final Map<String, Voter> voters = new HashMap<>();
    public static String currentUser = null;

    public static void loadUsers() {

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT username, is_verified FROM users");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                boolean verified = rs.getBoolean("is_verified");
                users.put(username, verified);
            }

            con.close();
            System.out.println("User cache loaded: " + users.size() + " users.");

        } catch (SQLException e) {
            System.out.println("Failed to load user cache.");
            e.printStackTrace();
        }
    }

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    public static boolean isVerified(String username) {
        return Boolean.TRUE.equals(users.get(username));
    }

    public static void addUser(String username, boolean verified) {
        users.put(username, verified);
    }

    public static void updateUser(String username, boolean verified) {

        if (users.containsKey(username)) {
            users.put(username, verified);
        }

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE users SET is_verified = ? WHERE username = ?");
            stmt.setBoolean(1, verified);
            stmt.setString(2, username);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Failed to update verification.");
            e.printStackTrace();
        }
    }

    public static void registerVoter(Voter voter) {
        voters.put(voter.getVoterId(), voter);
    }

    public static Voter getVoter(String username) {
        return voters.get(username);
    }

    public static void setCurrentUser(String username) {
        currentUser = username;
    }
}
