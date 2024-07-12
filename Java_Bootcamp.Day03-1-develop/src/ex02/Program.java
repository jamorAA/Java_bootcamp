import java.util.Random;

public class Program {
    public static void main(String[] args) {
        int arraySize = 10;
        int threadsCount = 2;
        if (args.length > 1) {
            if (args[0].startsWith("--arraySize=")) {
                try {
                    arraySize = Integer.parseInt(args[0].substring("--arraySize=".length()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid array size provided, using default value (10)");
                }
            }
            if (args[1].startsWith("--threadsCount=")) {
                try {
                    threadsCount = Integer.parseInt(args[1].substring("--threadsCount=".length()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid threads count provided, using default value (2)");
                }
            }
        }

        Random random = new Random();
        int[] numbers = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            numbers[i] = random.nextInt(1000);
        }

        int expectedSum = calculateSequentialSum(numbers);
        System.out.println("Sum: " + expectedSum);

        int elementsPerThread = arraySize / threadsCount;
        int lastIndex = 0;
        SummingThread[] summingThreads = new SummingThread[threadsCount];
        for (int i = 0; i < threadsCount - 1; i++) {
            summingThreads[i] = new SummingThread(numbers, i + 1, lastIndex, lastIndex + elementsPerThread);
            lastIndex += elementsPerThread;
            summingThreads[i].start();
        }

        summingThreads[threadsCount - 1] = new SummingThread(numbers, threadsCount, lastIndex, arraySize);
        summingThreads[threadsCount - 1].start();

        for (SummingThread thread : summingThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int sumByThreads = 0;
        for (SummingThread thread : summingThreads) {
            sumByThreads += thread.getSum();
        }
        System.out.println("Sum by threads: " + sumByThreads);
    }

    private static int calculateSequentialSum(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }
}
