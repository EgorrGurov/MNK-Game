package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Which board do you wanna play? [Diamond/Rectangle]");
        String typeBoard = in.next();
        int tries = 0;
        //note: save me from ctrl d
        while (!typeBoard.equals("Diamond") && !typeBoard.equals("Rectangle") && tries < 2) {
            System.out.println("Type of board must be Diamond or Rectangle");
            typeBoard = in.next();
            tries++;
        }
        if (!typeBoard.equals("Diamond") && !typeBoard.equals("Rectangle")) {
            System.out.println("You try to break my game. Go out of here");
            return;
        }
        System.out.println("Enter parameters (m, n, k) for play game");
        int m = in.nextInt();
        int n = in.nextInt();
        int k = in.nextInt();
        if (typeBoard.equals("Diamond")) {
            int cnt = 0;
            while (m != n && cnt < 2) {
                cnt += 1;
                System.out.println("In diamond board m must be equals n. Enter m, n, k again");
                m = in.nextInt();
                n = in.nextInt();
                k = in.nextInt();
            }
            if (cnt == 2 && m != n) {
                System.out.println("You try break my game! Go out of here");
                return;
            }
        }
        System.out.println("Do you wanna to hold a tournament? [Yes/No]");
        String answer = in.next();
        if (answer.equals("Yes")) {
            System.out.println("How many players will be play?");
            int countPlayers = in.nextInt();
            System.out.println("Okay, how many Random player will be played? [0 to " + countPlayers + "]");
            int countRandom = in.nextInt();
            int cntTries = 0;
            while ((countRandom < 0 || countRandom > countPlayers) && cntTries < 2){
                System.out.println("Count random players must be in [0 to " + countPlayers + "]");
                countRandom = in.nextInt();
                cntTries++;
            }
            if (countRandom < 0 || countRandom > countPlayers){
                System.out.println("You try to break my game. Go out of here.");
                return;
            }
            System.out.println("So, count of human players: " + (countPlayers - countRandom));
            ArrayList<Player> players = new ArrayList<>();
            for (int i = 0; i < countRandom; i++) {
                if (typeBoard.equals("Diamond")) {
                    players.add(new RandomPlayer(new Random(),  2 * m - 2, 2 * n - 2));
                }
                else{
                    players.add(new RandomPlayer(new Random(), m, n));
                }
            }
            for (int i = 0; i < countPlayers - countRandom; i++){
                players.add(new HumanPlayer());
            }
            DoubleElemination tournament = new DoubleElemination(countPlayers, players, m, n, k, typeBoard);
            tournament.play();
        } else {
            System.out.println("And finally, Who will be played? [Random, Human]");
            String firstPlayer = in.next();
            String secondPlayer = in.next();
            Player Player1, Player2;
            //note: copypaste
            if (firstPlayer.equals("Human")) {
                Player1 = new HumanPlayer();
            } else {
                if (typeBoard.equals("Diamond"))
                    Player1 = new RandomPlayer(new Random(), 2 * m - 2, 2 * n - 2);
                else {
                    Player1 = new RandomPlayer(new Random(), m, n);
                }
            }
            if (secondPlayer.equals("Human")) {
                Player2 = new HumanPlayer();
            } else {
                if (typeBoard.equals("Diamond"))
                    Player2 = new RandomPlayer(new Random(),2 * m - 2, 2 * n - 2);
                else {
                    Player2 = new RandomPlayer(new Random(), m, n);
                }
            }
            final Game game = new Game(true, Player1, Player2);
            if (typeBoard.equals("Diamond")) {
                int result = game.play(new MNKBoard(m, n, k, true));
                System.out.println("Game result: " + "Player " + result + " win");
            } else {
                int result = game.play(new MNKBoard(m, n, k, false));
                System.out.println("Game result: " + "Player " + result + " win");
            }
        }
    }
}
