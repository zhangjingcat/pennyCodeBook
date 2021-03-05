public class pennyTest {


    public static int XiaoMingTest() {
        int[] inputArray = new int[]{1,8,3,1,5};
        if (inputArray.length == 0) {
            return 0;
        } else if (inputArray.length == 1) {
            return inputArray[0];
        } else if (inputArray.length == 2) {
            return Math.max(inputArray[1],inputArray[0]);
        } else if (inputArray.length == 3) {
            return Math.max(inputArray[0] + inputArray[2],inputArray[1]);
        }
        int[] dp = new int[inputArray.length];
        dp[0] = inputArray[0];
        dp[1] = Math.max(dp[0], inputArray[1]);
        System.out.println("dp[0]=" + dp[0] + "dp[1]" + dp[1]);
        // dp数组代表含有i的最优选择
        for(int i=2;i<inputArray.length ;i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + inputArray[i]);
            System.out.println("i=" + i + "dp[i]=" + dp[i]);
        }
        System.out.println("dp[inputArray.length]" + dp[inputArray.length -1]);
        return dp[inputArray.length -1];
    }

    public static void main(String[] args) {
        XiaoMingTest();
    }
}
