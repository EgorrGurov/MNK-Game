package game;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );
    private final int m, n, k;
    private int empty;

    private final Cell[][] cells;
    private Cell turn;

    public TicTacToeBoard(int m, int n, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.empty = m * n;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        int x = move.getRow();
        int y = move.getColumn();
        int row = x, col = y;
        int countInRow = 0, countInCol = 0, countInDig1 = 0, countInDig2 = 0;
        cells[x][y] = move.getValue();
        while (row < m && col < n && cells[row][col] == turn) {
            row++;
            col++;
            countInDig1 += 1;
        }
        row = x - 1;
        col = y - 1;
        while (row >= 0 && col >= 0 && cells[row][col] == turn) {
            row--;
            col--;
            countInDig1 += 1;
        }
        row = x;
        col = y;
        while (row >= 0 && col < n && cells[row][col] == turn) {
            row--;
            col++;
            countInDig2 += 1;
        }
        row = x + 1;
        col = y - 1;
        while (row < m && col >= 0 && cells[row][col] == turn){
            row++;
            col--;
            countInDig2 += 1;
        }
        row = x;
        col = y;
        while (row < m && cells[row][col] == turn){
            row++;
            countInCol += 1;
        }
        row = x - 1;
        while (row >= 0 && cells[row][col] == turn){
            row--;
            countInCol += 1;
        }
        row = x;
        col = y;
        while (col < n && cells[row][col] == turn){
            col++;
            countInRow += 1;
        }
        col = y - 1;
        while (col >= 0 && cells[row][col] == turn){
            col--;
            countInRow += 1;
        }
//        int iUp = Math.max(0, x - k + 1), iDown = Math.min(x + k, m);
//        while (iUp < iDown){
//            if (cells[iUp][y] == turn){
//                countInCol += 1;
//            }
//            else{
//                if (countInCol < k){
//                    countInCol = 0;
//                }
//            }
//            iUp++;
//        }
//        int iLeft = Math.max(0, y - k + 1), iRight = Math.min(y + k, n);
//        while (iLeft < iRight){
//            if (cells[x][iLeft] == turn){
//                countInRow += 1;
//            }
//            else{
//                if (countInRow < k){
//                    countInRow = 0;
//                }
//            }
//            iLeft++;
//        }
//        int iDig1Up = Math.max(0, x - k + 1), iDig1Down = Math.min(m, x + k);
//        int jDig1Left = Math.max(0, y - k + 1), jDig1Right = Math.min(n, y + k);
//        while (iDig1Up < iDig1Down && jDig1Left < jDig1Right){
//            if (cells[iDig1Up][jDig1Left] == turn){
//                countInDig1 += 1;
//            }
//            else{
//                if (countInDig1 < k){
//                    countInDig1 = 0;
//                }
//            }
//            iDig1Up++;
//            jDig1Left++;
//        }
//        int iDig2Down = Math.min(m, x + k - 1), iDig2Up = Math.max(0, x - k);
//        int jDig2Left = Math.max(0, y - k + 1), jDig2Right = Math.min(n, y + k);
//        while (iDig2Down < iDig2Up && jDig2Left < jDig2Right){
//            if (cells[iDig2Down][jDig2Left] == turn){
//                countInDig2 += 1;
//            }
//            else{
//                if (countInDig2 < k){
//                    countInDig2 = 0;
//                }
//            }
//            iDig2Down--;
//            jDig2Left++;
//        }
        this.empty -= 1;
        if (countInCol >= k || countInRow >= k || countInDig1 >= k || countInDig2 >= k){
            return Result.WIN;
        }
        if (this.empty <= 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getColumn() && move.getColumn() < n
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public Offer getOffer() {
        return null;
    }

    @Override
    public DrawAnswer getAnswer() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append(" ");
        for (int i = 0; i < n; i++){
            sb.append(i);
        }
        for (int r = 0; r < m; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
