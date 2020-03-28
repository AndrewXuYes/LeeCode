package LeeCode;

/**
 * @author andrew
 * @date 2020/3/28 15:14
 */
public class _55_canJump {


    public static boolean canJump(int[] nums) {
        int lastPos = nums.length - 1;

        for (int i = nums.length - 1; i >= 0; i--)
            if (i + nums[i] >= lastPos)
                lastPos = i;

        return lastPos == 0;
    }


    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(canJump(nums));
    }
}
