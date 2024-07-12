import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Program {
    private static int threadsCount;
    private static Object[] urls;

    public static void main(String[] args) throws IOException {
        if  (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            System.out.println("Error");
            System.exit(-1);
        }
        threadsCount = Integer.parseInt(args[0].substring("--threadsCount=".length()));
        BufferedReader fileReader = new BufferedReader(new FileReader("files_urls.txt"));
        urls = fileReader.lines().toArray();
        Consumer consumer = new Consumer(urls);
        download(consumer);
        fileReader.close();
    }
    private static void download(Consumer consumer) {
        Thread[] threads = new Thread[threadsCount];
        for (int i = 0; i < threadsCount; i++)
            threads[i] = new Thread(new Producer(consumer, i + 1, urls.length));
        for (Thread thread : threads)
            thread.start();
    }
}
