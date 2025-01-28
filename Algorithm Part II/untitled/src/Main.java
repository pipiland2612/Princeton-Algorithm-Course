import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(generateSubset(new String[]{}));
    }

    public static List<List<String>> generateSubset(String[] strings) {
        List<List<String>> results = new ArrayList<>();
        subsetHelper(results, strings, new ArrayList<>(), 0);
        return results;
    }

    private static void subsetHelper(List<List<String>> results, String[] strings, List<String> subset, int start) {
        results.add(new ArrayList<>(subset));
        for (int i = start; i < strings.length; i++) {
            subset.add(strings[i]);
            subsetHelper(results,strings, subset, i + 1);
            subset.remove(subset.size() - 1);
        }
    }
}
