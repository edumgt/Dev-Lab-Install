public class ArrayRefObjExample {
    public static void main(String[] args) {
        String[] strArray = new String[3];
        strArray[0] = "java1";
        strArray[1] = "java1";
        strArray[2] = new String("java1");
        int[] oldIntArr = { 1, 2, 3 };
        int[] newIntArr = new int[5];
        for (int i = 1; i < oldIntArr.length; i++) {
            newIntArr[i] = oldIntArr[i];
        }

        String[] oldStrArr = { "java", "html", "react" };
        String[] newStrArr = new String[5];

        System.arraycopy(oldStrArr, 0, newStrArr, 2, 3);

        for (int i = 0; i < newIntArr.length; i++) {
            System.out.println(newStrArr[i]);
        }

        int[] scores = { 99, 22, 33, 77, 88 };
        int sum = 0;
        for (int score : scores) {
            sum = sum + score;
        }

        System.out.println("sum -- " + sum);

        int[][][] scores1 = { { { 99, 22, 33, 77, 88 }, { 99, 22, 33, 77, 88 } },
                { { 99, 22, 33, 77, 88 }, { 99, 22, 33, 77, 88 } } };

    }
}
