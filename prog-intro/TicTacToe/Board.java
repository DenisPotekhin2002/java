package TicTacToe;

public interface Board {
    Position getPosition();
    Cell getCell();
    Result makeMove(final int num, final int m, final int n, final int k, Move move);
}
