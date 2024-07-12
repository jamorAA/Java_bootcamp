import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long result = 0;
        int i = 1;
        for (String line = scanner.nextLine(); !line.equals("42") && i <= 18; ++i, line = scanner.nextLine()) {
            if (line.equals("Week " + i)) {
                int tempMin = 10;
                for (int j = 0; j < 5; ++j) {
                    int tempNum = scanner.nextInt();
                    if (tempNum < 1 || tempNum > 10)
                        error();
                    if (tempNum < tempMin)
                        tempMin = tempNum;
                }
                result = result * 10 + tempMin;
            } else {
                error();
            }
            scanner.nextLine();
        }
        printResult(i - 1, result);
    }

    private static void error() {
        System.out.println("IllegalArgument");
        System.exit(-1);
    }

    private static void printResult(int count, long result) {
        long num = 1;
        for (int i = 0; i < count - 1; ++i)
            num *= 10;
        for (int i = 1; i <= count; ++i) {
            System.out.print("Week " + i + " ");
            for (int j = 0; j < result / num; ++j)
                System.out.print("=");
            System.out.println(">");
            result = result - (result / num) * num;
            num /= 10;
        }
    }
}

