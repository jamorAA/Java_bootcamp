import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] students = new String[10];
        String[][] schedule = new String[7][10];
        String[][][][] attendances = new String[10][30][10][1];
        String student;
        String date;
        String time;
        String attendance;
        for (int i = 0; !(student = scanner.next()).equals("."); ++i)
            students[i] = student;
        while (!(time = scanner.next()).equals(".")) {
            date = scanner.next();
            fill(schedule, date, time);
        }
        while (!(student = scanner.next()).equals(".")) {
            scanner.next();
            date = scanner.next();
            attendance = scanner.next();
            for (int i = 0; i < students.length; ++i) {
                if (students[i].equals(student)) {
                    attendances[i][Integer.parseInt(date) - 1][0][0] = attendance;
                    break;
                }
            }
        }
        for (int i = 0; i <= 30; ++i)
            draw(i, schedule);
        System.out.println();
        for (int i = 0; i < students.length && students[i] != null; ++i) {
            System.out.printf("%10s", students[i]);
            draw(attendances[i], schedule);
        }
    }
    private static void fill(String[][] schedule, String temp, String time) {
        switch (temp) {
            case "MO":
                schedule[0][0] = time;
                schedule[0][1] = temp;
                break;
            case "TU":
                schedule[1][0] = time;
                schedule[1][1] = temp;
                break;
            case "WE":
                schedule[2][0] = time;
                schedule[2][1] = temp;
                break;
            case "TH":
                schedule[3][0] = time;
                schedule[3][1] = temp;
                break;
            case "FR":
                schedule[4][0] = time;
                schedule[4][1] = temp;
                break;
            case "SA":
                schedule[5][0] = time;
                schedule[5][1] = temp;
                break;
            case "SU":
                schedule[6][0] = time;
                schedule[6][1] = temp;
                break;
        }
    }
    private static void draw(String[][][] attendances, String[][] schedule) {
        for (int i = 0; i < 30; ++i) {
            for (int j = 0; j < 1 && schedule[(i + 1) % 7][j] != null; ++j) {
                if (attendances[i][j][0] != null && attendances[i][j][0].equals("HERE"))
                    System.out.printf("        %2d|", 1);
                else if (attendances[i][j][0] != null && attendances[i][j][0].equals("NOT_HERE"))
                    System.out.printf("        %2d|", -1);
                else
                    System.out.print("          |");
            }
        }
        System.out.println();
    }
    private static void draw(int i, String[][] schedule) {
        int j = 0;
        if (i == 0)
            System.out.print("          ");
        int day = ++i % 7;
        while (j < 1 && schedule[day][j] != null) {
            System.out.print(schedule[day][j] + ":00 ");
            if (day == 0)
                System.out.printf("MO %2d|", i);
            else if (day == 1)
                System.out.printf("TU %2d|", i);
            else if (day == 2)
                System.out.printf("WE %2d|", i);
            else if (day == 3)
                System.out.printf("TH %2d|", i);
            else if (day == 4)
                System.out.printf("FR %2d|", i);
            else if (day == 5)
                System.out.printf("SA %2d|", i);
            else
                System.out.printf("SU %2d|", i);
            j++;
        }
    }
}

