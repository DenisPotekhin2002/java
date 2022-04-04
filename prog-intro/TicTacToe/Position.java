package TicTacToe;

public interface Position {
    boolean isValid(final int romb, final int m, final int n, final int k, Move move);

    Cell getCell(int r, int c);
}
