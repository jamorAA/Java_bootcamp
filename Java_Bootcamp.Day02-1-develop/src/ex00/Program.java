import java.util.*;
import java.io.*;

public class Program {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("signatures.txt");
        FileOutputStream outputStream = new FileOutputStream("result.txt", true);
        Map<String, String> map = new HashMap<String, String>(){};
        ParseSignatures(inputStream, map);
        CheckFiles(outputStream, map);
    }

    private static void ParseSignatures(FileInputStream inputStream, Map map) throws FileNotFoundException {
        scanner = new Scanner(inputStream);
        String s;
        String[] signature = new String[2];
        while (scanner.hasNext()) {
            s = scanner.nextLine();
            signature = s.split(", ");
            map.put(signature[0], signature[1]);
        }
    }
    private static void CheckFiles(FileOutputStream outputStream, Map<String, String> map) throws FileNotFoundException {
        String file;
        scanner = new Scanner(System.in);
        if (scanner.hasNext() && !(file = scanner.nextLine()).equalsIgnoreCase("42")) {
            try {
                boolean processed = false;
                FileInputStream file_input_stream = new FileInputStream(file);
                StringBuilder bytes = new StringBuilder();
                for (int i = 0; i < 10; ++i)
                    bytes.append(String.format("%02X ", file_input_stream.read()));
                for (String key : map.keySet()) {
                    String s = bytes.substring(0, map.get(key).length());
                    if (map.get(key).startsWith(s)) {
                        outputStream.write(key.getBytes());
                        outputStream.write('\n');
                        System.out.println("PROCESSED");
                        processed = true;
                        CheckFiles(outputStream, map);
                    }
                }
                if (!processed) {
                    System.err.println("Incorrect file");
                    CheckFiles(outputStream, map);
                }
            } catch (IOException io) {
                scanner.close();
                System.out.println(io);
            }
        }
        scanner.close();
    }
}
