package game;


public interface Board {
    Position getPosition();
    Cell getCell();
//    DrawAnswer getAnswer();
//    Offer getOffer();
    Result makeMove(Move move);
}
