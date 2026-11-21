
import java.util.*;

class Solution {
    int[] answer;
    int[] newParent;
    List<Integer>[] children;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        answer = new int[n];
        newParent = parent.clone();

        // Build original tree
        children = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        // Find new parents using DFS
        int[] last = new int[26];
        Arrays.fill(last, -1);

        dfs(0, s, last);

        // Build modified tree
        List<Integer>[] updated = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            updated[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            updated[newParent[i]].add(i);
        }

        // Calculate subtree sizes
        calculateSize(0, updated);

        return answer;
    }

    void dfs(int node, String s, int[] last) {

        int ch = s.charAt(node) - 'a';

        int old = last[ch];

        if (old != -1) {
            newParent[node] = old;
        }

        last[ch] = node;

        for (int child : children[node]) {
            dfs(child, s, last);
        }

        // Backtrack
        last[ch] = old;
    }

    int calculateSize(int node, List<Integer>[] updated) {

        int size = 1;

        for (int child : updated[node]) {
            size += calculateSize(child, updated);
        }

        answer[node] = size;

        return size;
    }
}
