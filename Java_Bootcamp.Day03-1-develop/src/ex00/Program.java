public class Program {
    public static void main(String[] args) {
        if (args.length != 1 && !args[0].startsWith("--count=")) {
            System.out.println("Error");
            System.exit(-1);
        }
        int count = Integer.parseInt(args[0].substring("--count=".length()));
        if (count <= 0) {
            System.out.println("Error");
            System.exit(-1);
        }

        Thread eggThread = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                System.out.println("Egg");
            }
        });

        Thread henThread = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                System.out.println("Hen");
            }
        });

        eggThread.start();
        henThread.start();

        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < count; i++)
            System.out.println("Human");
    }
}
