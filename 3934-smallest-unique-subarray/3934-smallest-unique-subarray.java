
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        int[] sa = buildSuffixArray(nums);
        int[] lcp = buildLCP(nums, sa);

        int ans = n;

        for (int i = 0; i < n; i++) {
            int maxLCP = 0;

            if (i > 0) {
                maxLCP = Math.max(maxLCP, lcp[i]);
            }

            if (i < n - 1) {
                maxLCP = Math.max(maxLCP, lcp[i + 1]);
            }

            int start = sa[i];
            int remaining = n - start;

            if (maxLCP < remaining) {
                ans = Math.min(ans, maxLCP + 1);
            }
        }

        return ans;
    }

    private int[] buildSuffixArray(int[] nums) {
        int n = nums.length;
        int N = n + 1;

        int[] p = new int[N];
        int[] c = new int[N];

        int maxVal = Math.max(n, 100000);
        int[] cnt = new int[maxVal + 1];

        // Initial sorting (0 is the sentinel)
        for (int x : nums) {
            cnt[x]++;
        }
        cnt[0]++;

        for (int i = 1; i <= maxVal; i++) {
            cnt[i] += cnt[i - 1];
        }

        for (int i = n; i >= 0; i--) {
            int val = (i == n) ? 0 : nums[i];
            p[--cnt[val]] = i;
        }

        int classes = 1;
        c[p[0]] = 0;

        for (int i = 1; i < N; i++) {
            int cur = (p[i] == n) ? 0 : nums[p[i]];
            int prev = (p[i - 1] == n) ? 0 : nums[p[i - 1]];

            if (cur != prev) {
                classes++;
            }

            c[p[i]] = classes - 1;
        }

        int[] pn = new int[N];
        int[] cn = new int[N];

        for (int len = 1; len < N; len *= 2) {

            for (int i = 0; i < N; i++) {
                pn[i] = p[i] - len;

                if (pn[i] < 0) {
                    pn[i] += N;
                }
            }

            Arrays.fill(cnt, 0, classes, 0);

            for (int i = 0; i < N; i++) {
                cnt[c[pn[i]]]++;
            }

            for (int i = 1; i < classes; i++) {
                cnt[i] += cnt[i - 1];
            }

            for (int i = N - 1; i >= 0; i--) {
                int x = pn[i];
                p[--cnt[c[x]]] = x;
            }

            cn[p[0]] = 0;
            int newClasses = 1;

            for (int i = 1; i < N; i++) {
                int cur = p[i];
                int prev = p[i - 1];

                int mid1 = (cur + len) % N;
                int mid2 = (prev + len) % N;

                if (c[cur] != c[prev] ||
                    c[mid1] != c[mid2]) {
                    newClasses++;
                }

                cn[cur] = newClasses - 1;
            }

            int[] temp = c;
            c = cn;
            cn = temp;

            classes = newClasses;

            if (classes == N) {
                break;
            }
        }

        // Remove the sentinel suffix
        int[] sa = new int[n];
        int idx = 0;

        for (int x : p) {
            if (x != n) {
                sa[idx++] = x;
            }
        }

        return sa;
    }

    private int[] buildLCP(int[] nums, int[] sa) {
        int n = nums.length;

        int[] rank = new int[n];
        int[] lcp = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int k = 0;

        for (int i = 0; i < n; i++) {
            int r = rank[i];

            if (r == 0) {
                continue;
            }

            int j = sa[r - 1];

            while (i + k < n && j + k < n &&
                   nums[i + k] == nums[j + k]) {
                k++;
            }

            lcp[r] = k;

            if (k > 0) {
                k--;
            }
        }

        return lcp;
    }
}
