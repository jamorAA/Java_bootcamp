import java.io.IOException;

public class Producer implements Runnable {
    private final Consumer consumer;
    private final int id;
    private static int filesCount = 0;
    public Producer(Consumer consumer, int id, int filesCount) {
        this.id = id;
        this.consumer = consumer;
        Producer.filesCount = filesCount;
    }
    @Override
    public void run() {
        long m = System.currentTimeMillis();
        while(consumer.getFileCount() < filesCount) {
            try {
                consumer.downloadFile(id);
            }
            catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
