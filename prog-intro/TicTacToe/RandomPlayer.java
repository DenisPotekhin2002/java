package TicTacToe;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final int romb, final int num, final int m, final int n, final int k, final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(m);
            int c = random.nextInt(n);
            final Move move = new Move(m, n, k, r, c, cell);

            if (position.isValid(romb, m, n, k, move)) {
                return move;
            }
        }
    }
}
