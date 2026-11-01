package Pages;

import java.sql.*;
import java.util.LinkedList;

public class CandidateRegistry {

    public static final LinkedList<Candidate> pendingCandidates = new LinkedList<>();
    public static final LinkedList<Candidate> approvedCandidates = new LinkedList<>();

    public static void loadPendingFromDB() {

        pendingCandidates.clear();

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT id, candidate_id, name, party "
                    + "FROM candidacy_applications "
                    + "WHERE status = 'pending'");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String candidateId = rs.getString("candidate_id");
                String name = rs.getString("name");
                String party = rs.getString("party");

                Candidate c = new Candidate(candidateId, name, party);
                c.setId(id);
                pendingCandidates.add(c);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loadApprovedFromDB() {

        approvedCandidates.clear();

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT id, name, party FROM candidates");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String party = rs.getString("party");

                Candidate c = new Candidate("DB-" + name, name, party);
                c.setId(id);
                c.setApproved(true);
                approvedCandidates.add(c);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean addCandidate(Candidate candidate, String voterId) {

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO candidacy_applications "
                    + "(candidate_id, name, party, status, voter_id) "
                    + "VALUES (?, ?, ?, 'pending', ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, candidate.getCandidateId());
            stmt.setString(2, candidate.getName());
            stmt.setString(3, candidate.getParty());
            stmt.setString(4, voterId);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                candidate.setId(keys.getInt(1));
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        pendingCandidates.add(candidate);
        return true;
    }

    public static boolean hasUserApplied(String voterId) {

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT 1 FROM candidacy_applications "
                    + "WHERE voter_id = ? AND status IN ('pending', 'approved') LIMIT 1");
            stmt.setString(1, voterId);
            ResultSet rs = stmt.executeQuery();
            boolean applied = rs.next();
            con.close();
            return applied;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getCandidacyStatus(String voterId) {

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT status FROM candidacy_applications "
                    + "WHERE voter_id = ? ORDER BY id DESC LIMIT 1");
            stmt.setString(1, voterId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                con.close();
                return status.substring(0, 1).toUpperCase() + status.substring(1);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No application";
    }

    public static boolean candidateExists(String name) {

        try {
            Connection con = dbconnection.connect();

            PreparedStatement stmt1 = con.prepareStatement(
                    "SELECT 1 FROM candidacy_applications "
                    + "WHERE name = ? AND status = 'pending' LIMIT 1");
            stmt1.setString(1, name);
            ResultSet rs1 = stmt1.executeQuery();
            if (rs1.next()) {
                con.close();
                return true;
            }

            PreparedStatement stmt2 = con.prepareStatement(
                    "SELECT 1 FROM candidates WHERE name = ? LIMIT 1");
            stmt2.setString(1, name);
            ResultSet rs2 = stmt2.executeQuery();
            if (rs2.next()) {
                con.close();
                return true;
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean approveCandidate(Candidate candidate) {

        try {
            Connection con = dbconnection.connect();

            PreparedStatement update = con.prepareStatement(
                    "UPDATE candidacy_applications SET status = 'approved' WHERE id = ?");
            update.setInt(1, candidate.getId());
            update.executeUpdate();

            PreparedStatement insert = con.prepareStatement(
                    "INSERT INTO candidates (name, party, position) VALUES (?, ?, ?)");
            insert.setString(1, candidate.getName());
            insert.setString(2, candidate.getParty());
            insert.setString(3, "Candidate");
            insert.executeUpdate();

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        pendingCandidates.remove(candidate);
        candidate.setApproved(true);
        approvedCandidates.add(candidate);
        return true;
    }

    public static boolean rejectCandidate(Candidate candidate) {

        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE candidacy_applications SET status = 'rejected' WHERE id = ?");
            stmt.setInt(1, candidate.getId());
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        pendingCandidates.remove(candidate);
        return true;
    }

    public static int getPendingCount() {
        return pendingCandidates.size();
    }

    public static int getApprovedCount() {
        return approvedCandidates.size();
    }
}
