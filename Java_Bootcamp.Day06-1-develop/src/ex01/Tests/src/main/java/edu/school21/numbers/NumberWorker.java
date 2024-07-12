package edu.school21.numbers;

public class NumberWorker {
    public static void main(String[] args) {

    }
    public boolean isPrime(int number) {
        if (number <= 1)
            throw new IllegalArgumentException("Error");

        for (int i = 2; i * i <= number; ++i) {
            if (number % i == 0)
                return false;
        }
        return true;
    }
    public int digitsSum(int number) {
        if (number < 0)
            number = -number;
        int s;
        for (s = 0; number != 0; number /= 10)
            s += number % 10;
        return s;
    }
}