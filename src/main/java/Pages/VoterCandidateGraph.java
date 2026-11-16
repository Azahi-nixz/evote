package Pages;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class VoterCandidateGraph {

    // Adjacency list: voter ID -> LinkedList of "age -> candidateName" labels
    private Map<String, LinkedList<String>> adjacencyList;

    VoterCandidateGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addVoter(String voterId) {
        if (!adjacencyList.containsKey(voterId)) {
            adjacencyList.put(voterId, new LinkedList<>());
        }
    }

    // Edge label is "age -> candidateName"
    public void addEdge(String voterId, int age, String candidateName) {
        addVoter(voterId);
        String label = "Age " + age + " -> " + candidateName;
        adjacencyList.get(voterId).add(label);
    }

    public LinkedList<String> getEdgesFor(String voterId) {
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

    public Map<String, LinkedList<String>> getAdjacencyList() {
        return adjacencyList;
    }

    @Override
    public String toString() {
        return adjacencyList.toString();
    }
}
