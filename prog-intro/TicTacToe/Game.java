package TicTacToe;

import java.util.ArrayList;

public class Game {
    private final boolean log;
    private final ArrayList<Player> list;
    private final int m;
    private final int n;
    private final int k;
    private final int num;
    private final int romb;

    public Game(final int romb, final int num, final int m, final int n, final int k, final boolean log, final ArrayList<Player> list) {
        this.log = log;
        this.list = list;
        this.m = m;
        this.n = n;
        this.k = k;
        this.num = num;
        this.romb = romb;
    }

    public int play(Board board) {

        while (true) {
            for (int i = 1; i <= list.size(); i++) {
                int result = -1;
                if (list.get(i - 1) != null) {
                    result = move(board, list.get(i - 1), i);
                }
                if (result != -1) {
                    return result;
                }
            }
        }
    }

    private int move(final Board board, final Player player, final int no) {
        final Move move = player.move(romb, num, m, n, k, board.getPosition(), board.getCell());
        final Result result = board.makeMove(num, m, n, k, move);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return num + 1 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else if (result == Result.UNKNOWN) {
            //log("Unknown");
            return -1;
        } else {
            throw new AssertionError("Impossible");
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
