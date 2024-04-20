import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

interface Node {
    void setFollowees(boolean[] followees);
    void setPendingTransactions(Set<Transaction> pendingTransactions);
    Set<Transaction> sendToFollowers();
    void receiveFromFollowees(Set<Candidate> candidates);
}

class Transaction {
    int id;
    // other transaction properties
}

class Candidate {
    int sender;
    Transaction tx;
    // other candidate properties
}

public class CompliantNode implements Node {

    private final double graph;
    private final double malicious;
    private final double txDistribution;
    private final int numRounds;
    private Set<Transaction> pendingTransactions;
    private boolean[] followees;
    private Set<Integer> blackList;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
        this.graph = p_graph;
        this.malicious = p_malicious;
        this.txDistribution = p_txDistribution;
        this.numRounds = numRounds;
    }

    public void setFollowees(boolean[] followees) {
        this.followees = followees;
        this.blackList = new HashSet<>(followees.length);
    }

    public void setPendingTransactions(Set<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public Set<Transaction> sendToFollowers() {
        return new HashSet<>(pendingTransactions);
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
        final Set<Integer> senders = candidates.stream().map(candidate -> candidate.sender).collect(Collectors.toSet());
        for (int i = 0; i < this.followees.length; i++) {
            if (this.followees[i] && !senders.contains(i))
                this.blackList.add(i);
        }
        this.pendingTransactions = candidates.stream()
                .filter(candidate -> !this.blackList.contains(candidate.sender))
                .map(candidate -> candidate.tx)
                .collect(Collectors.toSet());
    }
}
