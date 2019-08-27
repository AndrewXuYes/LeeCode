package LeeCode;

public class _48_rotate {
    public static int[][] rotate(int[][] matrix) {
        System.out.println("n==" + (matrix.length));
        if (matrix.length == 0 || matrix.length != matrix[0].length) {
            return new int[0][0];
        }
        int nums = matrix.length;
        int times = 0;
        //移位运算符就是在二进制的基础上对数字进行平移
        while (times <= (nums >> 1)) {
            int len = nums - (times << 1);
            for (int i = 0; i < len - 1; i++) {
                int temp = matrix[times][times + i];
                matrix[times][times + i] = matrix[times + len - i - 1][times];
                matrix[times + len - i - 1][times] = matrix[times + len - 1][times + len - i - 1];
                matrix[times + len - 1][times + len - i - 1] = matrix[times + i][times + len - 1];
                matrix[times + i][times + len - 1] = temp;
            }
            ++times;
        }
        return matrix;
    }

    public static void main(String[] args) {

        int[][] a = new int[5][5];
        rotate(a);
    }

}