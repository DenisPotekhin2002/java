package TicTacToe;

public class SequentialPlayer implements Player {
    @Override
    public Move move(final int romb, final int num, final int m, final int n, final int k, final Position position, final Cell cell) {
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                final Move move = new Move(m, n, k, r, c, cell);
                if (position.isValid(romb, m, n, k, move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
