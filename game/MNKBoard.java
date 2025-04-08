package game;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );
    private final int n, m, k;
    private int empty;
    private final Cell[][] cells;
    private Cell turn;
    private final FinalMoves finalMoves;
    private final boolean isDiamond;

    public MNKBoard(int m, int n, int k, boolean isDiamond) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.empty = m * n;
        this.isDiamond = isDiamond;
        if (isDiamond) {
            this.cells = new Cell[2 * m - 1][2 * n - 1];
            for (Cell[] row : cells) {
                Arrays.fill(row, Cell.E);
            }
        }
        else{
            this.cells = new Cell[m][n];
            for (Cell[] row : cells) {
                Arrays.fill(row, Cell.E);
            }
        }
        this.finalMoves = new FinalMoves();
        this.turn = Cell.X;
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
        if (move.getOffer() == Offer.SURRENDER){
            return Result.LOSE;
        }
        if (finalMoves.getOffer() == Offer.DRAW){
            if (move.getAnswer() == DrawAnswer.NO){
                finalMoves.nowAnswer(DrawAnswer.NO);
                turn = turn == Cell.X ? Cell.O : Cell.X;
                finalMoves.nowCell(turn);
                finalMoves.nowOffer(Offer.NULL);
                return Result.UNKNOWN;
            }
            else{
                return Result.DRAW;
            }
        }
        if (move.getOffer() == Offer.DRAW){
            finalMoves.nowOffer(Offer.DRAW);
            turn = turn == Cell.X ? Cell.O : Cell.X;
            finalMoves.nowCell(turn);
            return Result.UNKNOWN;
        }
        if (!isValid(move)) {
            return Result.LOSE;
        }
        finalMoves.nowOffer(Offer.NULL);
        finalMoves.nowAnswer(DrawAnswer.NULL);
        int x = move.getRow();
        int y = move.getColumn();
        int row = x, col = y, maxCol = n, maxRow = m;
        int countInRow = 0, countInCol = 0, countInDig1 = 0, countInDig2 = 0;
        cells[x][y] = move.getValue();
        if (isDiamond){
            maxCol = 2 * n - 1;
            maxRow = 2 * m - 1;
        }
        while (row < maxRow && col < maxCol && cells[row][col] == turn) {
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
        while (row >= 0 && col < maxCol && cells[row][col] == turn) {
            row--;
            col++;
            countInDig2 += 1;
        }
        row = x + 1;
        col = y - 1;
        while (row < maxRow && col >= 0 && cells[row][col] == turn){
            row++;
            col--;
            countInDig2 += 1;
        }
        row = x;
        col = y;
        while (row < maxRow && cells[row][col] == turn){
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
        while (col < maxRow && cells[row][col] == turn){
            col++;
            countInRow += 1;
        }
        col = y - 1;
        while (col >= 0 && cells[row][col] == turn){
            col--;
            countInRow += 1;
        }
        this.empty -= 1;
        if (countInCol >= k || countInRow >= k || countInDig1 >= k || countInDig2 >= k) {
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
        if (isDiamond) {
            int w = move.getRow();
            int q = move.getColumn();
            int row = w, col = q;
            if (w >= m) {
                row = m - (w % m) - 2;
            }
            if (q >= m) {
                col = m - (q % m) - 2;
            }
            return 0 <= w && w < (2 * m - 1)
                    && 0 <= q && q < (2 * n - 1)
                    && cells[w][q] == Cell.E
                    && turn == getCell()
                    && (row + col) >= (n - 1)
                    && (row + col) <= (3 * n - 3);
        }
        else{
            return 0 <= move.getRow() && move.getRow() < m
                    && 0 <= move.getColumn() && move.getColumn() < n
                    && cells[move.getRow()][move.getColumn()] == Cell.E
                    && turn == getCell();
        }
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public Offer getOffer() {
        return finalMoves.getOffer();
    }

    @Override
    public DrawAnswer getAnswer() {
        return finalMoves.getAnswer();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!isDiamond) {
            sb.append(" ");
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
        } else {
            sb.append("  ");
            for (int i = 0; i < 2 * n - 1; i++) {
                sb.append(i).append(" ");
                if (i <= 9) {
                    sb.append(" ");
                }
            }
            for (int r = 0; r < 2 * m - 1; r++) {
                sb.append("\n");
                sb.append(r);
                if (r <= 9) {
                    sb.append(" ");
                }
                for (int c = 0; c < 2 * m - 1; c++) {
                    int v = r;
                    if (r >= m) {
                        v = m - (r % m) - 2;
                    }
                    if (c >= (m - v - 1) && c <= (m + v - 1)) {
                        sb.append(SYMBOLS.get(cells[r][c])).append("  ");
                    } else {
                        sb.append("   ");
                    }
                }
            }
        }
        return sb.toString();
    }
}
