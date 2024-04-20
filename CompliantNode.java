import java.util.HashSet;
import java.util.Set;

public class CompliantNode implements Node {

    private boolean[] followees;
    private Set<Transaction> pendingTransactions;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
        // Constructor for CompliantNode
    }

    @Override
    public void setFollowees(boolean[] followees) {
        this.followees = followees;
    }

    @Override
    public void setPendingTransactions(Set<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    @Override
    public Set<Transaction> sendToFollowers() {
        return new HashSet<>(pendingTransactions);
    }

    @Override
    public void receiveFromFollowees(Set<Candidate> candidates) {
        for (Candidate candidate : candidates) {
            if (followees[candidate.sender]) {
                pendingTransactions.add(candidate.tx);
            }
        }
    }
}
