package Pages;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UserRegistry {

    // HashMap for verification lookup: username -> verified status
    public static final Map<String, Boolean> users = new HashMap<>();

    // HashSet for quick voter registration existence check
    public static final HashSet<String> registeredUsernames = new HashSet<>();

    public static final Map<String, Voter> voters = new HashMap<>();
    public static String currentUser = null;

    // Graph to represent connections between voters (by age) and candidates (by name)
    public static VoterCandidateGraph graph = new VoterCandidateGraph();

    public static void loadUsers() {

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT username, is_verified, age FROM users");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                boolean verified = rs.getBoolean("is_verified");
                int age = rs.getInt("age");
                users.put(username, verified);
                registeredUsernames.add(username);
                if (verified) {
                    voters.put(username, new Voter(username, username, true, age));
                }
            }

            con.close();
            System.out.println("User cache loaded: " + users.size() + " users.");

        } catch (SQLException e) {
            System.out.println("Failed to load user cache.");
            e.printStackTrace();
        }

        // Load existing votes into graph using voter age -> candidate name
        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT u.username, u.age, c.name AS cname "
                    + "FROM votes v "
                    + "JOIN users u ON v.username = u.username "
                    + "JOIN candidates c ON v.candidate_id = c.id");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String voterId = rs.getString("username");
                int age = rs.getInt("age");
                String candidateName = rs.getString("cname");
                graph.addEdge(voterId, age, candidateName);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // O(1) duplicate check using HashSet
    public static boolean userExists(String username) {
        return registeredUsernames.contains(username);
    }

    // O(1) verification check using HashMap
    public static boolean isVerified(String username) {
        return Boolean.TRUE.equals(users.get(username));
    }

    public static void addUser(String username, boolean verified) {
        users.put(username, verified);
        registeredUsernames.add(username);
    }

    public static void updateUser(String username, boolean verified, int age) {

        if (users.containsKey(username)) {
            users.put(username, verified);
        }

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE users SET is_verified = ?, age = ? WHERE username = ?");
            stmt.setBoolean(1, verified);
            stmt.setInt(2, age);
            stmt.setString(3, username);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Failed to update verification.");
            e.printStackTrace();
        }
    }

    public static void registerVoter(Voter voter) {
        voters.put(voter.getVoterId(), voter);
        registeredUsernames.add(voter.getVoterId());
    }

    public static Voter getVoter(String username) {
        return voters.get(username);
    }

    public static void setCurrentUser(String username) {
        currentUser = username;
    }
}
