package Pages;

import java.time.LocalDateTime;

public class VoteRecord {

    private String voteId;
    private String voterId;
    private String candidateId;
    private LocalDateTime timestamp;

    public VoteRecord(String voteId, String voterId,
                      String candidateId, LocalDateTime timestamp) {
        this.voteId = voteId;
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.timestamp = timestamp;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Vote " + voteId + " - Voter: " + voterId
                + " -> Candidate: " + candidateId
                + " at " + timestamp;
    }
}
