package Pages;

import java.sql.*;
import java.util.LinkedList;

public class CandidateRegistry {

    // Linked list to store pending and approved candidates
    public static final LinkedList<Candidate> pendingCandidates =
            new LinkedList<>();

    public static final LinkedList<Candidate> approvedCandidates =
            new LinkedList<>();

    // Database
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/swing_demo";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    // Create table and load pending
    public static void initialize() {

        String sql =
                "CREATE TABLE IF NOT EXISTS "
                        + "candidacy_applications ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "candidate_id VARCHAR(255), "
                        + "name VARCHAR(255), "
                        + "party VARCHAR(255), "
                        + "status VARCHAR(20) "
                        + "DEFAULT 'pending'"
                        + ")";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(
                    "Failed to create candidacy table."
            );
            e.printStackTrace();
        }

        // ADD voter_id COLUMN IF NOT EXISTS
        String alterSql =
                "ALTER TABLE candidacy_applications "
                        + "ADD COLUMN voter_id VARCHAR(255)";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(alterSql)
        ) {
            statement.executeUpdate();
        } catch (SQLException e) {
        }

        loadPendingFromDB();
    }


    // Load pending candidates from db into linkedlist
    public static void loadPendingFromDB() {

        String sql =
                "SELECT id, candidate_id, name, party "
                        + "FROM candidacy_applications "
                        + "WHERE status = 'pending'";

        synchronized (pendingCandidates) {
            pendingCandidates.clear();

            try (
                    Connection connection =
                            DriverManager.getConnection(
                                    DB_URL, DB_USER, DB_PASSWORD
                            );
                    PreparedStatement statement =
                            connection.prepareStatement(sql);
                    ResultSet result = statement.executeQuery()
            ) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String candidateId =
                            result.getString("candidate_id");
                    String name = result.getString("name");
                    String party = result.getString("party");

                    Candidate c =
                            new Candidate(candidateId, name, party);
                    c.setId(id);

                    pendingCandidates.add(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Load approved candidates from db into linkedlist
    public static void loadApprovedFromDB() {

        String sql = "SELECT id, name, party FROM candidates";

        synchronized (approvedCandidates) {
            approvedCandidates.clear();

            try (
                    Connection connection =
                            DriverManager.getConnection(
                                    DB_URL, DB_USER, DB_PASSWORD
                            );
                    PreparedStatement statement =
                            connection.prepareStatement(sql);
                    ResultSet result = statement.executeQuery()
            ) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String party = result.getString("party");

                    Candidate c =
                            new Candidate("DB-" + name, name, party);
                    c.setId(id);
                    c.setApproved(true);

                    approvedCandidates.add(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Add a new candidacy application
    public static boolean addCandidate(
            Candidate candidate, String voterId
    ) {

        String sql =
                "INSERT INTO candidacy_applications "
                        + "(candidate_id, name, party, status, "
                        + "voter_id) "
                        + "VALUES (?, ?, ?, 'pending', ?)";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql, Statement.RETURN_GENERATED_KEYS
                        )
        ) {
            statement.setString(1, candidate.getCandidateId());
            statement.setString(2, candidate.getName());
            statement.setString(3, candidate.getParty());
            statement.setString(4, voterId);
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    candidate.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        synchronized (pendingCandidates) {
            pendingCandidates.add(candidate);
        }

        return true;
    }


    // Check if a user has already filed a candidacy
    public static boolean hasUserApplied(String voterId) {

        String sql =
                "SELECT 1 FROM candidacy_applications "
                        + "WHERE voter_id = ? "
                        + "AND status IN ('pending', 'approved') "
                        + "LIMIT 1";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, voterId);

            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // Get candidacy status for a voter
    public static String getCandidacyStatus(String voterId) {

        String sql =
                "SELECT status FROM candidacy_applications "
                        + "WHERE voter_id = ? "
                        + "ORDER BY id DESC LIMIT 1";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, voterId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String status = result.getString("status");

                    return status.substring(0, 1).toUpperCase()
                            + status.substring(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No application";
    }


    // Approve a candidate
    public static boolean approveCandidate(Candidate candidate) {

        // UPDATE STATUS IN candidacy_applications
        String updateSql =
                "UPDATE candidacy_applications "
                        + "SET status = 'approved' "
                        + "WHERE id = ?";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(updateSql)
        ) {
            statement.setInt(1, candidate.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // INSERT INTO candidates TABLE
        String insertSql =
                "INSERT INTO candidates "
                        + "(name, party, position) "
                        + "VALUES (?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(insertSql)
        ) {
            statement.setString(1, candidate.getName());
            statement.setString(2, candidate.getParty());
            statement.setString(3, "Candidate");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Move in linkedlist
        synchronized (pendingCandidates) {
            pendingCandidates.remove(candidate);
        }
        candidate.setApproved(true);
        synchronized (approvedCandidates) {
            approvedCandidates.add(candidate);
        }

        return true;
    }


    // Reject a candidate
    public static boolean rejectCandidate(Candidate candidate) {

        String sql =
                "UPDATE candidacy_applications "
                        + "SET status = 'rejected' "
                        + "WHERE id = ?";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setInt(1, candidate.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        synchronized (pendingCandidates) {
            pendingCandidates.remove(candidate);
        }

        return true;
    }


    // Check if a candidate already exists by name
    public static boolean candidateExists(String name) {

        // Check pending in db
        String pendingSql =
                "SELECT 1 FROM candidacy_applications "
                        + "WHERE name = ? AND status = 'pending' "
                        + "LIMIT 1";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(pendingSql)
        ) {
            statement.setString(1, name);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check approved in db
        String approvedSql =
                "SELECT 1 FROM candidates WHERE name = ? LIMIT 1";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(approvedSql)
        ) {
            statement.setString(1, name);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check in-memory as fallback
        synchronized (pendingCandidates) {
            for (Candidate c : pendingCandidates) {
                if (c.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        synchronized (approvedCandidates) {
            for (Candidate c : approvedCandidates) {
                if (c.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }

        return false;
    }


    // Get pending count
    public static int getPendingCount() {
        synchronized (pendingCandidates) {
            return pendingCandidates.size();
        }
    }

    // Get approved count
    public static int getApprovedCount() {
        synchronized (approvedCandidates) {
            return approvedCandidates.size();
        }
    }
}
