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

        Object monitor = new Object();
        final boolean[] eggTurn = {true};

        Thread eggThread = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                synchronized (monitor) {
                    while (!eggTurn[0]) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Egg");
                    eggTurn[0] = false;
                    monitor.notifyAll();
                }
            }
        });

        Thread henThread = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                synchronized (monitor) {
                    while (eggTurn[0]) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Hen");
                    eggTurn[0] = true;
                    monitor.notifyAll();
                }
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
    }
}
