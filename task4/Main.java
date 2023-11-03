import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {


        String fileName = args[0];
        List<Integer> nums = readListFromFile(fileName);


        int minMoves = findMinMoves(nums);
        System.out.println(minMoves);
    }

    private static List<Integer> readListFromFile(String fileName) {
        List<Integer> nums = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(" ");

                for (String value : values) {
                    nums.add(Integer.parseInt(value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nums;
    }

    private static int findMinMoves(List<Integer> nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        int minMoves = Integer.MAX_VALUE;
        for (int target = min; target <= max; target++) {
            int moves = 0;
            for (int num : nums) {
                moves += Math.abs(num - target);
            }
            minMoves = Math.min(minMoves, moves);
        }

        return minMoves;
    }
}
