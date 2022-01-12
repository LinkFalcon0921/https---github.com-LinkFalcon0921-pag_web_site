package PagesDesign;

import java.util.Arrays;

public class code {
    public static int add(int num1, int num2) {
        String result = "";
        int lent;
        int leng;
        int[] val1;
        int[] val2;

        if (Math.max(num1, num2) == num1) {
            val1 = asIntArray(String.valueOf(num1).toCharArray());
            val2 = asIntArray(String.valueOf(num2).toCharArray());
        } else {
            val1 = asIntArray(String.valueOf(num2).toCharArray());
            val2 = asIntArray(String.valueOf(num1).toCharArray());
        }

        lent = val1.length;
        leng = val2.length;

        for (int T = lent - 1; T >= 0; T--) {
            if (leng > 0) {
                val1[T] += val2[--leng];
            }
            result = String.format("%d%s", val1[T], result);
        }

        return Integer.valueOf(result);
    }

    public static int[] asIntArray(char[] charArray) {
        int[] result = new int[charArray.length];
        Arrays.setAll(result, i -> (int) charArray[i] - '0');
        return result;
    }

    static int addt(int num1, int num2) {
        var sum = new StringBuilder();
        do {
            sum.insert(0, num1 % 10 + num2 % 10);
        } while ((num1 /= 10) + (num2 /= 10) > 0);
        return Integer.parseInt(sum.toString());
    }

    public static void main(String[] args) {
    }
}
