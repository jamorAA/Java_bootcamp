import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            if (num <= 1) {
                System.out.println("Illegal Argument");
                System.exit(-1);
            }
            int i = 2;
            for (; i * i <= num; i++) {
                if (num % i == 0) {
                    System.out.println("false " + --i);
                    System.exit(0);
                }
            }
            System.out.println("true " + --i);
        }
    }
}

