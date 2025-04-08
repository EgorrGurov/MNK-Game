package game;

public class BoardInPosition implements Position{

    private MNKBoard board;

    public BoardInPosition(MNKBoard ourBoard) {
        board = ourBoard;
    }

    public Cell getCell() {
        return board.getCell();
    }

    @Override
    public boolean isValid(final Move move) {
        return board.isValid(move);
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return board.getCell(r, c);
    }

    @Override
    public Offer getOffer() {
        return board.getOffer();
    }

    @Override
    public DrawAnswer getAnswer() {
        return board.getAnswer();
    }

    @Override
    public String toString() {
        return board.toString();
    }
}