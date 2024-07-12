import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Consumer {
    private static int fileCount = 0;
    private final Object[] urlsArray;
    private final Path dir = Paths.get("C:\\Users\\User\\TEST");

    public Consumer(Object[] urlsArray) throws IOException {
        this.urlsArray = urlsArray;
        if (!Files.exists(dir))
            Files.createDirectory(dir);
    }

    public void downloadFile(int id) throws InterruptedException, IOException {
        synchronized (this) {
            fileCount++;
        }
        try {
            String[] line = urlsArray[fileCount - 1].toString().split("\\s+");
            startDownloading(id, line);
            URL url = new URL(line[1]);
            String fileName = getFileName(url);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(dir.resolve(fileName).toFile());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            finishDownloading(id, line);
            rbc.close();
            fos.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    private static void finishDownloading(int id, String[] line) {
        System.out.println("Thread-" + id + " finish download " + "file number " + line[0]);
    }

    private static void startDownloading(int id, String[] line) {
        System.out.println("Thread-" + id + " start download " + "file number " + line[0]);
    }

    public String getFileName(URL url) {
        String[] str = url.toString().split("/");
        return str[str.length - 1];
    }

    public synchronized int getFileCount() {
        return fileCount;
    }
}
