package Pages;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class VoterCandidateGraph {

    // Adjacency list: voter ID -> list of candidate IDs they voted for
    private Map<String, LinkedList<String>> adjacencyList;

    VoterCandidateGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addVoter(String voterId) {
        if (!adjacencyList.containsKey(voterId)) {
            adjacencyList.put(voterId, new LinkedList<>());
        }
    }

    public void addEdge(String voterId, String candidateId) {
        addVoter(voterId);
        adjacencyList.get(voterId).add(candidateId);
    }

    public LinkedList<String> getVotesFor(String voterId) {
        if (adjacencyList.containsKey(voterId)) {
            return adjacencyList.get(voterId);
        }
        return new LinkedList<>();
    }

    public boolean hasVoted(String voterId) {
        return adjacencyList.containsKey(voterId)
                && !adjacencyList.get(voterId).isEmpty();
    }

    public int getTotalVoters() {
        return adjacencyList.size();
    }

    @Override
    public String toString() {
        return adjacencyList.toString();
    }
}
