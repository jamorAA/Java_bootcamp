class SummingThread extends Thread {
    private final int[] numbers;

    private final int count;
    private final int start;
    private final int end;
    private int partialSum;

    SummingThread(int[] numbers, int count, int start, int end) {
        this.numbers = numbers;
        this.count = count;
        this.start = start;
        this.end = end;
    }

    public void run() {
        partialSum = 0;
        for (int i = start; i < end; i++)
            partialSum += numbers[i];
        System.out.println("Thread " + count + ": from " + start + " to " + (end - 1) + " sum is " + partialSum);
    }

    public int getSum() {
        return partialSum;
    }
}
