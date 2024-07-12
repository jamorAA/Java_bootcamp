import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class Program {
    private static Path path;

    public static void main(String[] args) throws IOException {
        if (args.length < 1 || !args[0].startsWith("--current-folder=")) {
            System.out.println("Error");
            System.exit(-1);
        }
        String folderPath = args[0].substring("--current-folder=".length());
        path = Paths.get(folderPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            System.out.println("Error");
            System.exit(-1);
        }
        System.out.println(path);
        getCommand();
    }

    private static void getCommand() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("-> ");
            String s = scanner.nextLine();
            String[] command = s.split("\\s+");
            if (command[0].equals("exit")) {
                System.exit(-1);
            } else if (command[0].equals("ls") && command.length == 1) {
                ls();
            } else if (command[0].equals("cd") && command.length == 2) {
                cd(command[1]);
            } else if (command[0].equals("mv") && command.length == 3) {
                mv(command[1], command[2]);
            } else {
                System.out.println("Don't support this command " + s);
            }
            getCommand();
        }
    }

    private static void ls() throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                String type = Files.isDirectory(entry) ? "DIR" : "FILE";
                long size = Files.isDirectory(entry) ? getDirectorySize(entry) / 1024 : Files.size(entry) / 1024;
                System.out.println(entry.getFileName() + " " + type + " " + size + " KB");
            }
        }
    }

    private static void cd(String directory) {
        Path target = path.resolve(directory);
        if (Files.isDirectory(target)) {
            path = target;
            System.out.println(path);
        } else {
            System.out.println("Argument for cd command isn't a directory");
        }
    }

    private static void mv(String what, String where) throws IOException {
        Path fromFile = path.resolve(what);
        Path toFile = path.resolve(where);
        Files.move(fromFile, toFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private static long getDirectorySize(Path directory) throws IOException {
        AtomicLong size = new AtomicLong(0);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                size.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }
        });
        return size.get();
    }
}
