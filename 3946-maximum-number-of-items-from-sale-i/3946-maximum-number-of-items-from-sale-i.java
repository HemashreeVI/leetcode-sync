class Solution {
   public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        // Step 1: value of activating each item = 1 (the copy) + free copies it gives
        int[] value = new int[n];
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int bonus = 0;
            for (int j = 0; j < n; j++) {
                if (j != i && items[j][0] % items[i][0] == 0) {  // factor_i divides factor_j
                    bonus++;
                }
            }
            value[i] = 1 + bonus;
            minPrice = Math.min(minPrice, items[i][1]);
        }

        // Step 2: 0/1 knapsack. dp[c] = best value when activations cost EXACTLY c (-1 = impossible)
        int[] dp = new int[budget + 1];
        java.util.Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            for (int c = budget; c >= price; c--) {         // go backwards so each item is used once
                if (dp[c - price] != -1) {
                    dp[c] = Math.max(dp[c], dp[c - price] + value[i]);
                }
            }
        }

        // Step 3: leftover money buys the cheapest item, 1 copy each
        int best = 0;
        for (int c = 0; c <= budget; c++) {
            if (dp[c] != -1) {
                best = Math.max(best, dp[c] + (budget - c) / minPrice);
            }
        }
        return best;
    }
}