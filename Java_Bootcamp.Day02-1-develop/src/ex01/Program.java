import java.io.*;
import java.util.*;

public class Program {
    private static final List<String> dict = new ArrayList<String>() {};
    private static final ArrayList<String> input1 = new ArrayList<String>(){};
    private static final ArrayList<String> input2 = new ArrayList<String>(){};

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Error!");
            System.exit(-1);
        }
        try {
            BufferedWriter dictFile = new BufferedWriter(new FileWriter("dictionary.txt"));
            BufferedReader file1 = new BufferedReader(new FileReader(args[0]));
            BufferedReader file2 = new BufferedReader(new FileReader(args[1]));
            parseFile(file1, input1);
            parseFile(file2, input2);
            file1.close();
            file2.close();
            for (String elem : dict) {
                dictFile.write(elem);
                dictFile.newLine();
            }
            dictFile.close();
            findSimilarity();
        } catch (IOException iox) {
            System.out.println(iox);
        }
    }
    private static void parseFile(BufferedReader file, List<String> lst) throws IOException {
        String s;
        while((s = file.readLine()) != null) {
            s = s.replaceAll("\\p{Punct}", "");
            String[] words = s.split(" ");
            for (String word : words) {
                if (!word.isEmpty()) {
                    lst.add(word);
                    if (!dict.contains(word))
                        dict.add(word);
                }
            }
        }
    }
    private static void findSimilarity() {
        Map<String, Integer> array1 = new HashMap<String, Integer>(){};
        Map<String, Integer> array2 = new HashMap<String, Integer>(){};
        int count;
        for (String word : dict) {
            count = 0;
            for (String sym : input1) {
                if (word.equals(sym)) {
                    count += 1;
                    array1.put(word, count);
                }
            }
            if (!array1.containsKey(word))
                array1.put(word, 0);
            count = 0;
            for (String sym : input2) {
                if (word.equals(sym)) {
                    count += 1;
                    array2.put(word, count);
                }
            }
            if (!array2.containsKey(word))
                array2.put(word, 0);
        }
        ArrayList<Integer> values1 = new ArrayList<Integer>(array1.values()){};
        ArrayList<Integer> values2 = new ArrayList<Integer>(array2.values()){};

        int numerator = 0;
        double denominator1 = 0.0, denominator2 = 0.0;
        for(int i = 0; i < values2.size(); ++i) {
            numerator += values1.get(i) * values2.get(i);
            denominator1 += values1.get(i) * values1.get(i);
            denominator2 += values2.get(i) * values2.get(i);
        }
        double similarity = numerator / (Math.sqrt(denominator1) * Math.sqrt(denominator2));
        System.out.printf("%.2f", Math.floor(similarity * 100) / 100);
    }
}
