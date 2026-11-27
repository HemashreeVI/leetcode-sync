class Solution {
    public boolean winnerSquareGame(int n) {
        boolean[] win = new boolean[n + 1];   // win[i] = can the player to move win with i stones?
        // win[0] = false by default (no move = lose)

        for (int i = 1; i <= n; i++) {
            for (int k = 1; k * k <= i; k++) {      // try every square: 1, 4, 9, ...
                if (!win[i - k * k]) {              // this move leaves opponent in a LOSING spot
                    win[i] = true;                  // so I win
                    break;                          // no need to try other moves
                }
            }
        }
        return win[n];
    }
}