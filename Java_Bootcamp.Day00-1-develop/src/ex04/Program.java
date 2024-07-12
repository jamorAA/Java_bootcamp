import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if (line.isEmpty())
            System.exit(-1);

        int[] amount = new int[65536];
        for (char c : line.toCharArray())
            amount[c]++;

        int[] finalAmount = new int[10];
        char[] finalSym = new char[10];

        for (int i = 0; i < amount.length; ++i) {
            int tempAmount = amount[i];
            for (int j = 0; j < finalAmount.length; ++j) {
                if (tempAmount > finalAmount[j]) {
                    for (int k = finalAmount.length - 1; k > j; --k) {
                        finalAmount[k] = finalAmount[k - 1];
                        finalSym[k] = finalSym[k - 1];
                    }
                    finalAmount[j] = tempAmount;
                    finalSym[j] = (char) i;
                    break;
                } else if (tempAmount == finalAmount[j] && i < finalSym[j]) {
                    for (int k = finalAmount.length - 1; k > j; --k)
                        finalSym[k] = finalSym[k - 1];
                    finalSym[j] = (char) i;
                    break;
                }
            }
        }
        int maxAmount = finalAmount[0];
        int len = 0;
        for (int i = 0; i < 10; ++i) {
            if (finalAmount[i] > 0)
                ++len;
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < 10; ++i) {
            if (finalAmount[i] == maxAmount)
                System.out.print(finalAmount[i] + "\t");
        }
        System.out.println();
        for (int i = 10; i > 0; --i) {
            for (int j = 0; j < 10; ++j) {
                if (finalAmount[j] * 10 / maxAmount >= i)
                    System.out.print("#\t");
                if (finalAmount[j] * 10 / maxAmount == i - 1) {
                    if (finalAmount[j] != 0)
                        System.out.print(finalAmount[j] + "\t");
                }
            }
            System.out.println();
        }
        for (int i = 0; i < len; ++i)
            System.out.print(finalSym[i] + "\t");
    }
}

