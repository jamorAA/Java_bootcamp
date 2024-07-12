import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        int num = 0;
        while (num != 42) {
            num = scanner.nextInt();
            if (isPrime(sum(num)))
                count++;
        }
        System.out.println("Count of coffee-request – " + count);
    }

    private static int sum(int num) {
        int result = 0;
        while (num > 0) {
            result += num % 10;
            num = num / 10;
        }
        return result;
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        int i = 2;
        for (; i * i <= num; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }
}

