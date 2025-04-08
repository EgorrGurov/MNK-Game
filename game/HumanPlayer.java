package game;

import java.io.PrintStream;
import java.util.Scanner;


public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            final Move move;
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            if (position.getOffer() == Offer.DRAW){
                out.println("okay, do you agree?");
                String ans = in.next();
                if (ans.equals("Yes")){
                    move = new Move(DrawAnswer.YES);
                    return move;
                }
                else{
                    move = new Move(DrawAnswer.NO);
                    return move;
                }
            }
            out.println("Do you wanna draw?");
            String ans1 = in.next();
            if (ans1.equals("Yes")){
                move = new Move(Offer.DRAW);
                return move;
            }
            out.println("Surrender?");
            String ans2 = in.next();
            if (ans2.equals("Yes")){
                move = new Move(Offer.SURRENDER);
                return move;
            }
            out.println("Enter row and column");
            move = new Move(in.nextInt(), in.nextInt(), cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
}
