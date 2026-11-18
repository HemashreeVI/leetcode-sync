class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;

        // Step 1: remove full circles
        k = k % n;

        // Step 2: put each element directly into its new position
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[(i + k) % n] = nums[i];
        }

        // Step 3: copy the result back into nums
        for (int i = 0; i < n; i++) {
            nums[i] = res[i];
        }
    }
}