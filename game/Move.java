package game;

public final class Move {
    private final int row;
    private final int column;
    private final Cell value;
    private final Offer offer;
    private final DrawAnswer answer;
    public Move(final int row, final int column, final Cell value) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.answer = DrawAnswer.NULL;
        this.offer = Offer.NULL;
    }
    public Move(Offer offer) {
        this.row = -1;
        this.column = -1;
        this.value = Cell.E;
        this.offer = offer;
        this.answer = DrawAnswer.NULL;
    }
    public Move(DrawAnswer answer) {
        this.row = -1;
        this.column = -1;
        this.value = Cell.E;
        this.offer = Offer.DRAW;
        this.answer = answer;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Cell getValue() {
        return value;
    }

    public Offer getOffer() { return this.offer; }

    public DrawAnswer getAnswer() { return this.answer; }



    @Override
    public String toString() {
        return "row=" + row + ", column=" + column + ", value=" + value;
    }
}
