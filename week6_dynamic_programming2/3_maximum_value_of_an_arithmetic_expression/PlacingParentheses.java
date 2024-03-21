import java.util.Scanner;

public class PlacingParentheses {
    private static long getMaximValue(String exp) {
        int length = exp.length() / 2 + 1;
        long[][] maxValues = new long[length][length];
        long[][] minValues = new long[length][length];

        for (int i = 0; i < length; i++) {
            maxValues[i][i] = Character.getNumericValue(exp.charAt(2 * i));
            minValues[i][i] = Character.getNumericValue(exp.charAt(2 * i));
        }

        for (int s = 0; s < length - 1; s++) {
            for (int i = 0; i < length - s - 1; i++) {
                int j = i + s + 1;
                long[] minMax = minAndMax(i, j, exp, minValues, maxValues);
                minValues[i][j] = minMax[0];
                maxValues[i][j] = minMax[1];
            }
        }

        return maxValues[0][length - 1];
    }

    private static long[] minAndMax(int i, int j, String exp, long[][] minValues, long[][] maxValues) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int k = i; k < j; k++) {
            char op = exp.charAt(2 * k + 1);
            long a = eval(maxValues[i][k], maxValues[k + 1][j], op);
            long b = eval(maxValues[i][k], minValues[k + 1][j], op);
            long c = eval(minValues[i][k], maxValues[k + 1][j], op);
            long d = eval(minValues[i][k], minValues[k + 1][j], op);
            min = Math.min(min, Math.min(a, Math.min(b, Math.min(c, d))));
            max = Math.max(max, Math.max(a, Math.max(b, Math.max(c, d))));
        }
        return new long[]{min, max};
    }

    private static long eval(long a, long b, char op) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            throw new IllegalArgumentException("Unsupported operator: " + op);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}
