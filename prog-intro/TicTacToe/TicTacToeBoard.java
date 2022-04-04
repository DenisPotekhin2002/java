package TicTacToe;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board {
    private final int m;
    private final int n;
    private final int romb;
    private int empty;

    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.H, '-',
            Cell.D, '|',
            Cell.E, '.',
            Cell.I, ' '
    );

    private final Cell[][] cells;
    private final Position position;
    private Cell turn = Cell.X;

    public class GamePosition implements Position {

        public Cell getCell() {
            return turn;
        }

        @Override
        public Cell getCell(final int r, final int c) {
            return cells[r][c];
        }

        @Override
        public boolean isValid(final int romb, final int m, final int n, final int k, final Move move) {
            if (romb == 0) {
                return 0 <= move.getRow() && move.getRow() < m
                        && 0 <= move.getColumn() && move.getColumn() < n
                        && cells[move.getRow()][move.getColumn()] == Cell.E
                        && turn == getCell();
            }
            else {
                int mid = (m - 1)/2;
                return 0 <= move.getRow() && move.getRow() < m && 0 <= move.getColumn() && move.getColumn() < n
                            && Math.abs(mid - move.getRow()) + Math.abs(mid - move.getColumn()) <= mid
                            && cells[move.getRow()][move.getColumn()] == Cell.E
                            && turn == getCell();
            }
        }
    }

    public TicTacToeBoard(final int romb, final int num, final int m, final int n, final int k) {
        this.cells = new Cell[m][n];
        this.m = m;
        this.n = n;
        this.empty = m * n;
        this.romb = romb;
        if (romb == 0) {
            for (Cell[] row : cells) {
                Arrays.fill(row, Cell.E);
            }
        }
        else {
            for (int rowcount = 0; rowcount < m; rowcount++){
                for (int columncount = 0; columncount < m; columncount++){
                    int midtemp = (m - 1)/2;
                    if (Math.abs(midtemp - rowcount) + Math.abs(midtemp - columncount) <= midtemp) {
                        cells[rowcount][columncount] = Cell.E;
                    } else {
                        cells[rowcount][columncount] = Cell.I;
                    }
                }
            }
        }
        this.position = new GamePosition();
    }


    @Override
    public Position getPosition() {
        return position;
    }

    public int check(int count1, int count2, int row, int column, Cell turn, Cell[][] cells){
        int inLine = 0;
        int rowtemp = row;
        int columntemp = column;
        while (rowtemp < m && columntemp < n &&
                rowtemp >= 0 && columntemp >= 0 &&
                cells[rowtemp][columntemp] == turn) {
            inLine++;
            rowtemp += count1;
            columntemp += count2;
        }
        return inLine;
    }

    @Override
    public Result makeMove(final int num, final int m, final int n, final int k, final Move move) {
        if (!position.isValid(romb, m, n, k, move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();
        int row = move.getRow();
        int column = move.getColumn();

        for (int count1 = 0; count1 <= 1; count1++){
            for (int count2 = -1; count2 <= 1; count2++){
                if (count1 != 0 || (count2 != 0 && count2 != -1)){
                    if (check(count1, count2, row, column, turn, cells) + check(-count1, -count2, row, column, turn, cells) >= k + 1) {
                        return Result.WIN;
                    }
                }
            }
        }

        empty--;
        if ( (romb == 0 && empty == 0) || (romb == 1 && empty == ((m - 1) / 2 + 1) * (m - 1)) ) {
            return Result.DRAW;
        }

        Cell[] queue = new Cell[4];
        queue[0] = Cell.X;
        queue[1] = Cell.O;
        queue[2] = Cell.H;
        queue[3] = Cell.D;

        for (int j = 0; j < num; j++){
            if (queue[j] == turn){
                if (j < num - 1){
                    turn = queue[j + 1];
                } else {
                    turn = queue[0];
                }
                break;
            }
        }
        //turn = turn == Cell.X ? Cell.O : Cell.X;

        return Result.UNKNOWN;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public String toString() {
        int max = m;
        if (n > max){
            max = n;
        }
        int len = (int)(Math.log(max - 1) / Math.log(2));
        final StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(" ".repeat(len - 2));
        for (int r = 0; r < n; r++) {
            //StringBuilder temp = new StringBuilder(Integer.toString(r));
            //temp.append(" ".repeat(len - 1 - temp.length()));
            sb.append(r + " ".repeat(len - 1 - Integer.toString(r).length()));
        }
        for (int r = 0; r < m; r++) {
            sb.append("\n");
            //StringBuilder temp = new StringBuilder(Integer.toString(r));
            //temp.append(" ".repeat(len - 1 - temp.length()));
            sb.append(r + " ".repeat(len - 1 - Integer.toString(r).length()));
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
                sb.append(" ".repeat(len - 2));
            }
        }
        return sb.toString();
    }
}
