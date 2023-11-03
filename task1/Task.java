public class Task {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        int[] array = new int[m * n];
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < m * n; i++) {
            array[i] = (i % n) + 1;
        }

        for (int i = m - 1; i < m * n; i += (m - 1)) {
            if (array[i] != 1) {
                result.append(array[i - (m - 1)]);
            } else {
                result.append(array[i - (m - 1)]);
                break;
            }
        }

        System.out.println(result.toString());
    }
}