package game;

public final class FinalMoves {
    private Offer offer;
    private DrawAnswer answer;
    private Cell cell;

    public FinalMoves(){
        this.offer = Offer.NULL;
        this.answer = DrawAnswer.NULL;
        this.cell = Cell.X;
    }
    public Cell getCell(){
        return cell;
    }
    public DrawAnswer getAnswer(){
        return answer;
    }
    public Offer getOffer(){
        return offer;
    }
    public void nowCell(Cell cell){
        this.cell = cell;
    }
    public void nowAnswer(DrawAnswer answer){
        this.answer = answer;
    }
    public void nowOffer(Offer offer){
        this.offer = offer;
    }
}
