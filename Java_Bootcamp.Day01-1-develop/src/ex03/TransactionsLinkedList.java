import java.util.UUID;

class TransactionNotFoundException extends RuntimeException {}

public class TransactionsLinkedList implements TransactionsList{
    private Node first;
    private Node last;
    private Integer count = 0;

    private static class Node {
        Transaction transaction;
        Node next;
        Node prev;

        public Node(Transaction transaction, Node next, Node prev) {
            this.transaction = transaction;
            this.next = next;
            this.prev = prev;
        }

        private Transaction getTransaction() {
            return transaction;
        }
        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    public TransactionsLinkedList() {}

    @Override
    public void addTransaction(Transaction transaction) {
        Node temp = last;
        Node newNode = new Node(transaction, null, temp);
        last = newNode;
        if (temp == null)
            first = newNode;
        else
            temp.next = newNode;
        count++;
    }

    @Override
    public void deleteTransactionById(UUID id) {
        if (id == null || last == null)
            System.err.println("Error");
        for (Node i = first; i != null; i = i.next) {
            if (i.transaction.getIdentifier().equals(id)) {
                remove(i);
                return;
            }
        }
        System.err.println("Error");
    }
    private void remove(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        if (next != null)
            next.prev = prev;
        else
            last = first;

        if (prev != null)
            prev.next = next;
        else
            first = next;

        if (first == null)
            last = null;

        count--;
    }
    @Override
    public Transaction[] toArray() {
        if(count == 0) {
            System.err.println("Error");
            return null;
        }
        Transaction[] transactions = new Transaction[count];
        int j = 0;
        for(Node i = first; i != null; i = i.next) {
            transactions[j] = i.transaction;
            j++;
        }
        return transactions;
    }

    public Integer getCountTransactions() {
        return count;
    }
}
